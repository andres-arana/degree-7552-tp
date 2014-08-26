package fiuba.mda.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an entity in the static model of the software
 */
public class ModelEntity extends AbstractLeafProjectComponent {
	private static final long serialVersionUID = 123858230538320339L;

	public enum Kind {
		MAESTRA_COSA, MAESTRA_DOMINIO, TRANSACCIONAL_HISTORICA, TRANSACCIONAL_PROGRAMADA

	}

	public static final String[] KINDS = { "MAESTRA_COSA", "MAESTRA_DOMINIO",
			"TRANSACCIONAL_HISTORICA", "TRANSACCIONAL_PROGRAMADA" };

	private Kind kind;
	private final Set<ModelAttribute> attributes = new HashSet<>();

	/**
	 * Creates a new {@link ModelEntity} instance
	 * 
	 * @param name
	 *            the name which represents this entity
	 */
	public ModelEntity(String name) {
		super(name);
		this.kind = Kind.MAESTRA_COSA;
	}

	public Kind getKind() {
		return kind;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
	}

	public Set<ModelAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public void accept(ProjectComponentVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public Collection<? extends String> getOwnProperties() {
		ArrayList<String> result = new ArrayList<String>();
		
		for (ModelAttribute a : getAttributes()) {
			result.add(this.getQualifiedName() + " - " + a.getName());
		}
		
		return result;
	}
}
