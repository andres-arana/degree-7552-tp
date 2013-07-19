package fiuba.mda.ui.main.workspace;

import fiuba.mda.model.BehaviorRelation;
import fiuba.mda.model.BehaviorState;
import fiuba.mda.model.Representation;
import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.text.TextFragmentBox;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Juan-Asus
 * Date: 05/07/13
 * Time: 01:09
 * To change this template use File | Settings | File Templates.
 */
public class RelationDialog extends TitleAreaDialog {
    List<Representation<BehaviorState>> states;
    List<Representation<BehaviorRelation>> existingRelations;
    private Combo initialStateNameCombo;
    private Combo finalStateNameCombo;
    private Text nameText;
    private Combo tipoCombo;
    private Text codigoText;
    private String codigo;
    private String name;
    private String tipo;
    private String initialStateName;
    private String finalStateName;

    public RelationDialog(Shell parentShell, List<Representation<BehaviorState>> states,List<Representation<BehaviorRelation>> existingRelations) {
        super(parentShell);
        this.states = states;
        this.existingRelations = existingRelations;
    }

    @Override
    public void create() {
        super.create();
        // Set the title
        setTitle("Creación de Relación");
        // Set the message
        setMessage("Seleccion 2 estados para mapear", IMessageProvider.INFORMATION);

    }

    @Override
    protected Control createDialogArea(Composite parent) {
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        // layout.horizontalAlignment = GridData.FILL;
        parent.setLayout(layout);


        GridData gridData = null ;

        //Nombre
        gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;
        Label label1 = new Label(parent, SWT.NONE);
        label1.setText("Nombre");
        nameText = new Text(parent, SWT.NONE);
        nameText.setLayoutData(gridData);


        //Tipo
        gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;
        Label label3 = new Label(parent, SWT.NONE);
        label3.setText("Tipo");
        tipoCombo = new Combo(parent, SWT.READ_ONLY);
        tipoCombo.setItems(new String[]{BehaviorRelation.TIPO_CONTROL,BehaviorRelation.TIPO_FUNCIONAL});
        tipoCombo.setLayoutData(gridData);

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


        List<String> stringList = new ArrayList<>();
        for (Representation<BehaviorState> state : states) {
            stringList.add(state.getEntity().getName());
        }

        String[] strings = stringList.toArray(new String[0]);

        //Estado Inicial
        gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;
        Label label4 = new Label(parent, SWT.NONE);
        label4.setText("Estado Inicial");
        initialStateNameCombo = new Combo(parent, SWT.READ_ONLY);
        initialStateNameCombo.setItems(strings);
        initialStateNameCombo.setLayoutData(gridData);

        //Estado Final
        gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;
        Label label2 = new Label(parent, SWT.NONE);
        label2.setText("Estado Final");
        finalStateNameCombo = new Combo(parent, SWT.READ_ONLY);
        finalStateNameCombo.setItems(strings);
        finalStateNameCombo.setLayoutData(gridData);
        return parent;
    }

    private String isValidInput() {
        String valid = "OK";
        if (StringUtils.isBlank(name)) return "Debe ingresar un nombre";
        if (StringUtils.isBlank(tipo)) return "Debe ingresar un tipo";
        if (tipo.equals(BehaviorRelation.TIPO_FUNCIONAL) && StringUtils.isBlank(codigo)) return "Al ser una relacion de control, debe ingresar un código para ejecutar";
        if (StringUtils.isBlank(initialStateName) || StringUtils.isBlank(finalStateName)) return "Debe seleccionar 2 estados obligatoriamente";
        if (finalStateName.equals(initialStateName)) return "Debe seleccionar 2 estados diferentes";
        for (Representation<BehaviorRelation> relation : existingRelations){
            if (relation.getEntity().hasAll(initialStateName,finalStateName)) return "La Relación ya existe";
        }
        return valid;
    }

    @Override
    protected boolean isResizable() {
        return false;
    }

    // We need to have the textFields into Strings because the UI gets disposed
    // and the Text Fields are not accessible any more.
    private void saveInput() {
        name = nameText.getText();
        tipo = tipoCombo.getText();
        codigo = codigoText.getText();
        initialStateName = initialStateNameCombo.getText();
        finalStateName = finalStateNameCombo.getText();

    }

    @Override
    protected void okPressed() {
        saveInput();
        String validInput = isValidInput();
        if (validInput.equals("OK")) super.okPressed();
        else super.setErrorMessage(validInput);
    }

    public String getInitialStateName() {
        return initialStateName;
    }

    public String getFinalStateName() {
        return finalStateName;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getName() {
        return name;
    }

    public String getTipo() {
        return tipo;
    }
}
