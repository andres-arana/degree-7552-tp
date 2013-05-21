package fiuba.mda.model;

/**
 * Represents a package from the software being modeled
 */
public class ModelPackage extends ProjectComponent {
	/**
	 * @see ProjectComponent#ProjectComponent(String)
	 */
	public ModelPackage(String name) {
		super(name);
	}

	/**
	 * @see ProjectComponent#ProjectComponent(String)
	 */
	@Override
	public ModelPackage closestOwningPackage() {
		return this;
	}

}
