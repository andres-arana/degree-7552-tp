package fiuba.mda.model;

import java.util.ArrayList;
import java.util.List;

public class BehaviorRelation {

    public final static String TIPO_FUNCIONAL = "FUNCIONAL";
    public final static String TIPO_CONTROL = "CONTROL";

	private final String name;
    private final String tipo;
    private String codigo;
    private final BehaviorState initialState;
    private final BehaviorState finalState;

	public BehaviorRelation(final String name, String tipo, String codigo, BehaviorState initialState, BehaviorState finalState) {
		this.name = name;
        this.tipo = tipo;
        if (tipo.equals(TIPO_CONTROL)) this.codigo = codigo;
        else this.codigo = "";
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

    public String getTipo() {
        return tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
