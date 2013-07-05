package fiuba.mda.model;

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
}
