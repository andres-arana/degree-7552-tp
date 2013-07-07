package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.BehaviorRelation;
import fiuba.mda.model.Representation;
import fiuba.mda.ui.launchers.SimpleDialogLauncher;
import fiuba.mda.ui.main.workspace.RelationDialog;
import fiuba.mda.ui.utilities.ImageLoader;

/**
 * Created with IntelliJ IDEA. User: Juan-Asus Date: 04/07/13 Time: 23:57 To
 * change this template use File | Settings | File Templates.
 */
public class NewBehaviorDiagramRelationAction extends Action {

	private BehaviorDiagram boundDiagram;
	private final SimpleDialogLauncher dialog;
	private int relationNumber = 0;
	private final Shell shell;

	/**
	 * Creates a new {@link NewBehaviorDiagramStateAction} instance
	 * 
	 * @param imageLoader
	 *            the image loader used to provide the image of this action
	 * @param dialog
	 */
	@Inject
	public NewBehaviorDiagramRelationAction(final Shell shell,
			final ImageLoader imageLoader, SimpleDialogLauncher dialog) {
		this.shell = shell;
		this.dialog = dialog;
		setupPresentation(imageLoader);
	}

	private void setupPresentation(final ImageLoader imageLoader) {
		setText("Nueva Relación");
		setToolTipText("Crear una nueva relación en el diagrama actual");
		setImageDescriptor(imageLoader.descriptorOf("link_add"));
	}

	/**
	 * Binds this action to work on a given {@link BehaviorDiagram} instance
	 * 
	 * @param diagram
	 *            the diagram to bind this action to
	 * @return this for method chaining
	 */
	public NewBehaviorDiagramRelationAction boundTo(
			final BehaviorDiagram diagram) {
		boundDiagram = diagram;
		return this;
	}

	@Override
	public void run() {
		if (!isValidSituation())
			return;
		RelationDialog dialogo = new RelationDialog(shell,
				boundDiagram.getStates(), boundDiagram.getRelations());
		Optional<String> stringOptional = dialog.showDialog(dialogo);
		if (stringOptional.isPresent()) {
			String initialStateName = dialogo.getInitialStateName();
			String finalStateName = dialogo.getFinalStateName();
			// TODO - Validar que la relación no exista
			BehaviorRelation relation = new BehaviorRelation("Relation"
					+ relationNumber,
					boundDiagram.getStateByName(initialStateName),
					boundDiagram.getStateByName(finalStateName));
			Representation<BehaviorRelation> representation = new Representation<>(
					relation);
			boundDiagram.addRelation(representation);
			relationNumber++;
		}
	}

	private boolean isValidSituation() {
		if (boundDiagram.getStates().size() < 2) {
			dialog.showError("Debe haber como minimo 2 estados");
			return false;
		}
		return true;
	}
}
