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


	private interface IPropertyChanged {
		void changed(String string);
	};

	private class UpdateElementOnList implements IPropertyChanged {
		private int index;
		private List<String> list;

		UpdateElementOnList(int index, List<String> list) {
			this.index = index;
			this.list = list;
		}

		public void changed(String string) {
			list.set(index, string);
		}
	}

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
	   	newPropertiesQty.setSelection(newPropertiesAdded.size());
	    newPropertiesQty.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {		
				int currentValue = ((Spinner)e.getSource()).getSelection();

				if (currentValue > newPropertiesAdded.size()){
					newPropertiesAdded.add("");
				} else {
					newPropertiesAdded.remove(newPropertiesAdded.size()-1);
				} 

				for (Composite composite : newPropertiesAddedUI) {
					composite.dispose();
				}

				newPropertiesAddedUI.clear();
				for (int i = 0; i < newPropertiesAdded.size(); i++) {
	   				String property = newPropertiesAdded.get(i);
					newPropertiesAddedUI.add(getLabelAndTextfield(container, property, new UpdateElementOnList(i, newPropertiesAdded)));
				} 

				container.layout(); // refreshs the container
			}
	    	
	    });
	   	    
	   	for (int i = 0; i < newPropertiesAdded.size(); i++) {
	   		String property = newPropertiesAdded.get(i);
			newPropertiesAddedUI.add(getLabelAndTextfield(container, property, new UpdateElementOnList(i, newPropertiesAdded)));
		}

	    // Required to avoid an error in the system
	    setControl(container);
	    setPageComplete(true);
	}

    
    /**
     * 
     * @return a pair label-textfield to complete with a new property to add to the form
     */
    private Composite getLabelAndTextfield(Composite parent, String property, final IPropertyChanged propertyChanged){
    	Composite miniComposite = new Composite(parent, SWT.NONE);
    	GridLayout layout = new GridLayout();
    	miniComposite.setLayout(layout);
    	layout.numColumns = 2;
    	
		Label addFieldLabel = new Label(miniComposite, SWT.NONE);
		addFieldLabel.setText("Campo:");

		Text textField = new Text(miniComposite, SWT.SINGLE | SWT.BORDER);
		textField.setText(property);
		textField.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				String newProperty = ((Text)e.getSource()).getText();
				propertyChanged.changed(newProperty);
				//newPropertiesAdded.add(newProperty); // TODO: Validate repeated elements? 
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

	public void setNewPropertiesAdded(List<String> list) {
		this.newPropertiesAdded = list;
	}	
    
}
