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

public class FourthWizardPage extends WizardPage{
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
	
	private Spinner textsQty;
	
	private List<Composite> textsAddedUI; // list that contains every pair Label+Text UI added to the form
	private List<String> textsAdded;
	
	public FourthWizardPage(String pageName) {
		super(pageName);
		this.setTitle("Textos");
		textsAddedUI = new ArrayList<Composite>();
		textsAdded = new ArrayList<String>();
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
    
	    GridLayout layout = new GridLayout();
	    container.setLayout(layout);
	    layout.numColumns = 2;
	    Label fieldsQtyLabel = new Label(container, SWT.NONE);
	    fieldsQtyLabel.setText("Cantidad de textos a agregar:");
	    textsQty = new Spinner(container, SWT.BORDER);
	    textsQty.setSelection(textsAdded.size());
	    textsQty.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {		
				int currentValue = ((Spinner)e.getSource()).getSelection();

				if (currentValue > textsAdded.size()){
					textsAdded.add("");
				} else {
					textsAdded.remove(textsAdded.size()-1);
				} 

				for (Composite composite : textsAddedUI) {
					composite.dispose();
				}

				textsAddedUI.clear();
				for (int i = 0; i < textsAdded.size(); i++) {
	   				String property = textsAdded.get(i);
					textsAddedUI.add(getLabelAndTextfield(container, property, new UpdateElementOnList(i, textsAdded)));
				} 

				container.layout(); // refreshs the container
			}
	    	
	    });

	   	    
	   	for (int i = 0; i < textsAdded.size(); i++) {
	   		String property = textsAdded.get(i);
			textsAddedUI.add(getLabelAndTextfield(container, property, new UpdateElementOnList(i, textsAdded)));
		}
	   	    
	    // Required to avoid an error in the system
	    setControl(container);
	    setPageComplete(true);
	}

    
    /**
     * 
     * @return a pair label-textfield to complete with a text to add to the form
     */
    private Composite getLabelAndTextfield(Composite parent, String property, final IPropertyChanged propertyChanged){
    	Composite miniComposite = new Composite(parent, SWT.NONE);
    	GridLayout layout = new GridLayout();
    	miniComposite.setLayout(layout);
    	layout.numColumns = 2;
    	
		Label addFieldLabel = new Label(miniComposite, SWT.NONE);
		addFieldLabel.setText("Texto:");

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
     * Returns the texts that have been added to the form
     * @return a list with the texts added to the form
     */
	public List<String> getTextsAdded() {
		return textsAdded;
	}

	public void setTextsAdded(List<String> list) {
		this.textsAdded = list;
	}
        
}
