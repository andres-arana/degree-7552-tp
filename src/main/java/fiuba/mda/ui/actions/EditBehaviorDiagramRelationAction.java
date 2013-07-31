package fiuba.mda.ui.actions;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.BehaviorRelation;
import fiuba.mda.model.Representation;
import fiuba.mda.ui.launchers.SimpleDialogLauncher;
import fiuba.mda.ui.main.workspace.EditRelationDialog;
import fiuba.mda.ui.main.workspace.RelationDialog;
import fiuba.mda.ui.utilities.ImageLoader;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;

/**
 * Created with IntelliJ IDEA.
 * User: JuanchoM
 * Date: 31/07/13
 * Time: 13:50
 * To change this template use File | Settings | File Templates.
 */
public class EditBehaviorDiagramRelationAction extends Action {
    BehaviorRelation relation;
    private final SimpleDialogLauncher dialog;
    private final Shell shell;

    /**
     * Creates a new {@link NewBehaviorDiagramStateAction} instance
     *
     * @param imageLoader
     *            the image loader used to provide the image of this action
     * @param dialog
     */
    @Inject
    public EditBehaviorDiagramRelationAction(final Shell shell,
                                            final ImageLoader imageLoader, SimpleDialogLauncher dialog) {
        this.shell = shell;
        this.dialog = dialog;
        setupPresentation(imageLoader);
    }

    private void setupPresentation(final ImageLoader imageLoader) {
       /* setText("Editar Relación");
        setToolTipText("Editar una relación en el diagrama actual");
        setImageDescriptor(imageLoader.descriptorOf("link_add"));*/
    }

    /**
     * Binds this action to work on a given {@link BehaviorDiagram} instance
     *
     * @param  relation
     *            the diagram to bind this action to
     * @return this for method chaining
     */
    public EditBehaviorDiagramRelationAction boundTo(
            final BehaviorRelation relation) {
        this.relation = relation;
        return this;
    }

    @Override
    public void run() {
        if (!isValidSituation())
            return;
        EditRelationDialog dialogo = new EditRelationDialog(shell,relation.getCodigo());
        Optional<String> stringOptional = dialog.showDialog(dialogo);
        if (stringOptional.isPresent()) {
            String codigo = dialogo.getCodigo();
            relation.setCodigo(codigo);
        }
    }

    private boolean isValidSituation() {
        if (relation == null) {
            dialog.showError("Relacion Nula");
            return false;
        }
        if (relation.getTipo().equals(BehaviorRelation.TIPO_CONTROL)) {
            dialog.showError("No se puede asignar una función a una transición del tipo control");
            return false;
        }
        return true;
    }
}
