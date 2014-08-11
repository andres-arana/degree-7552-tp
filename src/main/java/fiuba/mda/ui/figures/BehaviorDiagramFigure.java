package fiuba.mda.ui.figures;

import fiuba.mda.model.BehaviorRelation;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;

import fiuba.mda.model.BehaviorButton;
import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.BehaviorField;
import fiuba.mda.model.BehaviorState;
import fiuba.mda.model.BehaviorText;
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
    /*    behaviorTextFigures = new ArrayList<>();
        behaviorButtonFigures = new ArrayList<>();
    */}

	private void rebindChildFigures() {
		removeAll();
		for (Representation<BehaviorState> state : component.getStates()) {
            BehaviorStateFigure figure = new BehaviorStateFigure(state);
            SelectableElementFigure selectable = new SelectableElementFigure(state, figure, figure.getWidth(), figure.getHeidth());

            add(selectable);
            getBehaviorStateFigures().add(figure);
		}
        for (Representation<BehaviorRelation> relation : component.getRelations()) {
            BehaviorRelationFigure figure = new BehaviorRelationFigure(relation, getBehaviorStateFigures());
            add(figure);
        }

	}
	


	public List<BehaviorStateFigure> getBehaviorStateFigures() {
		if (behaviorStateFigures == null){
			behaviorStateFigures = new ArrayList<>();
		}
		return behaviorStateFigures;
	} 

}
