package fiuba.mda.ui.actions;

import fiuba.mda.model.*;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import fiuba.mda.ui.launchers.SimpleDialogLauncher;
import fiuba.mda.ui.main.workspace.StateDialog;
import fiuba.mda.ui.utilities.ImageLoader;

import java.util.List;

/**
 * {@link Action} implementation which represents the command of creating a new
 * behavior state in the behavior diagram
 */
public class NewBehaviorDiagramStateAction extends Action {
    private final Application model;
    private final SimpleDialogLauncher dialog;
	private BehaviorDiagram boundDiagram;
	private int stateNumber = 0;
	private final Shell shell;

	/**
	 * Creates a new {@link NewBehaviorDiagramStateAction} instance
	 *
     * @param imageLoader
     *            the image loader used to provide the image of this action
     * @param model
     * @param dialog
     */
	@Inject
	public NewBehaviorDiagramStateAction(final Shell shell,
                                         final ImageLoader imageLoader, Application model, SimpleDialogLauncher dialog) {
		this.shell = shell;
        this.model = model;
        this.dialog = dialog;
		setupPresentation(imageLoader);
	}

	private void setupPresentation(final ImageLoader imageLoader) {
		setText("Nuevo Estado");
		setToolTipText("Crear un nuevo estado en el diagrama actual");
		setImageDescriptor(imageLoader.descriptorOf("package_add"));
	}

	/**
	 * Binds this action to work on a given {@link BehaviorDiagram} instance
	 * 
	 * @param diagram
	 *            the diagram to bind this action to
	 * @return this for method chaining
	 */
	public NewBehaviorDiagramStateAction boundTo(final BehaviorDiagram diagram) {
		boundDiagram = diagram;
		return this;
	}

	@Override
	public void run() {
        List<GraficInterfaceDiagram> interfaces = model.getAllGIDiagramForActivePackage();
        StateDialog dialogo = new StateDialog(shell,interfaces,boundDiagram.hasInitialState());
		Optional<String> stringOptional = dialog.showDialog(dialogo);
		if (stringOptional.isPresent()) {
			String formName = dialogo.getFormName();
			String formTypeName = dialogo.getFormTypeName();
            GraficInterfaceDiagram interfazGrafica = dialogo.getGraficInterfaceDiagram();
			BehaviorState state = new BehaviorState(
					formName, formTypeName,interfazGrafica);
			Representation<BehaviorState> representation = new Representation<>(
					state);
			representation.getPosition().setX(stateNumber * 100);
			boundDiagram.addState(representation);
			stateNumber++;
		}

	}
}
