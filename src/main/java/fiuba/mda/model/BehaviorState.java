package fiuba.mda.model;

public class BehaviorState {
    public static final String FORM_SALIDA = "SALIDA";
    public static final String FORM_ENTRADA = "ENTRADA";
    public static final String FORM_COMPUESTO = "COMPUESTO";


    private final String name;
    private final String type;
    private final String interfazName;
	
	public BehaviorState(final String name,final String type,final String interfaz) {
		this.name = name;
        this.type = type;
        this.interfazName = interfaz;
    }

	public String getName() {
		return name;
	}

    public String getType() {
        return type;
    }

    public String getInterfazName() {
        return interfazName;
    }
}
