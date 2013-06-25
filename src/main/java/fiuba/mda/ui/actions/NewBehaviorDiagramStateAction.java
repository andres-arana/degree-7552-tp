package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;

import com.google.inject.Inject;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.BehaviorState;
import fiuba.mda.ui.utilities.ImageLoader;

/**
 * {@link Action} implementation which represents the command of creating a new
 * behavior state in the behavior diagram
 */
public class NewBehaviorDiagramStateAction extends Action {
	private BehaviorDiagram boundDiagram;
	private int stateNumber = 0;

	/**
	 * Creates a new {@link NewBehaviorDiagramStateAction} instance
	 * 
	 * @param imageLoader
	 *            the image loader used to provide the image of this action
	 */
	@Inject
	public NewBehaviorDiagramStateAction(final ImageLoader imageLoader) {
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
		// TODO: Implement dialog to configure the new state
		BehaviorState state = new BehaviorState("State " + stateNumber);
		state.setX(stateNumber * 100);
		boundDiagram.addState(state);
		stateNumber++;
	}
}
