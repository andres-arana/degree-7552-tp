package fiuba.mda.ui.launchers;

import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import com.google.inject.Inject;

import fiuba.mda.model.WizardForm;
import fiuba.mda.ui.main.workspace.ListEditorWizardPage;
import fiuba.mda.ui.main.workspace.FirstWizardPage;
import fiuba.mda.ui.main.workspace.SecondWizardPage;

public class WizardDialogLauncher extends Wizard {
	private FirstWizardPage firstPage;
	private SecondWizardPage secondPage;
	private ListEditorWizardPage thirdPage;
	private ListEditorWizardPage fourthPage;
	private ListEditorWizardPage fifthPage;
	private WizardForm form;
    private boolean wasCanceled = false;

 	/**
	 * Creates a new {@link WizardDialogLauncher} instance
	 * 
	 *
	 */
	@Inject
	public WizardDialogLauncher() {
		super();
		form = new WizardForm();
		firstPage = new FirstWizardPage("Primera página");
		secondPage = new SecondWizardPage("Segunda página");
		thirdPage = new ListEditorWizardPage("Tercera página", "Campos", "Cantidad de campos a agregar:", "Campo:");
		fourthPage = new ListEditorWizardPage("Cuarta página", "Textos", "Cantidad de textos a agregar:", "Texto:");
		fifthPage = new ListEditorWizardPage("Quinta página", "Botones", "Cantidad de botones a agregar:", "Texto del botón:");
        super.setWindowTitle("Wizard para la creación de un formulario");
	}

	@Override
	public boolean performFinish() {
		form.setFormName(this.getFormName());
		form.setExistingPropertiesAdded(this.getExistingPropertiesAdded());
		form.setNewPropertiesAdded(this.getNewPropertiesAdded());
		form.setTextsAdded(this.getTextsAdded());
		form.setButtonsAdded(this.getButtonsAdded());
		
		// Close the wizard dialog
		return true;
	}
    @Override

    public boolean performCancel() {
        this.wasCanceled = true;
        // Close the wizard dialog
        return true;
    }

	@Override
	public void addPages() {
	  addPage(firstPage);
	  addPage(secondPage);
	  addPage(thirdPage);
	  addPage(fourthPage);
	  addPage(fifthPage);
	}
	
	/**
	 * Returns the form name
	 * @return a string containing the name of the form
	 */
	public String getFormName(){
		return firstPage.getFormName();
	}

	public void setFormName(String string){
		firstPage.setFormName(string);
	}	
	
    /**
     * Returns the properties that have been added to the form
     * @return a list with existing properties added to the form
     */
	public List<String> getExistingPropertiesAdded() {
		return secondPage.getExistingPropertiesAdded();
	}

	public void setExistingPropertiesAdded(List<String> list) {
		secondPage.setExistingPropertiesAdded(list);
	}	
	
    /**
     * Returns new properties that have been added to the form
     * @return a list with the new properties added to the form
     */
	public List<String> getNewPropertiesAdded() {
		return thirdPage.getElementsAdded();
	}
	
	public void setNewPropertiesAdded(List<String> list) {
		thirdPage.setElementsAdded(list);
	}	
    /**
     * Returns the texts that have been added to the form
     * @return a list with the texts added to the form
     */
	public List<String> getTextsAdded() {
		return fourthPage.getElementsAdded();
	}

	public void setTextsAdded(List<String> list) {
		fourthPage.setElementsAdded(list);
	}
	
    /**
     * Returns the buttons that have been added to the form
     * @return a list with the buttons added to the form
     */
	public List<String> getButtonsAdded() {
		return fifthPage.getElementsAdded();
	}

	public void setButtonsAdded(List<String> list) {
		fifthPage.setElementsAdded(list);
	}	

	/**
	 * Returns an object that contains all the information gathered in the wizard
	 * @return a form object
	 */
	public WizardForm getForm(){
		return this.form;
	}

    public boolean wasCanceled(){
        return wasCanceled;
    }
}
