package fiuba.mda.model;

public class BehaviorButton implements java.io.Serializable {
	private String name;

	public BehaviorButton(final String name) {
		this.name = name;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
