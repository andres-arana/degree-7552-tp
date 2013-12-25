package fiuba.mda.ui.main.workspace;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class FirstWizardPage extends WizardPage {
	
	private Text formName;
	private Composite container;

	public FirstWizardPage(String pageName) {
		super(pageName);
		this.setTitle("Nombre del formulario");
	}

	@Override
	public void createControl(Composite parent) {
	    container = new Composite(parent, SWT.NONE);
	    GridLayout layout = new GridLayout();
	    container.setLayout(layout);
	    layout.numColumns = 2;
	    Label formNameLabel = new Label(container, SWT.NONE);
	    formNameLabel.setText("Name:");
	    formName = new Text(container, SWT.BORDER | SWT.SINGLE);
	    formName.setText("");
	    formName.addKeyListener(new KeyListener() {

	      @Override
	      public void keyPressed(KeyEvent e) {
              if (!formName.getText().isEmpty()) {
                  setPageComplete(true);
              } else {
                  setPageComplete(false);
              }
	      }

	      @Override
	      public void keyReleased(KeyEvent e) {
	        if (!formName.getText().isEmpty()) {
	          setPageComplete(true);
	        } else {
                setPageComplete(false);
            }
	      }

	    });

	    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
	    formName.setLayoutData(gd);

	    // Required to avoid an error in the system
	    setControl(container);
	    setPageComplete(false);
	}

	/**
	 * Returns the name of the form
	 * @return a string containing the name of the form
	 */
	public String getFormName() {
		return formName.getText();
	}
	
}
