package fiuba.mda.model;

public class BehaviorState {
    public static final String FORM_SALIDA = "SALIDA";
    public static final String FORM_ENTRADA = "ENTRADA";
    public static final String FORM_COMPUESTO = "COMPUESTO";


    private final String name;
    private final String type;
	
	public BehaviorState(final String name, String type) {
		this.name = name;
        this.type = type;
    }

	public String getName() {
		return name;
	}

    public String getType() {
        return type;
    }
}
