package fiuba.mda.model;

public class ModelPackage extends ProjectComponent {

	public ModelPackage(String name) {
		super(name);
	}

	@Override
	public ModelPackage closestOwningPackage() {
		return this;
	}

}
