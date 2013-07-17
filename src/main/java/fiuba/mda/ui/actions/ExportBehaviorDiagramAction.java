package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import fiuba.mda.model.BehaviorButton;
import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.Representation;
import fiuba.mda.ui.launchers.SimpleDialogLauncher;
import fiuba.mda.ui.main.workspace.ButtonDialog;
import fiuba.mda.ui.utilities.ImageLoader;

public class ExportBehaviorDiagramAction extends Action {
	private final SimpleDialogLauncher dialog;
	private BehaviorDiagram boundDiagram;
	private int buttonNumber = 0;
	private final Shell shell;

	/**
	 * Creates a new {@link NewButtonAction} instance
	 * 
	 * @param imageLoader
	 *            the image loader used to provide the image of this action
	 * @param dialog
	 */
	@Inject
	public ExportBehaviorDiagramAction(final Shell shell, final ImageLoader imageLoader, SimpleDialogLauncher dialog) {
		this.shell = shell;
		this.dialog = dialog;
		setupPresentation(imageLoader);
	}

	private void setupPresentation(final ImageLoader imageLoader) {
		setText("Exportar Diagrama como Imagen");
		setToolTipText("Exporta una imagen del diagrama actual");
		setImageDescriptor(imageLoader.descriptorOf("image"));
	}

	/**
	 * Binds this action to work on a given {@link BehaviorDiagram} instance
	 * 
	 * @param diagram
	 *            the diagram to bind this action to
	 * @return this for method chaining
	 */
	public ExportBehaviorDiagramAction boundTo(final BehaviorDiagram diagram) {
		boundDiagram = diagram;
		return this;
	}

	@Override
	public void run() {
		// TODO: implementar accion para exportar el diagrama como imagen
		System.out.println("TODO: implementar accion para exportar el diagrama como imagen");
	}
}
