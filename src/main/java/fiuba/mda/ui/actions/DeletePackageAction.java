package fiuba.mda.ui.actions;

import com.google.inject.Inject;
import fiuba.mda.model.Application;
import fiuba.mda.model.ModelPackage;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Created with IntelliJ IDEA.
 * User: Juan-Asus
 * Date: 26/06/13
 * Time: 00:43
 * To change this template use File | Settings | File Templates.
 */
public class DeletePackageAction extends Action {

    private final Application model;

    private final ModelPackage modelPackageToDelete;

    @Inject
    public DeletePackageAction(Application model, ModelPackage modelPackageToDelete) {
        this.model = model;
        this.modelPackageToDelete = modelPackageToDelete;
        setupPresentation();
    }


    private void setupPresentation() {
        setText("Borrar");
    }

    @Override
    public void run() {

        MessageDialog dialog = new MessageDialog(fiuba.mda.Application.getShell(), "Confirmación de Borrado de Paquete", null,
                "Desea Borrar?", MessageDialog.CONFIRM, new String[] { "Aceptar",
                "Cancelar"}, 0);

        int result = dialog.open();

        if (result == 0){
            ModelPackage parentPackage = (ModelPackage)modelPackageToDelete.getParent();
            parentPackage.removeChildren(modelPackageToDelete);
            model.clearActivePackage();
        }

    }
}
