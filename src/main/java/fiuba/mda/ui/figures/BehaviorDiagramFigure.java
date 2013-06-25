package fiuba.mda.ui.figures;

import org.eclipse.draw2d.FreeformLayer;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.BehaviorState;
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
		rebindChildFigures();
	}

	private void rebindChildFigures() {
		removeAll();
		for (BehaviorState state : component.getStates()) {
			BehaviorStateFigure figure = new BehaviorStateFigure(state);
			add(figure);
			figure.updatePreferredSize();
		}
	}

}
