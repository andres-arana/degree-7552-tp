package fiuba.mda.ui.actions;

import com.google.inject.Inject;
import fiuba.mda.model.Application;
import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.ModelPackage;
import org.eclipse.jface.action.Action;

/**
 * Created with IntelliJ IDEA.
 * User: Juan-Asus
 * Date: 26/06/13
 * Time: 00:43
 * To change this template use File | Settings | File Templates.
 */
public class DeleteBehaviorDiagramAction extends Action {

    private final Application model;

    private final BehaviorDiagram modelPackageToDelete;

    @Inject
    public DeleteBehaviorDiagramAction(Application model, BehaviorDiagram modelPackageToDelete) {
        this.model = model;
        this.modelPackageToDelete = modelPackageToDelete;
        setupPresentation();
    }


    private void setupPresentation() {
        setText("Borrar");
    }

    @Override
    public void run() {
       // model.getActivePackage().removeChildren(modelPackageToDelete);
    }
}
