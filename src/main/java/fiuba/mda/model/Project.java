package fiuba.mda.model;

public class Project {
	private String name;
	private final ModelPackage rootPackage;

	public Project(final String name, final ModelPackage rootPackage) {
		this.name = name;
		this.rootPackage = rootPackage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ModelPackage getRootPackage() {
		return rootPackage;
	}
}
