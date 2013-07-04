package fiuba.mda.ui.figures;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.BehaviorState;
import fiuba.mda.model.Representation;
import fiuba.mda.utilities.SimpleEvent.Observer;

/**
 * Figure which displays a behavior diagram
 */
public class BehaviorDiagramFigure extends FreeformLayer {
	private Observer<BehaviorDiagram> onStatesChanged = new Observer<BehaviorDiagram>() {
		@Override
		public void notify(BehaviorDiagram observable) {
			rebindChildFigures();
		}
	};

	private final BehaviorDiagram component;

	/**
	 * Creates a new @{link BehaviorDiagramFigure} instance
	 * 
	 * @param component
	 *            the {@link BehaviorDiagram} instance to display
	 */
	public BehaviorDiagramFigure(final BehaviorDiagram component) {
		this.component = component;
		component.statesChangedEvent().observe(onStatesChanged);
		setLayoutManager(new FreeformLayout());
		rebindChildFigures();
	}

	private void rebindChildFigures() {
		removeAll();
		for (Representation<BehaviorState> state : component.getStates()) {
			add(new BehaviorStateFigure(state));
		}
	}

}
