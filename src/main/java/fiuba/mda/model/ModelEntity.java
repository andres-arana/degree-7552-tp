package fiuba.mda.model;

/**
 * Represents an entity in the static model of the software
 */
public class ModelEntity extends AbstractLeafProjectComponent {
	private static final long serialVersionUID = 123858230538320339L;

	/**
	 * Creates a new {@link ModelEntity} instance
	 * 
	 * @param name
	 *            the name which represents this entity
	 */
	public ModelEntity(String name) {
		super(name);
	}

	@Override
	public void accept(ProjectComponentVisitor visitor) {
		visitor.visit(this);
	}
}
