package fiuba.mda.model;

/**
 * Represents an entity in the static model of the software
 */
public class ModelEntity extends AbstractLeafProjectComponent {
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
