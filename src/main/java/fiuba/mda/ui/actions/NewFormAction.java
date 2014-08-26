package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.google.inject.Inject;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.BehaviorForm;
import fiuba.mda.model.Representation;
import fiuba.mda.model.WizardForm;
import fiuba.mda.model.GraficInterfaceDiagram;
import fiuba.mda.ui.launchers.WizardDialogLauncher;
import fiuba.mda.ui.utilities.ImageLoader;

public class NewFormAction extends Action {
	private GraficInterfaceDiagram boundDiagram;
	private int formNumber = 0;
	private final Shell shell;

	/**
	 * Creates a new {@link NewFormAction} instance
	 * 
	 * @param imageLoader
	 *            the image loader used to provide the image of this action
	 */
	@Inject
	public NewFormAction(final Shell shell, final ImageLoader imageLoader) {
		this.shell = shell;
		setupPresentation(imageLoader);
	}

	private void setupPresentation(final ImageLoader imageLoader) {
		setText("Nuevo Formulario");
		setToolTipText("Crear un nuevo formulario en el diagrama actual");
		setImageDescriptor(imageLoader.descriptorOf("application_form_add"));
	}

	/**
	 * Binds this action to work on a given {@link BehaviorDiagram} instance
	 * 
	 * @param diagram
	 *            the diagram to bind this action to
	 * @return this for method chaining
	 */
	public NewFormAction boundTo(final GraficInterfaceDiagram diagram) {
		boundDiagram = diagram;
		return this;
	}

	@Override
	public void run() {
		WizardDialogLauncher launcher = new WizardDialogLauncher(boundDiagram);
		WizardDialog dialogo = new WizardDialog(shell, launcher);
		dialogo.open();

		if (!launcher.wasCanceled()) {
			WizardForm form = launcher.getForm();
			BehaviorForm behaviorForm = new BehaviorForm(form.getFormName(),
					form.getExistingPropertiesAdded(),
					form.getNewPropertiesAdded(), form.getTextsAdded(),
					form.getButtonsAdded());

			Representation<BehaviorForm> representation = new Representation<>(
					behaviorForm);
			representation.getPosition().setX(formNumber * 100);
			boundDiagram.addForm(representation);
			formNumber++;
		}

	}
}
