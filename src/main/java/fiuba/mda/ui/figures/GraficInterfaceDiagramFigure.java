package fiuba.mda.ui.figures;

import fiuba.mda.model.*;
import fiuba.mda.utilities.SimpleEvent.Observer;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Figure which displays a behavior diagram
 */
public class GraficInterfaceDiagramFigure extends FreeformLayer {
	private Observer<GraficInterfaceDiagram> onTextChanged = new Observer<GraficInterfaceDiagram>() {
		@Override
		public void notify(GraficInterfaceDiagram observable) {
			rebindChildFigures();
		}
	};

    private Observer<GraficInterfaceDiagram> onFieldChanged = new Observer<GraficInterfaceDiagram>() {
        @Override
        public void notify(GraficInterfaceDiagram observable) {
            rebindChildFigures();
        }
    };

    private Observer<GraficInterfaceDiagram> onButtonChanged = new Observer<GraficInterfaceDiagram>() {
        @Override
        public void notify(GraficInterfaceDiagram observable) {
            rebindChildFigures();
        }
    };

	private final GraficInterfaceDiagram component;


    private List<BehaviorTextFigure> behaviorTextFigures;

    private List<BehaviorButtonFigure> behaviorButtonFigures;

    private List<BehaviorFieldFigure> behaviorFieldFigures;

	/**
	 * Creates a new @{link BehaviorDiagramFigure} instance
	 *
	 * @param component
	 *            the {@link fiuba.mda.model.BehaviorDiagram} instance to display
	 */
	public GraficInterfaceDiagramFigure(final GraficInterfaceDiagram component) {
		this.component = component;
        component.textsChangedEvent().observe(onTextChanged);
        component.fieldsChangedEvent().observe(onFieldChanged);
        component.buttonsChangedEvent().observe(onButtonChanged);
		setLayoutManager(new FreeformLayout());
		rebindChildFigures();
        behaviorTextFigures = new ArrayList<>();
        behaviorButtonFigures = new ArrayList<>();
        behaviorFieldFigures = new ArrayList<>();
    }

	private void rebindChildFigures() {
		removeAll();
		for (Representation<BehaviorText> text : component.getTexts()) {
            BehaviorTextFigure figure = new BehaviorTextFigure(text);
            add(figure);
            getBehaviorTextFigures().add(figure);
		}
		for (Representation<BehaviorButton> button : component.getButtons()) {
			BehaviorButtonFigure figure = new BehaviorButtonFigure(button);
            add(figure);
            getBehaviorButtonFigures().add(figure);
		}
		for (Representation<BehaviorField> field : component.getFields()) {
			BehaviorFieldFigure figure = new BehaviorFieldFigure(field);
            add(figure);
            getBehaviorFieldFigures().add(figure);
		}
	}
	
	public List<BehaviorTextFigure> getBehaviorTextFigures() {
		if (behaviorTextFigures == null){
            behaviorTextFigures = new ArrayList<>();
        }
        return behaviorTextFigures;
    } 
	
	public List<BehaviorButtonFigure> getBehaviorButtonFigures() {
		if (behaviorButtonFigures == null){
            behaviorButtonFigures = new ArrayList<>();
        }
        return behaviorButtonFigures;
    } 
	
	public List<BehaviorFieldFigure> getBehaviorFieldFigures() {
		if (behaviorFieldFigures == null){
			behaviorFieldFigures = new ArrayList<>();
        }
        return behaviorFieldFigures;
    } 
	


}
