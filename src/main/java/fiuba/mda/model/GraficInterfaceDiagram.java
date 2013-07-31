package fiuba.mda.model;

import fiuba.mda.utilities.SimpleEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a behavior diagram which presents details of a particular user
 * interaction flow on the modeled software
 */
public class GraficInterfaceDiagram extends AbstractLeafProjectComponent {
	private final SimpleEvent<GraficInterfaceDiagram> textsChangedEvent = new SimpleEvent<>(
			this);
    private final SimpleEvent<GraficInterfaceDiagram> buttonsChangedEvent = new SimpleEvent<>(
            this);
    private final SimpleEvent<GraficInterfaceDiagram> fieldsChangedEvent = new SimpleEvent<>(
            this);

	private final List<Representation<BehaviorText>> texts = new ArrayList<>();

    private final List<Representation<BehaviorButton>> buttons = new ArrayList<>();

    private final List<Representation<BehaviorField>> fields = new ArrayList<>();

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


    public List<Representation<BehaviorText>> getTexts() {
        return Collections.unmodifiableList(texts);
    }
    
    public List<Representation<BehaviorButton>> getButtons() {
        return Collections.unmodifiableList(buttons);
    }

    public List<Representation<BehaviorField>> getFields() {
        return Collections.unmodifiableList(fields);
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

        return s;
    }
}
