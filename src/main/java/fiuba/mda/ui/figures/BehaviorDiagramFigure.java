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

		SelectionManager selectionManager = new SelectionManager();

		for (Representation<BehaviorState> state : component.getStates()) {
            BehaviorStateFigure figure = new BehaviorStateFigure(state);

            SelectableElementFigure selectable = new SelectableElementFigure(state, figure, new SelectEventListener(selectionManager), figure.getWidth(), figure.getHeidth());

            selectionManager.add(selectable);

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

	private class SelectEventListener implements SelectableElementFigure.ISelectEvent {
		private SelectionManager selectionManager;

		public SelectEventListener(SelectionManager sm) {
			selectionManager = sm;
		}

		@Override
		public void select(SelectableElementFigure sef) {
			selectionManager.selected(sef);
		}

		@Override
		public void multiSelect(SelectableElementFigure sef) {
			selectionManager.multiSelect(sef);
		}		
	}

	private class SelectionManager {
		private List<SelectableElementFigure> selectables;
		public SelectionManager() {
			selectables = new ArrayList<>();
		}
		public void add(SelectableElementFigure selectable) {
			selectables.add(selectable);

		}
		public void selected(SelectableElementFigure selectedSelectable) {
			for (SelectableElementFigure selectable : selectables) {
				if (selectable != selectedSelectable) {
					selectable.setSelected(false);
				} else {
					selectable.setSelected(true);
				}
			}
		}

		public void multiSelect(SelectableElementFigure selectedSelectable) {
			selectedSelectable.setSelected(true);
		}		
	}

}
