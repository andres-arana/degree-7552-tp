package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;

import fiuba.mda.model.Application;
import fiuba.mda.model.ModelPackage;
import fiuba.mda.ui.launchers.EditorLauncher;
import fiuba.mda.ui.main.tree.ComponentEditorVisitor;

/**
 * Created with IntelliJ IDEA.
 * User: Juan-Asus
 * Date: 26/06/13
 * Time: 00:43
 * To change this template use File | Settings | File Templates.
 */
public class EditPackageAction extends Action {
    private final ModelPackage modelPackage;

    private final Provider<ComponentEditorVisitor> editorProvider;


    @Inject
    public EditPackageAction(Application model, ModelPackage modelPackage, Provider<ComponentEditorVisitor> editorProvider) {
        this.modelPackage = modelPackage;
        this.editorProvider = editorProvider;
        setupPresentation();
    }


    private void setupPresentation() {
        setText("Editar");
    }

    @Override
    public void run() {
        Optional<EditorLauncher> controller = editorProvider.get()
                .controllerFor(modelPackage);
        if (controller.isPresent()) {
            controller.get().launch(modelPackage);
        }
    }
}
