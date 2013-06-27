package fiuba.mda.ui.launchers.editors;

import fiuba.mda.model.Application;
import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.ModelAspect;
import fiuba.mda.ui.main.MainWindow;
import org.eclipse.jface.dialogs.IInputValidator;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.ModelPackage;
import fiuba.mda.ui.actions.validators.NameValidator;
import fiuba.mda.ui.launchers.SimpleDialogLauncher;

/**
 * {@link EditorLauncher} implementation which allows editing a package
 */
@Singleton
public class BehaviorDiagramEditLauncher extends BaseLauncher<BehaviorDiagram> {
    private final Application model;
    private final MainWindow mainWindow;
    private final SimpleDialogLauncher dialogs;
    private final IInputValidator dialogNameValidator;

    /**
     * Creates a new @{link {@link ModelPackageLauncher} instance
     *
     * @param model
     * @param mainWindow
     * @param dialogs
     *            the dialog controller used to create the associated dialogs
     * @param dialogNameValidator
     */
    @Inject
    public BehaviorDiagramEditLauncher(Application model, MainWindow mainWindow, SimpleDialogLauncher dialogs,
                                       final NameValidator dialogNameValidator) {
        this.model = model;
        this.mainWindow = mainWindow;
        this.dialogs = dialogs;
        this.dialogNameValidator = dialogNameValidator;
    }

    @Override
    protected void doLaunch(BehaviorDiagram component) {

        final String title = "Diagrama de comportamiento en "
                + model.getActivePackage().getQualifiedName();
        Optional<String> name = dialogs.showInput(title,
                "Nombre", component.getName(), dialogNameValidator);
        if (name.isPresent()) {
            String oldName = component.getQualifiedName();
            String newName = name.get();
            component.setName(newName);
            mainWindow.renameEditor(oldName, component.getQualifiedName());
        }
    }
}
