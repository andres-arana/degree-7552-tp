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

import com.google.common.base.Optional;
import com.google.inject.Inject;

public class ButtonDialog extends TitleAreaDialog {
	
    private String labelString;
    private Text text;
    private String title;

	public ButtonDialog(Shell parentShell) {
        super(parentShell);
        this.title = "Creación de Botón";
	}

    public ButtonDialog(Shell parentShell, String title) {
        super(parentShell);
        this.title = title;
    }
	
    @Override
    public void create() {
        super.create();
        // Set the title
        setTitle(title);
        // Set the message
        setMessage("Ingrese la etiqueta del botón", IMessageProvider.INFORMATION);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;

        parent.setLayout(layout);

        GridData gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;

        Label buttonLabel = new Label(parent, SWT.NONE);
        buttonLabel.setText("Etiqueta");
        
        text = new Text(parent, SWT.NONE);
        text.setLayoutData(gridData);

        return parent;
    }
    
    private String isValidInput() {
        if (StringUtils.isBlank(labelString)){
            return "Debe escribir una etiqueta";
        }
        return "OK";
    }

    @Override
    protected boolean isResizable() {
        return false;
    }

    private void saveInput() {
    	labelString = text.getText();
    }

    @Override
    protected void okPressed() {
        saveInput();
        String validMsg = isValidInput();
        if (validMsg.equals("OK")) super.okPressed();
        else
        super.setErrorMessage(validMsg);
    }

    public String getLabelString() {
        return labelString;
    }
}
