package fiuba.mda.ui.main.workspace;

import fiuba.mda.model.BehaviorState;
import fiuba.mda.model.Representation;
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
    private Combo initialStateNameCombo;
    private Combo finalStateNameCombo;
    private String initialStateName;
    private String finalStateName;

    public RelationDialog(Shell parentShell, List<Representation<BehaviorState>> states) {
        super(parentShell);
        this.states = states;
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

        List<String> stringList = new ArrayList<>();
        for (Representation<BehaviorState> state : states) {
             stringList.add(state.getEntity().getName());
        }

        String[] strings = stringList.toArray(new String[0]);

        // The text fields will grow with the size of the dialog
        GridData gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;

        Label label1 = new Label(parent, SWT.NONE);
        label1.setText("Estado Inicial");


        initialStateNameCombo = new Combo(parent, SWT.READ_ONLY);
        initialStateNameCombo.setItems(strings);
        initialStateNameCombo.setLayoutData(gridData);

        Label label2 = new Label(parent, SWT.NONE);
        label2.setText("Estado Final");
        // You should not re-use GridData
        gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;
        finalStateNameCombo = new Combo(parent, SWT.READ_ONLY);
        finalStateNameCombo.setItems(strings);
        finalStateNameCombo.setLayoutData(gridData);
        return parent;
    }

    private boolean isValidInput() {
        boolean valid = true;
        return valid;
    }

    @Override
    protected boolean isResizable() {
        return false;
    }

    // We need to have the textFields into Strings because the UI gets disposed
    // and the Text Fields are not accessible any more.
    private void saveInput() {
        initialStateName = initialStateNameCombo.getText();
        finalStateName = finalStateNameCombo.getText();

    }

    @Override
    protected void okPressed() {
        saveInput();
        super.okPressed();
    }

    public String getInitialStateName() {
        return initialStateName;
    }

    public String getFinalStateName() {
        return finalStateName;
    }
}
