package fiuba.mda.ui.main.workspace;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

public class ThirdWizardPage extends WizardPage{

	private Composite container;
	
	private Spinner newPropertiesQty;
	
	private List<Composite> newPropertiesAddedUI; // list that contains every pair Label+Property UI added to the form
	private List<String> newPropertiesAdded;
	
	public ThirdWizardPage(String pageName) {
		super(pageName);
		this.setTitle("Campos nuevos");
		newPropertiesAddedUI = new ArrayList<Composite>();
		newPropertiesAdded = new ArrayList<String>();
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
    
	    GridLayout layout = new GridLayout();
	    container.setLayout(layout);
	    layout.numColumns = 2;
	    Label fieldsQtyLabel = new Label(container, SWT.NONE);
	    fieldsQtyLabel.setText("Cantidad de campos a agregar:");
	    newPropertiesQty = new Spinner(container, SWT.BORDER);
	    newPropertiesQty.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {		
				int currentValue = ((Spinner)e.getSource()).getSelection();
				
				// "Up" spinner button
				if (currentValue > newPropertiesAddedUI.size()){ 
					// Add label + property field
					Composite miniComposite = getLabelAndTextfield(container); // [0] = label, [1] = textfield
					newPropertiesAddedUI.add(miniComposite);
									
				} else{ // "Down" spinner button
					// Remove the component from the main composite
					if (newPropertiesAddedUI.size() > 0){
						Composite miniCompositeToRemove = newPropertiesAddedUI.get(newPropertiesAddedUI.size()-1);

						// Get the textfield that will be removed from the UI an delete its selection from existingPropertiesAddedUI list
						Text textFieldToRemove = (Text)miniCompositeToRemove.getChildren()[1]; // [0] = label, [1] = textfield

						// Remove the previously added text as the textfield has been removed from the UI
						newPropertiesAdded.remove(textFieldToRemove.getText()); 

						// Remove textfield from the UI
						newPropertiesAddedUI.get(newPropertiesAddedUI.size()-1).dispose();
						newPropertiesAddedUI.remove(newPropertiesAddedUI.size()-1);

					}
				}
				
				container.layout(); // refreshs the container
			}
	    	
	    });
	   	    
	    // Required to avoid an error in the system
	    setControl(container);
	    setPageComplete(true);
	}

    
    /**
     * 
     * @return a pair label-textfield to complete with a new property to add to the form
     */
    private Composite getLabelAndTextfield(Composite parent){
    	Composite miniComposite = new Composite(parent, SWT.NONE);
    	GridLayout layout = new GridLayout();
    	miniComposite.setLayout(layout);
    	layout.numColumns = 2;
    	
		Label addFieldLabel = new Label(miniComposite, SWT.NONE);
		addFieldLabel.setText("Campo:");

		Text textField = new Text(miniComposite, SWT.SINGLE | SWT.BORDER);
		textField.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				String newProperty = ((Text)e.getSource()).getText();
				newPropertiesAdded.add(newProperty); // TODO: Validate repeated elements? 
			}
			
		});

    	return miniComposite;
    }

    /**
     * Returns new properties that have been added to the form
     * @return a list with the new properties added to the form
     */
	public List<String> getNewPropertiesAdded() {
		return newPropertiesAdded;
	}
    
}
