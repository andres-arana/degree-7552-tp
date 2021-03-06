package fiuba.mda.ui.figures;

import fiuba.mda.model.BehaviorRelation;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.BehaviorState;
import fiuba.mda.model.Representation;
import fiuba.mda.utilities.SimpleEvent.Observer;

import java.util.ArrayList;
import java.util.List;

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

    private Observer<BehaviorDiagram> onRelationChanged = new Observer<BehaviorDiagram>() {
        @Override
        public void notify(BehaviorDiagram observable) {
            rebindChildFigures();
        }
    };

	private final BehaviorDiagram component;

    private List<BehaviorStateFigure> behaviorStateFigures;
    
    private SelectionManager selectionManager;
  /*  private List<BehaviorTextFigure> behaviorTextFigures;
    
    private List<BehaviorButtonFigure> behaviorButtonFigures;
    
    private List<BehaviorFieldFigure> behaviorFieldFigures;
  */
	/**
	 * Creates a new @{link BehaviorDiagramFigure} instance
	 * 
	 * @param component
	 *            the {@link BehaviorDiagram} instance to display
	 */
	public BehaviorDiagramFigure(final BehaviorDiagram component) {
		this.component = component;
		component.statesChangedEvent().observe(onStatesChanged);
        component.relationChangedEvent().observe(onRelationChanged);
		setLayoutManager(new FreeformLayout());
		rebindChildFigures();
        behaviorStateFigures = new ArrayList<>();
        component.setDiagramFigure(this);
    }

	private void rebindChildFigures() {
		removeAll();

		selectionManager = new SelectionManager();

		for (final Representation<BehaviorState> state : component.getStates()) {
            BehaviorStateFigure figure = new BehaviorStateFigure(state);
            add(selectionManager.wrap(state, figure, new SelectableElementFigure.Removable() {
            	public void remove() {
            		component.removeState(state);		
            	}
            }));
            getBehaviorStateFigures().add(figure);
		}
        for (Representation<BehaviorRelation> relation : component.getRelations()) {
            BehaviorRelationFigure figure = new BehaviorRelationFigure(relation, getBehaviorStateFigures());
            add(figure);
        }

	}
	


	private List<BehaviorStateFigure> getBehaviorStateFigures() {
		if (behaviorStateFigures == null){
			behaviorStateFigures = new ArrayList<>();
		}
		return behaviorStateFigures;
	}


	public void removeSelectedObjects() {
		selectionManager.removeSelectedObjects();
	}

    public void removeSelections() {
        selectionManager.removeSelections();
    }    
}
