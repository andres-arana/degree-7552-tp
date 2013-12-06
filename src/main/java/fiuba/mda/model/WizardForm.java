package fiuba.mda.model;

import java.util.List;

/**
 * Contains the information gathered from the wizard to generate a form
 * @author scampa
 *
 */
public class WizardForm {
	
	private String formName;
	private List<String> existingPropertiesAdded;
	private List<String> newPropertiesAdded;
	private List<String> textsAdded;
	private List<String> buttonsAdded;
	
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	public List<String> getExistingPropertiesAdded() {
		return existingPropertiesAdded;
	}
	
	public void setExistingPropertiesAdded(List<String> existingPropertiesAdded) {
		this.existingPropertiesAdded = existingPropertiesAdded;
	}
	
	public List<String> getNewPropertiesAdded() {
		return newPropertiesAdded;
	}
	
	public void setNewPropertiesAdded(List<String> newPropertiesAdded) {
		this.newPropertiesAdded = newPropertiesAdded;
	}
	
	public List<String> getTextsAdded() {
		return textsAdded;
	}
	
	public void setTextsAdded(List<String> textsAdded) {
		this.textsAdded = textsAdded;
	}
	
	public List<String> getButtonsAdded() {
		return buttonsAdded;
	}
	
	public void setButtonsAdded(List<String> buttonsAdded) {
		this.buttonsAdded = buttonsAdded;
	}
	
}
