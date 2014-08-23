package fiuba.mda.model;

public class BehaviorField implements java.io.Serializable {
	private String fieldName;
	private String propertyName;

	public BehaviorField(String fieldName, String propertyName) {
		this.fieldName = fieldName;
		this.propertyName = propertyName;
    }

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String value) {
		this.fieldName = value;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String value) {
		this.propertyName = value;
	}

	public String getName() {
		return !this.fieldName.equals("") ? this.fieldName:this.propertyName;		
	}
}
