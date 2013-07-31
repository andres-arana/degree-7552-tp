package fiuba.mda.ui.main.workspace;

import fiuba.mda.model.BehaviorRelation;
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
public class EditRelationDialog extends TitleAreaDialog {
    private Text codigoText;
    private String codigo;

    public EditRelationDialog(Shell parentShell,String codigo) {
        super(parentShell);
        if (!StringUtils.isBlank(codigo))this.codigo = codigo;
    }

    @Override
    public void create() {
        super.create();
        // Set the title
        setTitle("Edición de Relación");
        // Set the message
        setMessage("Ingrese la función de la transición", IMessageProvider.INFORMATION);

    }

    @Override
    protected Control createDialogArea(Composite parent) {
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        // layout.horizontalAlignment = GridData.FILL;
        parent.setLayout(layout);


        GridData gridData = null ;

                //Codigo
        gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL_BOTH;
        gridData.heightHint = 200;
        gridData.widthHint = 400;
        Label label5 = new Label(parent, SWT.NONE);
        label5.setText("Codigo");
        codigoText = new Text(parent, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
        codigoText.setLayoutData(gridData);
        if (!StringUtils.isBlank(codigo)) codigoText.setText(codigo);


        return parent;
    }

    private String isValidInput() {
        String valid = "OK";
        if (StringUtils.isBlank(codigo)) return "Al ser una relacion de control, debe ingresar un código para ejecutar";

        return valid;
    }

    @Override
    protected boolean isResizable() {
        return false;
    }

    // We need to have the textFields into Strings because the UI gets disposed
    // and the Text Fields are not accessible any more.
    private void saveInput() {

        codigo = codigoText.getText();


    }

    @Override
    protected void okPressed() {
        saveInput();
        String validInput = isValidInput();
        if (validInput.equals("OK")) super.okPressed();
        else super.setErrorMessage(validInput);
    }



    public String getCodigo() {
        return codigo;
    }


}
