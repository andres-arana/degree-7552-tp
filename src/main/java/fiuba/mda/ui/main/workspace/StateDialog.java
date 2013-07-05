package fiuba.mda.ui.main.workspace;

import fiuba.mda.model.BehaviorState;
import fiuba.mda.model.Representation;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Juan-Asus
 * Date: 05/07/13
 * Time: 01:09
 * To change this template use File | Settings | File Templates.
 */
public class StateDialog extends TitleAreaDialog {
    private Combo formTypeNameCombo;
    private Text formNameText;
    private String formTypeName;
    private String formName;

    public StateDialog(Shell parentShell) {
        super(parentShell);
    }

    @Override
    public void create() {
        super.create();
        // Set the title
        setTitle("Creación de Estado");
        // Set the message
        setMessage("Seleccione el tipo de formulario", IMessageProvider.INFORMATION);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        // layout.horizontalAlignment = GridData.FILL;
        parent.setLayout(layout);

        GridData gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;

        Label label1 = new Label(parent, SWT.NONE);
        label1.setText("Nombre");

        formNameText = new Text(parent, SWT.NONE);
        formNameText.setLayoutData(gridData);

        GridData gridData2 = new GridData();
        gridData2.grabExcessHorizontalSpace = true;
        gridData2.horizontalAlignment = GridData.FILL;

        Label label2 = new Label(parent, SWT.NONE);
        label2.setText("Tipo");
        String[] strings = {BehaviorState.FORM_COMPUESTO,BehaviorState.FORM_ENTRADA,BehaviorState.FORM_SALIDA};

        formTypeNameCombo = new Combo(parent, SWT.READ_ONLY);
        formTypeNameCombo.setItems(strings);
        formTypeNameCombo.setLayoutData(gridData2);

        return parent;
    }

    private String isValidInput() {
        if (StringUtils.isBlank(formName)){
            return "Debe elegir un nombre";
        }
        if (StringUtils.isBlank(formTypeName)){
            return "Debe elegir un tipo";
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
        formTypeName = formTypeNameCombo.getText();
        formName = formNameText.getText();
    }

    @Override
    protected void okPressed() {
        saveInput();
        String validMsg = isValidInput();
        if (validMsg.equals("OK")) super.okPressed();
        else
        super.setErrorMessage(validMsg);
    }

    public String getFormTypeName() {
        return formTypeName;
    }

    public String getFormName() {
        return formName;
    }
}