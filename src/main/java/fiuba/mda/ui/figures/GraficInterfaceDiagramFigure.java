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
    public interface Dialogs {
        /**
        * Crea los dialogos para editar los behavior
        */
        public void showTextDialog(BehaviorText behaviorText);
        public void showButtonDialog(BehaviorButton bevaviorButton);
        public void showFieldDialog(BehaviorField behaviorField);
        public void showFormDialog(BehaviorForm behaviorForm);
    }

    private Dialogs dialogs;


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

    private Observer<GraficInterfaceDiagram> onFormChanged = new Observer<GraficInterfaceDiagram>() {
        @Override
        public void notify(GraficInterfaceDiagram observable) {
            rebindChildFigures();
        }
    };

	private final GraficInterfaceDiagram component;


    private List<BehaviorTextFigure> behaviorTextFigures;

    private List<BehaviorButtonFigure> behaviorButtonFigures;

    private List<BehaviorFieldFigure> behaviorFieldFigures;
    private List<BehaviorFormFigure> behaviorFormFigures;

	/**
	 * Creates a new @{link BehaviorDiagramFigure} instance
	 *
	 * @param component
	 *            the {@link fiuba.mda.model.BehaviorDiagram} instance to display
	 */
	public GraficInterfaceDiagramFigure(final GraficInterfaceDiagram component, final Dialogs dialogs) {
		this.component = component;
        this.dialogs = dialogs;
        component.textsChangedEvent().observe(onTextChanged);
        component.fieldsChangedEvent().observe(onFieldChanged);
        component.buttonsChangedEvent().observe(onButtonChanged);
        component.formsChangedEvent().observe(onFormChanged);
		setLayoutManager(new FreeformLayout());
		rebindChildFigures();
        behaviorTextFigures = new ArrayList<>();
        behaviorButtonFigures = new ArrayList<>();
        behaviorFieldFigures = new ArrayList<>();
        behaviorFormFigures = new ArrayList<>();
    }

	private void rebindChildFigures() {
		removeAll();
		for (Representation<BehaviorText> text : component.getTexts()) {
            BehaviorTextFigure figure = new BehaviorTextFigure(text, this.dialogs);
            add(figure);
            getBehaviorTextFigures().add(figure);
		}
		for (Representation<BehaviorButton> button : component.getButtons()) {
			BehaviorButtonFigure figure = new BehaviorButtonFigure(button, this.dialogs);
            add(figure);
            getBehaviorButtonFigures().add(figure);
		}
		for (Representation<BehaviorField> field : component.getFields()) {
			BehaviorFieldFigure figure = new BehaviorFieldFigure(field, this.dialogs);
            add(figure);
            getBehaviorFieldFigures().add(figure);
		}
        for (Representation<BehaviorForm> form : component.getForms()) {
            BehaviorFormFigure figure = new BehaviorFormFigure(form, this.dialogs);
            add(figure);
            getBehaviorFormFigures().add(figure);
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

    public List<BehaviorFormFigure> getBehaviorFormFigures() {
        if (behaviorFormFigures == null){
            behaviorFormFigures = new ArrayList<>();
        }
        return behaviorFormFigures;
    }



}
