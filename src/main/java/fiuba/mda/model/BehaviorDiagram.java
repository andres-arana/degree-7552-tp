package fiuba.mda.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fiuba.mda.utilities.SimpleEvent;

/**
 * Represents a behavior diagram which presents details of a particular user
 * interaction flow on the modeled software
 */
public class BehaviorDiagram extends AbstractLeafProjectComponent {
	private final SimpleEvent<BehaviorDiagram> statesChangedEvent = new SimpleEvent<>(
			this);
    private final SimpleEvent<BehaviorDiagram> relationChangedEvent = new SimpleEvent<>(
            this);

	private final List<Representation<BehaviorState>> states = new ArrayList<>();

    private final List<Representation<BehaviorRelation>> relations = new ArrayList<>();

    /**
	 * Creates a new {@link BehaviorDiagram} instance
	 * 
	 * @param name
	 *            the name which represents this diagram
	 */
	public BehaviorDiagram(final String name) {
		super(name);
	}

    @Override
    public void accept(ProjectComponentVisitor visitor) {
        visitor.visit(this);
    }

    @Override
	public void accept(ProjectComponentVisitor visitor,boolean isEditing) {
		visitor.visit(this,isEditing);
	}

	public List<Representation<BehaviorState>> getStates() {
		return Collections.unmodifiableList(states);
	}

    public List<Representation<BehaviorRelation>> getRelations() {
        return Collections.unmodifiableList(relations);
    }

	public void addState(Representation<BehaviorState> state) {
		states.add(state);
		statesChangedEvent.raise();
	}

    public void addRelation(Representation<BehaviorRelation> relation) {
        relations.add(relation);
        relationChangedEvent.raise();
    }

	/**
	 * An event raised when some states have been added or removed
	 * 
	 * @return the event
	 */
	public SimpleEvent<BehaviorDiagram> statesChangedEvent() {
		return statesChangedEvent;
	}

    public SimpleEvent<BehaviorDiagram> relationChangedEvent() {
        return relationChangedEvent;
    }

    public BehaviorState getStateByName(String name) {
        for (Representation<BehaviorState> state : getStates()){
            if (state.getEntity().getName().equals(name))
                return state.getEntity();
        }
        return null;  //To change body of created methods use File | Settings | File Templates.
    }
}
