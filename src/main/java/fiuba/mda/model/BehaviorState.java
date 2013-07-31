package fiuba.mda.model;

public class BehaviorState {
    public static final String FORM_SALIDA = "SALIDA";
    public static final String FORM_ENTRADA = "ENTRADA";
    public static final String FORM_COMPUESTO = "COMPUESTO";


    private final String name;
    private final String type;
    private final GraficInterfaceDiagram interfazGrafica;
	
	public BehaviorState(final String name, final String type, GraficInterfaceDiagram interfazGrafica) {
		this.name = name;
        this.type = type;
        this.interfazGrafica = interfazGrafica;

    }

	public String getName() {
		return name;
	}

    public String getType() {
        return type;
    }

    public GraficInterfaceDiagram getInterfazGrafica() {
        return interfazGrafica;
    }
}
