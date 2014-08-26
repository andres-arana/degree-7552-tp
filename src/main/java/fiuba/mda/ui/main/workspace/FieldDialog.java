package fiuba.mda.ui.main.workspace;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

import fiuba.mda.model.GraficInterfaceDiagram;

public class FieldDialog extends TitleAreaDialog {
    private Combo propertyNameCombo;
    private Text propertyNameText;
    private String propertyName;
    private String fieldName;
    private String title;
	private GraficInterfaceDiagram diagram;

    public FieldDialog(Shell parentShell) {
        super(parentShell);
        this.title = "Creación de Campo";
        this.fieldName = "";
        this.propertyName = "";
    }

    public FieldDialog(Shell parentShell, String title) {
        super(parentShell);
        this.title = title;
        this.fieldName = "";
        this.propertyName = "";
    }

    @Override
    public void create() {
        super.create();
        // Set the title
        setTitle(this.title);
        // Set the message
        setMessage("Seleccione la propiedad representada por el campo", IMessageProvider.INFORMATION);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        parent.setLayout(layout);

        GridData gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;

        Label label1 = new Label(parent, SWT.NONE);
        label1.setText("Nombre");

        propertyNameText = new Text(parent, SWT.NONE);
        propertyNameText.setLayoutData(gridData);
        propertyNameText.setText(this.fieldName);

        GridData gridData2 = new GridData();
        gridData2.grabExcessHorizontalSpace = true;
        gridData2.horizontalAlignment = GridData.FILL;

        Label label2 = new Label(parent, SWT.NONE);
        label2.setText("Propiedad con que se relaciona");
        String[] items = diagram.getAccessibleProperties();

        propertyNameCombo = new Combo(parent, SWT.READ_ONLY);
        propertyNameCombo.setItems(items);
        propertyNameCombo.setLayoutData(gridData2);

        for (int i = 0; i<items.length; i++) {
            if (items[i].equals(this.propertyName)) {
                propertyNameCombo.select(i);
                break;
            }
        };

        propertyNameText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                if (!propertyNameText.getText().equals(""))
                    propertyNameCombo.deselectAll();
            }
        });

        propertyNameCombo.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                propertyNameText.setText("");
            }
        });

        return parent;
    }

    private String isValidInput() {
    	if (StringUtils.isBlank(fieldName) && StringUtils.isBlank(propertyName)){
    		return "Complete un nombre o una propiedad";
    	}
    	if (!StringUtils.isBlank(fieldName) && !StringUtils.isBlank(propertyName)){
    		return "Debe completar un nombre o una propiedad, no ambas";
    	}
        return "OK";
    }

    @Override
    protected boolean isResizable() {
        return false;
    }

    // We need to have the textFields into Strings because the UI gets disposed
    // and the Text Fields are not accessible any more.
    private void saveInput() {
    	propertyName = propertyNameCombo.getText();
    	fieldName = propertyNameText.getText();
    }

    @Override
    protected void okPressed() {
        saveInput();
        String validMsg = isValidInput();
        if (validMsg.equals("OK")) super.okPressed();
        else
        super.setErrorMessage(validMsg);
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setPropertyName(String value) {
        this.propertyName = value;
    }

    public void setFieldName(String value) {
        this.fieldName = value;
    }

	public void setParentComponent(GraficInterfaceDiagram diagram) {
		this.diagram = diagram;	
	}    
}
