package fiuba.mda.model;

public class BehaviorText implements java.io.Serializable {
	private String name;

	public BehaviorText(String name) {
		this.name = name;
    }

	public String getName() {
		return name;
	}

	public void setName(String value) {
		this.name = value;
	}
}
