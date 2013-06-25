package fiuba.mda.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fiuba.mda.utilities.SimpleEvent;

/**
 * Represents a behavior diagram which present details of a particular user
 * interaction flow on the modeled software
 */
public class BehaviorDiagram extends AbstractLeafProjectComponent {
	private final SimpleEvent<BehaviorDiagram> statesChangedEvent = new SimpleEvent<>(
			this);

	private final List<BehaviorState> states = new ArrayList<BehaviorState>();

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

	/**
	 * Obtains an unmodifiable list of the states registered in this diagram
	 * 
	 * @return the list of states
	 */
	public List<BehaviorState> getStates() {
		return Collections.unmodifiableList(states);
	}

	/**
	 * Adds a new state to the diagram
	 * 
	 * @param state
	 *            the state to add
	 */
	public void addState(BehaviorState state) {
		states.add(state);
		statesChangedEvent.raise();
	}

	/**
	 * An event raised when some states have been added or removed
	 * 
	 * @return the event
	 */
	public SimpleEvent<BehaviorDiagram> statesChangedEvent() {
		return statesChangedEvent;
	}
}
