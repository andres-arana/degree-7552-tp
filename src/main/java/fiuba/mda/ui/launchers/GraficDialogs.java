package fiuba.mda.ui.launchers;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import fiuba.mda.model.GraficInterfaceDiagram;
import fiuba.mda.ui.actions.*;
import fiuba.mda.ui.figures.GraficInterfaceDiagramFigure;
import fiuba.mda.ui.main.MainWindow;
import fiuba.mda.ui.main.tree.ComponentImageVisitor;
import fiuba.mda.ui.main.workspace.ControlBuilder;
import fiuba.mda.ui.main.workspace.DiagramEditor;
import fiuba.mda.model.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.jface.wizard.WizardDialog;

import fiuba.mda.ui.launchers.WizardDialogLauncher;

import fiuba.mda.ui.main.workspace.*;

import org.eclipse.swt.widgets.Shell;

public class GraficDialogs implements GraficInterfaceDiagramFigure.Dialogs {
    private final Shell shell;
    private final SimpleDialogLauncher dialogLauncher;
    private GraficInterfaceDiagram diagram;

    @Inject
    public GraficDialogs(final Shell shell, final SimpleDialogLauncher dialogLauncher) {
        this.shell = shell;
        this.dialogLauncher = dialogLauncher;
    }

    public GraficDialogs boundTo(final GraficInterfaceDiagram diagram) {
        this.diagram = diagram;
        return this;
    }

    public void showTextDialog(BehaviorText behaviorText) {
        TextDialog dialogo = new TextDialog(shell, "Edición de Texto");
        dialogo.setTextString(behaviorText.getName());

        Optional<String> stringOptional = dialogLauncher.showDialog(dialogo);

        if (stringOptional.isPresent()) {     
            behaviorText.setName(dialogo.getTextString());
            diagram.textsChangedEvent().raise();
        };
    }
    public void showButtonDialog(BehaviorButton behaviorButton) {
        ButtonDialog dialogo = new ButtonDialog(shell, "Edición de Botón");
        dialogo.setLabelString(behaviorButton.getName());

        Optional<String> stringOptional = dialogLauncher.showDialog(dialogo);

        if (stringOptional.isPresent()) {     
            behaviorButton.setName(dialogo.getLabelString());
            diagram.buttonsChangedEvent().raise();
        };
    }
    public void showFieldDialog(BehaviorField behaviorField){
        FieldDialog dialogo = new FieldDialog(shell, "Edición de Campo");
        dialogo.setPropertyName(behaviorField.getPropertyName());
        dialogo.setFieldName(behaviorField.getFieldName());

        Optional<String> stringOptional = dialogLauncher.showDialog(dialogo);

        if (stringOptional.isPresent()) {     
            behaviorField.setPropertyName(dialogo.getPropertyName());
            behaviorField.setFieldName(dialogo.getFieldName());
            diagram.fieldsChangedEvent().raise();
        };
    }
    public void showFormDialog(BehaviorForm behaviorForm) {
        WizardDialogLauncher launcher = new WizardDialogLauncher();
        WizardDialog dialogo = new WizardDialog(shell, launcher);

        launcher.setFormName(behaviorForm.getFormName());
        launcher.setTextsAdded(behaviorForm.getTexts());
        launcher.setButtonsAdded(behaviorForm.getButtons());
        launcher.setExistingPropertiesAdded(behaviorForm.getExistingFields());
        launcher.setNewPropertiesAdded(behaviorForm.getNewFields());

        dialogo.open();

        if (!launcher.wasCanceled()){
            WizardForm form = launcher.getForm();
            behaviorForm.setFormName(form.getFormName());
            behaviorForm.setTexts(form.getTextsAdded());
            behaviorForm.setButtons(form.getButtonsAdded());
            behaviorForm.setNewFields(form.getNewPropertiesAdded());
            behaviorForm.setExistingFields(form.getExistingPropertiesAdded());

            diagram.formsChangedEvent().raise();
        }
    }
}
