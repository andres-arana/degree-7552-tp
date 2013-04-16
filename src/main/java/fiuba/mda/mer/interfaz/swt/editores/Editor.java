package fiuba.mda.mer.interfaz.swt.editores;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import fiuba.mda.mer.interfaz.swt.Principal;
import fiuba.mda.mer.interfaz.swt.dialogs.Dialog;
import fiuba.mda.mer.modelo.Atributo.TipoAtributo;
import fiuba.mda.mer.modelo.Entidad.TipoEntidad;
import fiuba.mda.mer.modelo.Jerarquia.TipoJerarquia;
import fiuba.mda.mer.modelo.Relacion.TipoRelacion;

public abstract class Editor<T> extends Dialog {
	public final static String[] TiposAtributo = getTipos(TipoAtributo.class);
	public final static String[] TiposEntidades = getTipos(TipoEntidad.class);
	public final static String[] TiposRelaciones = getTipos(TipoRelacion.class);
	public final static String[] TiposJerarquias = getTipos(TipoJerarquia.class);

	public static final String ENTIDAD = "Entidad";
	public static final String NOMBRE = "Nombre";
	public static final String TIPO = "Tipo";
	public static final String ROL = "Rol";
	public static final String CARDINALIDAD_MIN = "Card. Mín.";
	public static final String CARDINALIDAD_MAX = "Card. Máx.";
	public static final String ATRIBUTOS = "Atributos";
	public static final String ENTIDADES = "Entidades";
	public static final String IDENTIFICADORES = "Identificadores";
	public static final String NUEVO = "Nuevo";
	public static final String ELIMINAR = "Eliminar";
	public static final String FORMULA = "Fórmula";
	public static final String ORIGINAL = "Original";
	public static final String AGREGAR = "Agregar";

	protected T componente;

	public Editor(T componente) {
		super();
		this.componente = componente;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(400, 400);
	}

	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		this.cargarDatos();

		return control;
	}

	private static <T> String[] getTipos(Class<T> enumClass) {
		List<String> tipos = new ArrayList<>();

		for (T tipo : enumClass.getEnumConstants())
			tipos.add(tipo.toString());

		return tipos.toArray(new String[] {});
	}

	@Override
	protected void okPressed() {
		List<String> errors = new ArrayList<>();

		if (this.validar(errors)) {
			this.aplicarCambios();
			principal.actualizarVista();
			super.okPressed();
		} else {
			String mensaje = "";
			for (String error : errors)
				mensaje += error + "\n";
			Principal.getInstance().error(mensaje);
		}
	}

	protected static Text createLabelText(Composite parent, String name) {
		Label lblNombre = new Label(parent, SWT.LEFT);
		lblNombre.setText(name);

		Text txtField = new Text(parent, SWT.BORDER);

		return txtField;
	}

	protected static Combo createLabelCombo(Composite parent, String name) {
		Label lblNombre = new Label(parent, SWT.LEFT);
		lblNombre.setText(name);

		Combo cboField = new Combo(parent, SWT.READ_ONLY | SWT.BORDER);

		return cboField;
	}

	public T getComponente() {
		return this.componente;
	}

	/**
	 * Carga los datos del componente en los controles.
	 */
	protected abstract void cargarDatos();

	/**
	 * Aplica los cambios hechos al componente.
	 */
	protected abstract void aplicarCambios();

	/**
	 * Realiza validaciones antes de aceptar los cambios.
	 * 
	 * @param errors
	 *            lista de errores de validacion.
	 * @return <code>true</code> si no hay errores, <code>false</code> si los
	 *         hay.
	 */
	protected abstract boolean validar(List<String> errors);
}
