package fiuba.mda.ui.main.workspace;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TextDialog extends TitleAreaDialog {
	
    private Text text;
    private String textString;
    private String title;

	public TextDialog(Shell parentShell) {
		super(parentShell);
        this.title = "Creación de Texto";
        this.textString = "";
	}

    public TextDialog(Shell parentShell, String title) {
        super(parentShell);
        this.title = title;
        this.textString = "";
    }
	
    @Override
    public void create() {
        super.create();
        // Title
        setTitle(this.title);
        // Set the message
        setMessage("Ingrese el texto", IMessageProvider.INFORMATION);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;

        parent.setLayout(layout);

        GridData gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;

        Label textLabel = new Label(parent, SWT.NONE);
        textLabel.setText("Texto");

        text = new Text(parent, SWT.NONE);
        text.setLayoutData(gridData);
        text.setText(this.textString);

        return parent;
    }
    
    private String isValidInput() {
        if (StringUtils.isBlank(textString)){
            return "Debe escribir un texto";
        }
        return "OK";
    }

    @Override
    protected boolean isResizable() {
        return false;
    }

    private void saveInput() {
        textString = text.getText();
    }

    @Override
    protected void okPressed() {
        saveInput();
        String validMsg = isValidInput();
        if (validMsg.equals("OK")) super.okPressed();
        else
        super.setErrorMessage(validMsg);
    }

    public String getTextString() {
        return textString;
    }

    public void setTextString(String value) {
        this.textString = value;
    }
    
    public Text getText(){
    	return text;
    }

}
