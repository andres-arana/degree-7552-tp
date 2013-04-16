package fiuba.mda.mer.ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PrintFigureOperation;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.w3c.dom.Document;

import com.google.inject.Provider;
import com.google.inject.Singleton;

import fiuba.mda.mer.control.DiagramaControl;
import fiuba.mda.mer.control.DiagramaLogicoControl;
import fiuba.mda.mer.modelo.Proyecto;
import fiuba.mda.mer.modelo.ProyectoProxy;
import fiuba.mda.mer.modelo.Validacion.EstadoValidacion;
import fiuba.mda.mer.modelo.validacion.Observacion;
import fiuba.mda.mer.ui.builders.DialogBuilder;
import fiuba.mda.mer.ui.builders.DialogBuilder.PromptResult;
import fiuba.mda.mer.ui.builders.DialogBuilder.Resultado;
import fiuba.mda.mer.ui.builders.MenuBuilder;
import fiuba.mda.mer.ui.builders.ToolBarBuilder;
import fiuba.mda.mer.ui.builders.TreeManager;
import fiuba.mda.mer.ui.dialogs.AddEntityDialog;
import fiuba.mda.mer.ui.dialogs.AddHierarchyDialog;
import fiuba.mda.mer.ui.dialogs.AddRelationDialog;
import fiuba.mda.mer.ui.figuras.DiagramaFigura;
import fiuba.mda.mer.ui.figuras.DiagramaLogicoFigura;
import fiuba.mda.mer.xml.SaverLoaderXML;
import fiuba.mda.mrel.conversor.ConversorDERRepresentacion;
import fiuba.mda.mrel.conversor.ConversorDERaLogico;
import fiuba.mda.mrel.modelo.DiagramaLogico;

/**
 * Formulario principal de la aplicacion.
 * 
 */
@Singleton
public class Principal extends Observable implements FigureListener {
	/**
	 * Color predeterminado del ç–µea principal del diagrama.
	 */
	public static final Color defaultBackgroundColor = new Color(null, 255,
			255, 255);
	/**
	 * Tå’œulo a mostrar de la aplicaciî‰¢.
	 */
	public static final String APP_NOMBRE = "MER Editor";
	/**
	 * Tå’œulo del pop-up "Guardar cambios"
	 */
	private static final String TITULO_GUARDAR_DIAGRAMA_ACTUAL = "Informaciî‰¢";
	/**
	 * Mensaje del pop-up "Guardar cambios"
	 */
	private static final String MENSAJE_GUARDAR_DIAGRAMA_ACTUAL = "ï½¿Desea guardar los cambios hechos al diagrama actual?";
	/**
	 * Extensiî‰¢ de los archivos del proyecto.
	 */
	public static final String[] extensionProyecto = new String[] { "*.xml" };
	/**
	 * Extensiî‰¢ de los archivos del validaciî‰¢.
	 */
	public static final String[] extensionValidacion = new String[] { "*.txt" };
	/**
	 * Extensiî‰¢ de la imagen a exportar.
	 */
	public static final String[] extensionesImagen = new String[] { "*.jpg" };
	/**
	 * Ubicaciî‰¢ de los recursos de imç–Šenes.
	 */
	private static final String PATH_IMAGENES = "/imagenes/";
	/**
	 * Ubicaciî‰¢ de los recursos de iconos.
	 */
	private static final String PATH_ICONOS = "/imagenes/";
	/**
	 * Formato de fecha.
	 */
	private static final Format dateFormat = new SimpleDateFormat("yyMMdd");

	/**
	 * Service to display message boxes
	 */
	private final MessageBoxes messageBoxes;

	/**
	 * Service to set the currently open project
	 */
	private final CurrentOpenProject currentProject;

	/**
	 * {@link Provider} for {@link AddEntityDialog} instances
	 */
	private final Provider<AddEntityDialog> entityDialogProvider;

	/**
	 * {@link Provider} for {@link AddHierarchyDialog} instances
	 */
	private final Provider<AddHierarchyDialog> hierarchyDialogProvider;

	/**
	 * {@link Provider} for {@link AddRelationDialog} instances
	 */
	private final Provider<AddRelationDialog> relationDialogProvider;

	/**
	 * Shell de SWT de la aplicaciî‰¢.
	 */
	private Shell shell;

	private SashForm sashForm;
	private ToolBar toolBar;
	private FigureCanvas figureCanvas;
	private Label lblStatus;

	/**
	 * Figura sobre la que se dibuja el diagrama.
	 */
	private DiagramaFigura panelDiagrama;
	private DiagramaLogicoFigura panelDiagramaLogico;

	/**
	 * Handler del evento cuando se cierra la aplicaciî‰¢. Si hay modificaciones
	 * pendientes de ser guardadas muestra el prompt.
	 */
	private Listener promptClose = new Listener() {
		@Override
		public void handleEvent(Event event) {
			int resultado = preguntarGuardar();
			event.doit = resultado != SWT.CANCEL;
		}
	};

	/**
	 * Constructor
	 * 
	 * @param shell
	 */
	private Principal(final Shell shell, final MessageBoxes messageBoxes,
			final CurrentOpenProject currentProject,
			final Provider<AddEntityDialog> entityDialog,
			final Provider<AddHierarchyDialog> hierarchyDialog,
			final Provider<AddRelationDialog> relationDialog) {
		this.messageBoxes = messageBoxes;
		this.currentProject = currentProject;
		this.entityDialogProvider = entityDialog;
		this.hierarchyDialogProvider = hierarchyDialog;
		this.relationDialogProvider = relationDialog;
		this.shell = shell;
		this.shell.setMaximized(true);
		this.shell.setText(APP_NOMBRE);
		this.shell.setLayout(new FormLayout());
		this.shell.addListener(SWT.Close, this.promptClose);
		this.shell.setImage(getImagen("diagrama.png"));

		// Construir y agregar los controles.
		MenuBuilder.build(this);
		this.toolBar = ToolBarBuilder.build(this);
		this.sashForm = new SashForm(this.shell, SWT.HORIZONTAL);
		TreeManager.build(this.sashForm);
		this.lblStatus = new Label(shell, SWT.BORDER);

		this.initFigureCanvas();

		this.arregloLayout();
	}

	public void open() {
		this.shell.open();
	}

	/**
	 * Establece el layout de los diferentes widgets en la ventana principal.
	 */
	private void arregloLayout() {
		FormData formData = null;

		// Separacion vertical entre arbol y grafico.
		formData = new FormData();
		formData.top = new FormAttachment(this.toolBar);
		formData.bottom = new FormAttachment(this.lblStatus);
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		this.sashForm.setLayoutData(formData);
		this.mostrarArbol(false);

		formData = new FormData();
		formData.left = new FormAttachment(0);
		formData.right = new FormAttachment(100);
		formData.bottom = new FormAttachment(100);
		this.lblStatus.setLayoutData(formData);
	}

	/**
	 * Inicializa el canvas donde se dibuja el diagrama.
	 */
	private void initFigureCanvas() {
		this.figureCanvas = new FigureCanvas(this.sashForm, SWT.V_SCROLL
				| SWT.H_SCROLL);
		this.figureCanvas
				.setHorizontalScrollBarVisibility(FigureCanvas.AUTOMATIC);
		this.figureCanvas
				.setVerticalScrollBarVisibility(FigureCanvas.AUTOMATIC);
		this.figureCanvas.setBackground(Principal.defaultBackgroundColor);
		this.figureCanvas.getViewport().setContentsTracksHeight(true);
		this.figureCanvas.getViewport().setContentsTracksWidth(true);
	}

	/**
	 * Crea un nuevo proyecto.
	 * 
	 * @throws Exception
	 */
	public void nuevoProyecto() {
		int resultadoGuardar = this.preguntarGuardar();

		if (resultadoGuardar != SWT.CANCEL) {
			PromptResult resultado = DialogBuilder.prompt(this.shell,
					"Ingresar nombre", "Nombre");

			if (resultado.result == Resultado.OK) {
				currentProject.open(new Proyecto(resultado.value));
				this.cargarProyecto();
			}
		}
	}

	/**
	 * Abre un proyecto.
	 */
	public void abrirProyecto() {
		int resultado = this.preguntarGuardar();

		if (resultado != SWT.CANCEL) {
			FileDialog fileDialog = new FileDialog(this.shell);
			fileDialog.setFilterExtensions(extensionProyecto);
			String path = fileDialog.open();

			if (path != null) {
				try {
					SaverLoaderXML modelo = new SaverLoaderXML(path);
					currentProject.open(modelo.load());
					this.cargarProyecto();
				} catch (Exception e) {
					e.printStackTrace();
					messageBoxes.error(e.getMessage());
				}
			}
		}
	}

	/**
	 * Carga el proyecto actual.
	 */
	private void cargarProyecto() {
		Proyecto project = currentProject.get();
		project.setDiagramaActual(project.getDiagramaRaiz().getId());
		project.setDiagramaActual(project.getDiagramaRaiz().getId());
		this.panelDiagramaLogico = new DiagramaLogicoFigura(this.figureCanvas,
				project);
		this.panelDiagrama = new DiagramaFigura(this.figureCanvas, project);
		this.panelDiagrama.actualizar();

		// Carga inicial del arbol.
		TreeManager.cargar(project);
		this.mostrarArbol(true);
		// Notificar a la toolbar que hay un proyecto abierto.
		this.setChanged();
		this.notifyObservers();

		this.modificado(false);
		this.actualizarEstado();
	}

	private void cargarInterfazLogica() {
		this.panelDiagramaLogico = new DiagramaLogicoFigura(this.figureCanvas,
				currentProject.get());
		this.panelDiagramaLogico.actualizar();
	}

	private void cargarInterfazNormal() {
		this.panelDiagrama = new DiagramaFigura(this.figureCanvas,
				currentProject.get());
		this.panelDiagrama.actualizar();
	}

	/**
	 * Actualiza la barra de estado con el del proyecto y el diagrama actual.
	 */
	private void actualizarEstado() {
		String status = "[Ningun proyecto abierto]";

		if (currentProject.hasProject()) {
			Proyecto project = currentProject.get();
			status = "Proyecto: %s [%s]- Diagrama: %s [%s]";
			status = String
					.format(status, project.getNombre(), project
							.getValidacion().getEstado().toString(), project
							.getDiagramaActual().getNombre(), project
							.getDiagramaActual().getValidacion().getEstado()
							.toString());
		}

		this.lblStatus.setText(status);
	}

	/**
	 * Actualiza el titulo dependiendo de si el proyecto tiene modificaciones
	 * que todavåƒ˜ no se guardaron.
	 */
	private void actualizarTitulo() {
		String titulo = APP_NOMBRE;

		if (currentProject.hasProject()) {
			Proyecto project = currentProject.get();
			titulo += " - " + project.getNombre();
			titulo += this.shell.getModified() ? " *" : "";
			titulo += " [" + project.getPath() + "]";
		}

		this.shell.setText(titulo);
	}

	/**
	 * Guarda un proyecto en el path que ya tiene asignado o muestra el dialogo
	 * para elegir el archivo.
	 */
	public void guardarProyecto() {
		this.guardarProyecto(false);
	}

	/**
	 * Guarda un proyecto en el path indicado.
	 * 
	 * @param showDialog
	 *            indica si se debe mostrar el dialogo de seleccion de archivo.
	 */
	public void guardarProyecto(boolean showDialog) {
		Proyecto project = currentProject.get();
		String path = project.getPath();

		if (path == null || showDialog) {
			FileDialog fileDialog = new FileDialog(this.shell, SWT.SAVE);
			fileDialog.setFilterExtensions(extensionProyecto);
			path = fileDialog.open();
		}

		if (path != null) {
			File file = new File(path);
			String dir = file.getParent() + File.separator;
			project.setPath(path);
			SaverLoaderXML modelo;
			try {
				modelo = new SaverLoaderXML(currentProject.get());
				this.guardarXml(modelo.saveProyecto(), path);

				this.guardarXml(modelo.saveComponentes(),
						dir + project.getComponentesPath());

				this.guardarXml(modelo.saveRepresentacion(),
						dir + project.getRepresentacionPath());

				this.guardarXml(modelo.saveComponentesLogicos(),
						dir + project.getComponentesLogicosPath());

				this.guardarXml(modelo.saveRepresentacionDER(),
						dir + project.getRepresentacionDERPath());

			} catch (Exception e) {
				messageBoxes.error("Ocurrio un error al guardar el proyecto.");
				e.printStackTrace();
			}

			this.modificado(false);
		}
	}

	/**
	 * Guarda un objecto Document en un archivo fisico en el path especificado.
	 * 
	 * @param doc
	 * @param path
	 * @throws Exception
	 */
	private void guardarXml(Document doc, String path) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		// Indicar que escriba el xml con indentaciî‰¢.
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount", "4");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(path));
		transformer.transform(source, result);
	}

	/**
	 * Agrega un Diagrama al proyecto.
	 */
	public void agregarDiagrama() {
		PromptResult resultado = DialogBuilder.prompt(this.shell,
				"Ingresar nombre", "Nombre");
		if (resultado.result == Resultado.OK) {
			DiagramaControl nuevoDiagrama = new DiagramaControl(
					currentProject.get());
			nuevoDiagrama.setNombre(resultado.value);

			currentProject.get().agregar(nuevoDiagrama);
			this.actualizarVista();

			TreeManager.agregarADiagramaActual(nuevoDiagrama);

			this.modificado(true);
		}
	}

	/**
	 * Actualiza la vista.
	 */
	public void actualizarVista() {
		this.panelDiagrama.actualizar();
	}

	public void actualizarVistaLogica() {
		this.panelDiagramaLogico.actualizar();

	}

	/**
	 * Cierra el programa.
	 */
	public void salir() {
		System.exit(0);
	}

	/**
	 * Abre el diagrama para su visualizacion y/o edicion
	 * 
	 * @param id
	 *            Identificador del diagrama a abrir.
	 **/
	public void abrirDiagrama(String id) {
		String idActual = currentProject.get().getDiagramaActual().getId();
		cargarInterfazNormal();
		this.panelDiagrama.setVisible(true);
		this.panelDiagramaLogico.setVisible(false);

		if (!id.equals(idActual)) {
			int resultado = this.preguntarGuardar();

			if (resultado != SWT.CANCEL) {
				currentProject.get().setDiagramaActual(id);
				this.actualizarVista();
				this.actualizarEstado();
			}
		}
	}

	public void abrirDiagramaLogico(String id) {
		this.panelDiagrama.setVisible(false);
		this.panelDiagramaLogico.setVisible(true);
		currentProject.get().setDiagramaLogico(id);
		this.cargarInterfazLogica();
		this.actualizarVistaLogica();
		this.panelDiagramaLogico.actualizar();
	}

	/**
	 * Pregunta al usuario si se quiere guardar el diagrama. Si se ingresa un
	 * si, se realiza el guardado del diagrama y se devuelve el resultado del
	 * dialogo.
	 * 
	 * @return resultado: SWT.YES | SWT.NO | SWT.CANCEL
	 */
	private int preguntarGuardar() {
		int result = SWT.NO;

		if (shell.getModified()) {
			int style = SWT.APPLICATION_MODAL | SWT.YES | SWT.NO | SWT.CANCEL;
			MessageBox messageBox = new MessageBox(shell, style);
			messageBox.setText(TITULO_GUARDAR_DIAGRAMA_ACTUAL);
			messageBox.setMessage(MENSAJE_GUARDAR_DIAGRAMA_ACTUAL);

			result = messageBox.open();

			if (result == SWT.YES)
				guardarProyecto();
		}

		return result;
	}

	/**
	 * Abre el dialogo para agregar una Entidad al diagrama actual.
	 */
	public void agregarEntidad() {
		AddEntityDialog dialog = entityDialogProvider.get();
		if (dialog.open() == Window.OK) {
			currentProject.get().agregar(dialog.getComponent());
			this.actualizarVista();
			TreeManager.agregarADiagramaActual(dialog.getComponent());
			this.modificado(true);
		}
	}

	/**
	 * Abre el dialogo para agregar una Relacion al diagrama actual.
	 */
	public void agregarRelacion() {
		AddRelationDialog dialog = relationDialogProvider.get();
		if (dialog.open() == Window.OK) {
			currentProject.get().agregar(dialog.getComponent());
			this.actualizarVista();
			TreeManager.agregarADiagramaActual(dialog.getComponent());
			this.modificado(true);
		}
	}

	/**
	 * Abre el dialogo para agregar una Jerarquia al diagrama actual.
	 */
	public void agregarJerarquia() {
		AddHierarchyDialog dialog = hierarchyDialogProvider.get();
		if (dialog.open() == Window.OK) {
			currentProject.get().agregar(dialog.getComponent());
			this.actualizarVista();
			TreeManager.agregarADiagramaActual(dialog.getComponent());
			this.modificado(true);
		}
	}

	/**
	 * Disminuciî‰¢ del zoom.
	 */
	public void zoomOut(Combo cboZoom) {
		if (this.panelDiagrama.isVisible()) {
			this.panelDiagrama.zoomOut();
			cboZoom.setText(this.panelDiagrama.getZoom());
		} else if (this.panelDiagramaLogico.isVisible()) {
			this.panelDiagramaLogico.zoomOut(); // workaround para el zoom del
												// diagrama logico
			cboZoom.setText(this.panelDiagramaLogico.getZoom());
		}

	}

	/**
	 * Aumento del zoom.
	 */
	public void zoomIn(Combo cboZoom) {
		if (this.panelDiagrama.isVisible()) {
			this.panelDiagrama.zoomIn();
			cboZoom.setText(this.panelDiagrama.getZoom());
		} else if (this.panelDiagramaLogico.isVisible()) {
			this.panelDiagramaLogico.zoomIn(); // workaround para el zoom del
												// diagrama logico
			cboZoom.setText(this.panelDiagramaLogico.getZoom());
		}

	}

	/**
	 * Establece un valor zoom determinado.
	 * 
	 * @param zoom
	 *            Debe ser alguno de los valores establecidos en
	 *            {@link DiagramaFigura#zoomOptions}.
	 */
	public void zoom(String zoom) {
		if (this.panelDiagrama.isVisible()) {
			this.panelDiagrama.zoom(zoom);
		} else if (this.panelDiagramaLogico.isVisible()) {
			this.panelDiagramaLogico.zoom(zoom); // workaround para el zoom del
													// diagrama logico
		}
	}

	/**
	 * Exportar el diagrama a un archivo de imagen.
	 */
	public void exportar() {
		FileDialog fileDialog = new FileDialog(this.shell, SWT.SAVE);
		fileDialog.setFilterExtensions(extensionesImagen);
		fileDialog.setFileName(currentProject.get().getDiagramaActual()
				.getNombre()
				+ ".jpg");
		String path = fileDialog.open();

		if (path != null) {
			Image image = this.panelDiagrama.getImagen();

			ImageData[] data = new ImageData[1];
			data[0] = image.getImageData();

			ImageLoader imgLoader = new ImageLoader();
			imgLoader.data = data;
			imgLoader.save(path, SWT.IMAGE_JPEG);
		}

		FileDialog fileDialog2 = new FileDialog(this.shell, SWT.SAVE);
		fileDialog2.setFilterExtensions(extensionesImagen);
		fileDialog2.setFileName("Diagrama_Logico" + ".jpg");
		String path2 = fileDialog2.open();

		if (path2 != null) {
			Image image2 = this.panelDiagrama.getImagen();

			ImageData[] data2 = new ImageData[1];
			data2[0] = image2.getImageData();

			ImageLoader imgLoader2 = new ImageLoader();
			imgLoader2.data = data2;
			imgLoader2.save(path2, SWT.IMAGE_JPEG);
		}
	}

	/**
	 * Muestra la pantalla de impresiî‰¢ para el digrama actual.
	 */
	public void imprimir() {
		PrintDialog printDialog = new PrintDialog(this.shell);
		PrinterData printerData = printDialog.open();

		if (printerData != null) {
			Printer printer = new Printer(printerData);

			PrintFigureOperation printerOperation = new PrintFigureOperation(
					printer, this.panelDiagrama);
			printerOperation.setPrintMode(PrintFigureOperation.FIT_PAGE);
			printerOperation.setPrintMargin(new Insets(0, 0, 0, 0));
			printerOperation.run(currentProject.get().getDiagramaActual()
					.getNombre());

			printer.dispose();
		}
	}

	/**
	 * Validar diagrama actual.
	 */
	public void validar() {
		Observacion observacion = currentProject.get().getDiagramaActual()
				.validar();
		this.actualizarEstado();

		if (observacion.isEmpty())
			messageBoxes.warning(Observacion.SIN_OBSERVACIONES);
		else {
			messageBoxes.warning(observacion.toString());

			String nombreArchivo = "Diagrama-"
					+ currentProject.get().getDiagramaActual().getNombre();
			nombreArchivo += String.format("-%s.txt",
					dateFormat.format(new Date()));

			this.guardarValidacion(nombreArchivo, observacion.toString());
		}
	}

	/**
	 * Validar proyecto.
	 */
	public void validarProyecto() {
		Observacion observacion = currentProject.get().validar();
		this.actualizarEstado();

		if (observacion.isEmpty())
			messageBoxes.warning(Observacion.SIN_OBSERVACIONES);
		else {
			messageBoxes.warning(observacion.toString());

			String nombreArchivo = "Proyecto-"
					+ currentProject.get().getDiagramaRaiz().getNombre();
			nombreArchivo += String.format("_%s.txt",
					dateFormat.format(new Date()));

			this.guardarValidacion(nombreArchivo, observacion.toString());
		}
	}

	/**
	 * Muestra una ventana de diç–�ogo para seleccionar donde guardar el
	 * resultado de la validacion.
	 * 
	 * @param nombreArchivo
	 *            Nombre por defecto del archivo de validacion.
	 * @param resultado
	 *            Resultado de la validaciî‰¢.
	 */
	private void guardarValidacion(String path, String resultado) {
		FileDialog fileDialog = new FileDialog(this.shell, SWT.SAVE);
		fileDialog.setFileName(path);
		fileDialog.setFilterExtensions(extensionValidacion);
		path = fileDialog.open();

		if (path != null) {
			try {

				File file = new File(path);
				Writer output = new BufferedWriter(new FileWriter(file));
				output.write(resultado);
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
				messageBoxes.error(e.getMessage());
			}
		}
	}

	/**
	 * Implementaciî‰¢ de la interfaz FigureListener por medio de la cual la
	 * aplicaciî‰¢ se entera cuando se mueve alguna de las figuras.
	 * 
	 * @param source
	 */
	@Override
	public void figureMoved(IFigure source) {
		this.modificado(true);
	}

	/**
	 * Devuelve un proxy del proyecto que se encuentra abierto exponiendo solo
	 * los mé¨�odos de la interfaz <code>ProyectoProxy</code>.
	 * 
	 * @return
	 */
	public ProyectoProxy getProyecto() {
		return currentProject.getForQueries();
	}

	/**
	 * Define si el proyecto fue modificado y actualiza el titulo de la ventana
	 * principal.
	 * 
	 * @param modificado
	 *            Define el estado del diagrama actual. <code>true</code> si el
	 *            diagrama tiene alguna modificaciî‰¢ pendiente de ser guardada.
	 *            <code>false</code> si no tiene ninguna.
	 */
	private void modificado(boolean modificado) {
		if (modificado != this.shell.getModified()) {
			this.shell.setModified(modificado);
			this.actualizarTitulo();
		}

		if (modificado && currentProject.hasProject()) {
			currentProject.get().getValidacion()
					.setEstado(EstadoValidacion.SIN_VALIDAR);
			currentProject.get().getDiagramaActual().getValidacion()
					.setEstado(EstadoValidacion.SIN_VALIDAR);

			this.actualizarEstado();
		}
	}

	/**
	 * Muestra o esconde el arbol de jerarquåƒ˜s segä½– el valor del parç–¥etros
	 * 
	 * @param mostrar
	 *            indica si se debe mostrar el ç–µbol.
	 */
	public void mostrarArbol(boolean mostrar) {
		int peso = mostrar ? 3 : 0;
		this.sashForm.setWeights(new int[] { peso, 16 });
	}

	/**
	 * Convierte de DER a logico
	 */
	public void convertir() {

		TreeManager.borrarLogicoActivo();
		if (currentProject.get().getDiagramaLogico() != null)
			currentProject.get().borrarDiagramaLogico();

		ConversorDERaLogico conversor = ConversorDERaLogico.getInstance();
		DiagramaLogico diaLog = conversor.convertir(currentProject.get()
				.getDiagramaActual());
		DiagramaLogicoControl diaControl = new DiagramaLogicoControl(diaLog);
		currentProject.get().setDiagramaLogico(diaControl);

		ConversorDERRepresentacion converRep = new ConversorDERRepresentacion();

		converRep.createRepresentation(currentProject.get()
				.getDiagramaLogicoControl());

		TreeManager.agregarADiagramaActual(diaControl);
		this.modificado(true);
	}

	// From here downwards there are a couple of methods which will be removed
	// after we refactor dependencies to the principal, but that are needed
	// right now
	public void error(String message) {
		messageBoxes.error(message);
	}

	private static Principal instancia;

	public static void inicializar(Shell shell, MessageBoxes messageBoxes,
			CurrentOpenProject currentProject,
			final Provider<AddEntityDialog> entityDialog,
			final Provider<AddHierarchyDialog> hierarchyDialog,
			final Provider<AddRelationDialog> relationDialog) {
		Principal.instancia = new Principal(shell, messageBoxes,
				currentProject, entityDialog, hierarchyDialog, relationDialog);
	}

	public static Principal getInstance() {
		return Principal.instancia;
	}

	public static Image getImagen(String nombre) {
		return loadImagen(PATH_IMAGENES + nombre);
	}

	public static Image getIcono(String nombre) {
		return loadImagen(PATH_ICONOS + nombre);
	}

	private static Image loadImagen(String path) {
		Image img = new Image(Display.getDefault(),
				Principal.class.getResourceAsStream(path));
		return img;
	}

	public Shell getShell() {
		return this.shell;
	}
}
