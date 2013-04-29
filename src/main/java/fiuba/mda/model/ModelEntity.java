package fiuba.mda.model;

public class ModelEntity extends ProjectComponent {

	public ModelEntity(String name) {
		super(name);
	}

	@Override
	public ModelPackage closestOwningPackage() {
		return getParent().closestOwningPackage();
	}

}
