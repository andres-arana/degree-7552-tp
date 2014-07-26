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

public class ListEditorWizardPage extends WizardPage{

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
	
	private Spinner elementsQty;
	
	private List<Composite> elementsAddedUI; // list that contains every pair Label+Text UI added to the form
	private List<String> elementsAdded;

	private String qtyTitle;
	private String labelTitle;
	
	public ListEditorWizardPage(String pageName, String title, String qtyTitle, String labelTitle) {
		super(pageName);
		this.setTitle(title);
		elementsAddedUI = new ArrayList<Composite>();
		elementsAdded = new ArrayList<String>();
		this.qtyTitle = qtyTitle;
		this.labelTitle = labelTitle;
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
    
	    GridLayout layout = new GridLayout();
	    container.setLayout(layout);
	    layout.numColumns = 2;
	    Label fieldsQtyLabel = new Label(container, SWT.NONE);
	    fieldsQtyLabel.setText(this.qtyTitle);
	    elementsQty = new Spinner(container, SWT.BORDER);
	   	elementsQty.setSelection(elementsAdded.size());
	    elementsQty.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {		
				int currentValue = ((Spinner)e.getSource()).getSelection();

				if (currentValue > elementsAdded.size()){
					elementsAdded.add("");
				} else {
					elementsAdded.remove(elementsAdded.size()-1);
				} 

				for (Composite composite : elementsAddedUI) {
					composite.dispose();
				}

				elementsAddedUI.clear();
				for (int i = 0; i < elementsAdded.size(); i++) {
	   				String property = elementsAdded.get(i);
					elementsAddedUI.add(getLabelAndTextfield(container, property, new UpdateElementOnList(i, elementsAdded)));
				} 

				container.layout(); // refreshs the container
			}
	    	
	    });
   
	   	for (int i = 0; i < elementsAdded.size(); i++) {
	   		String property = elementsAdded.get(i);
			elementsAddedUI.add(getLabelAndTextfield(container, property, new UpdateElementOnList(i, elementsAdded)));
		}

	    // Required to avoid an error in the system
	    setControl(container);
	    setPageComplete(true);
	}

    
    /**
     * 
     * @return a pair label-textfield to complete with a text to add a element to the form
     */
    private Composite getLabelAndTextfield(Composite parent, String property, final IPropertyChanged propertyChanged){
    	Composite miniComposite = new Composite(parent, SWT.NONE);
    	GridLayout layout = new GridLayout();
    	miniComposite.setLayout(layout);
    	layout.numColumns = 2;
    	
		Label addFieldLabel = new Label(miniComposite, SWT.NONE);
		addFieldLabel.setText(this.labelTitle);

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
			}
			
		});

    	return miniComposite;
    }

    /**
     * Returns the labels of the elements that have been added to the form
     * @return a list with the labels of the elements added to the form
     */
	public List<String> getElementsAdded() {
		return elementsAdded;
	}

	public void setElementsAdded(List<String> list) {
		this.elementsAdded = list;
	}
}
