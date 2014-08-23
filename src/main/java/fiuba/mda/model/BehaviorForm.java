package fiuba.mda.model;

import java.util.List;

public class BehaviorForm implements java.io.Serializable {

	private String formName;
	private List<String> texts;
	private List<String> buttons;
	private List<String> newFields;
	private List<String> existingFields;
	
	public BehaviorForm(String formName, List<String> existingFields, List<String> newFields, 
			List<String> texts, List<String> buttons){
		this.formName = formName;
		this.texts = texts;
		this.buttons = buttons;
		this.newFields = newFields;
		this.existingFields = existingFields;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public List<String> getTexts() {
		return texts;
	}

	public void setTexts(List<String> texts) {
		this.texts = texts;
	}

	public List<String> getButtons() {
		return buttons;
	}

	public void setButtons(List<String> buttons) {
		this.buttons = buttons;
	}

	public List<String> getNewFields() {
		return newFields;
	}

	public void setNewFields(List<String> newFields) {
		this.newFields = newFields;
	}

	public List<String> getExistingFields() {
		return existingFields;
	}

	public void setExistingFields(List<String> existingFields) {
		this.existingFields = existingFields;
	}
	
}
