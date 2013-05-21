package fiuba.mda.model;

/**
 * Represents an entity in the static model of the software
 */
public class ModelEntity extends ProjectComponent {
	/**
	 * @see ProjectComponent#ProjectComponent(String)
	 */
	public ModelEntity(String name) {
		super(name);
	}

	/**
	 * @see ProjectComponent#ProjectComponent(String)
	 */
	@Override
	public ModelPackage closestOwningPackage() {
		return getParent().closestOwningPackage();
	}

}
