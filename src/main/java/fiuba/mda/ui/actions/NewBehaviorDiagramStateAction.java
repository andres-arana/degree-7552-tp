package fiuba.mda.ui.actions;

import com.google.common.base.Optional;
import fiuba.mda.Application;
import fiuba.mda.ui.launchers.SimpleDialogLauncher;
import fiuba.mda.ui.main.workspace.StateDialog;
import org.eclipse.jface.action.Action;

import com.google.inject.Inject;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.BehaviorState;
import fiuba.mda.model.Representation;
import fiuba.mda.ui.utilities.ImageLoader;

/**
 * {@link Action} implementation which represents the command of creating a new
 * behavior state in the behavior diagram
 */
public class NewBehaviorDiagramStateAction extends Action {
    private final SimpleDialogLauncher dialog;
    private BehaviorDiagram boundDiagram;
	private int stateNumber = 0;

	/**
	 * Creates a new {@link NewBehaviorDiagramStateAction} instance
	 *
     * @param imageLoader
     *            the image loader used to provide the image of this action
     * @param dialog
     */
	@Inject
	public NewBehaviorDiagramStateAction(final ImageLoader imageLoader, SimpleDialogLauncher dialog) {
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
        StateDialog dialogo = new StateDialog(Application.getShell());
        Optional<String> stringOptional = dialog.showDialog(dialogo);
        if(stringOptional.isPresent()){
            String formName = dialogo.getFormName();
            String formTypeName = dialogo.getFormTypeName();
            BehaviorState state = new BehaviorState(/*"State " + stateNumber*/formName, formTypeName);
            Representation<BehaviorState> representation = new Representation<>(state);
            representation.getPosition().setX(stateNumber * 100);
            boundDiagram.addState(representation);
            stateNumber++;
        }

	}
}
