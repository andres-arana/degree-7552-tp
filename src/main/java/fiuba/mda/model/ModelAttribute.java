package fiuba.mda.model;

import java.io.Serializable;

public class ModelAttribute implements Serializable {
	private static final long serialVersionUID = -3163800935825580493L;

	public enum Kind {
		CARACTERIZACION, DERIVADO_COPIA, DERIVADO_CALCULO
	}

	public static final String[] KINDS = { "CARACTERIZACION", "DERIVADO_COPIA",
			"DERIVADO_CALCULO" };

	private String name;
	private Kind kind;
	protected String min = "1";
	protected String max = "1";

	protected ModelAttribute original;

	protected String formula;

	private ModelEntity entity;

	public ModelAttribute(final ModelEntity entity, final String nombre) {
		this.name = nombre;
		this.kind = Kind.CARACTERIZACION;
		this.entity = entity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Kind getKind() {
		return kind;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public ModelAttribute getOriginal() {
		return original;
	}

	public void setOriginal(ModelAttribute original) {
		this.original = original;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public AbstractProjectComponent getEntity() {
		return entity;
	}
}
