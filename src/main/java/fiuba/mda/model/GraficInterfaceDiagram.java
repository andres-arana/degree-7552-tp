package fiuba.mda.model;

import fiuba.mda.ui.actions.ExportableToImage;
import fiuba.mda.utilities.SimpleEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.FreeformLayer;

/**
 * Represents a behavior diagram which presents details of a particular user
 * interaction flow on the modeled software
 */
public class GraficInterfaceDiagram extends AbstractLeafProjectComponent implements ExportableToImage{
	private transient SimpleEvent<GraficInterfaceDiagram> textsChangedEvent;
    private transient SimpleEvent<GraficInterfaceDiagram> buttonsChangedEvent;
    private transient SimpleEvent<GraficInterfaceDiagram> fieldsChangedEvent;
    private transient SimpleEvent<GraficInterfaceDiagram> formsChangedEvent;

	private List<Representation<BehaviorText>> texts = new ArrayList<>();

    private List<Representation<BehaviorButton>> buttons = new ArrayList<>();

    private List<Representation<BehaviorField>> fields = new ArrayList<>();

    private List<Representation<BehaviorForm>> forms = new ArrayList<>();
    
    private transient FreeformLayer diagram;

    /**
	 * Creates a new {@link fiuba.mda.model.GraficInterfaceDiagram} instance
	 *
	 * @param name
	 *            the name which represents this diagram
	 */
	public GraficInterfaceDiagram(final String name) {
		super(name);
	}

	@Override
	public void accept(ProjectComponentVisitor visitor) {
		visitor.visit(this);
	}

    @Override
    public void init() {
        super.init();
        textsChangedEvent = new SimpleEvent<>(
                this);
        buttonsChangedEvent = new SimpleEvent<>(
                this);
        fieldsChangedEvent = new SimpleEvent<>(
                this);
        formsChangedEvent = new SimpleEvent<>(
                this);        
    }


    public List<Representation<BehaviorText>> getTexts() {
        return Collections.unmodifiableList(texts);
    }
    
    public List<Representation<BehaviorButton>> getButtons() {
        return Collections.unmodifiableList(buttons);
    }

    public List<Representation<BehaviorField>> getFields() {
        return Collections.unmodifiableList(fields);
    }

    public List<Representation<BehaviorForm>> getForms() {
        return Collections.unmodifiableList(forms);
    }

    public SimpleEvent<GraficInterfaceDiagram> textsChangedEvent() {
        return textsChangedEvent;
    }

    public SimpleEvent<GraficInterfaceDiagram> buttonsChangedEvent() {
        return buttonsChangedEvent;
    }

    public SimpleEvent<GraficInterfaceDiagram> fieldsChangedEvent() {
        return fieldsChangedEvent;
    }


    public SimpleEvent<GraficInterfaceDiagram> formsChangedEvent() {
        return formsChangedEvent;
    }


    public void addText(Representation<BehaviorText> text) {
        texts.add(text);
        textsChangedEvent.raise();
    }
    
    public void addButton(Representation<BehaviorButton> button) {
        buttons.add(button);
        buttonsChangedEvent.raise();
    }

    public void addField(Representation<BehaviorField> field) {
        fields.add(field);
        fieldsChangedEvent.raise();
    }

    public void addForm(Representation<BehaviorForm> form) {
        forms.add(form);
        formsChangedEvent.raise();
    }


    //Devuelve en un string todos los elementos de la pantalla con su nombre y tipo.
    public List<String> getElementsStringList() {
        List<String> s = new ArrayList<>();
        for (Representation<BehaviorField> f : fields){
            BehaviorField entity = f.getEntity();
            String str = entity.getName() + ":Field;";
            s.add(str);
        }


        for (Representation<BehaviorText> t : texts){
            BehaviorText entity = t.getEntity();
            String str = entity.getName() + ":Text;";
            s.add(str);
        }

        for (Representation<BehaviorButton> b : buttons){
            BehaviorButton entity = b.getEntity();
            String str = entity.getName() + ":Button;";
            s.add(str);
        }

        for (Representation<BehaviorForm> b : forms){
            BehaviorForm entity = b.getEntity();
            String str = entity.getFormName() + ":Form;";
            s.add(str);
        }

        return s;
    }
    
    public void setDiagramFigure(FreeformLayer diagram){
    	this.diagram = diagram;
    }
    
    public FreeformLayer getDiagramFigure(){
    	return this.diagram;
    }
    
}
