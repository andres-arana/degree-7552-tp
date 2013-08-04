package fiuba.mda.model;

import java.util.ArrayList;
import java.util.List;

public class BehaviorRelation {

    public final static String TIPO_FUNCIONAL = "FUNCIONAL";
    public final static String TIPO_CONTROL = "CONTROL";

	private  String name;
    private  String tipo;
    private  String codigo;
    private  BehaviorState initialState;
    private  BehaviorState finalState;
    private  BehaviorRelation bilateralRelation;

	public BehaviorRelation(final String name, String tipo, String codigo, BehaviorState initialState, BehaviorState finalState) {
		this.name = name;
        this.tipo = tipo;
        if (tipo.equals(TIPO_CONTROL)) this.codigo = codigo;
        else this.codigo = "";
        this.initialState = initialState;
        this.finalState = finalState;
        this.bilateralRelation = null;
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

    public BehaviorRelation getBilateralRelation() {
        return bilateralRelation;
    }

    public void setBilateralRelation(BehaviorRelation bilateralRelation) {
        this.bilateralRelation = bilateralRelation;
    }

    public boolean hasBillateralRelation(){
        return bilateralRelation != null;
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
