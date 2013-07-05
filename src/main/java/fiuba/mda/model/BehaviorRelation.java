package fiuba.mda.model;

import java.util.ArrayList;
import java.util.List;

public class BehaviorRelation {
	private final String name;
    private final BehaviorState initialState;
    private final BehaviorState finalState;

	public BehaviorRelation(final String name, BehaviorState initialState, BehaviorState finalState) {
		this.name = name;
        this.initialState = initialState;
        this.finalState = finalState;
    }

	public String getName() {
		return name;
	}

    public BehaviorState getInitialState() {
        return initialState;
    }

    public BehaviorState getFinalState() {
        return finalState;
    }

    public boolean hasAll(List<String> stateNames) {
        List<String> relationStatesName = new ArrayList<>();
        relationStatesName.add(initialState.getName());
        relationStatesName.add(finalState.getName());
        return  relationStatesName.containsAll(stateNames);
    }

    public boolean hasAll(String initialState, String finalState) {
        return  this.initialState.getName().equals(initialState) && this.finalState.getName().equals(finalState);
    }
}
