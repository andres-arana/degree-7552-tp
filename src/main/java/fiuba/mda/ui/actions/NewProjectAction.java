package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.Application;
import fiuba.mda.model.Project;
import fiuba.mda.ui.actions.validators.NameValidator;
import fiuba.mda.ui.launchers.SimpleDialogLauncher;
import fiuba.mda.ui.utilities.ImageLoader;

/**
 * {@link Action} implementation which represents the command of creating a new
 * project
 */
@Singleton
public class NewProjectAction extends Action {
	private final Application model;

	private final SimpleDialogLauncher dialog;

	private final IInputValidator projectNameValidator;

	/**
	 * Creates a new {@link NewProjectAction} instance
	 * 
	 * @param model
	 *            the model on which this action will create a new project
	 * @param dialog
	 *            the dialog controller used to display associated dialogs
	 * @param imageLoader
	 *            the image loader used to provide the image of this action
	 * @param projectNameValidator
	 *            the validator used to validate the project name on input
	 *            dialogs
	 */
	@Inject
	public NewProjectAction(final Application model,
			final SimpleDialogLauncher dialog, final ImageLoader imageLoader,
			final NameValidator projectNameValidator) {
		this.model = model;
		this.dialog = dialog;
		this.projectNameValidator = projectNameValidator;

		setupPresentation(imageLoader);
	}

	private void setupPresentation(final ImageLoader imageLoader) {
		setText("&Nuevo Proyecto@Ctrl+Shift+N");
		setToolTipText("Cerrar el proyecto actual y crear un nuevo proyecto");
		setImageDescriptor(imageLoader.descriptorOf("application_add"));
	}

	@Override
	public void run() {
		Optional<String> name = dialog.showInput("Proyecto",
				"Nombre", null, projectNameValidator);
		if (name.isPresent()) {
			Project newProject = new Project(name.get().trim());
			model.openProject(newProject);
		}
	}
}
