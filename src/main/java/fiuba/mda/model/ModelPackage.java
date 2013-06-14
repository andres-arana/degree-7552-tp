package fiuba.mda.model;

/**
 * Represents a package from the software being modeled
 */
public class ModelPackage extends ProjectComponent {
	/**
	 * Creates a new {@link ModelPackage} instance
	 * 
	 * @param name
	 *            the name which represents this package
	 */
	public ModelPackage(String name) {
		super(name);
	}

	@Override
	public ModelPackage closestOwningPackage() {
		return this;
	}

}
