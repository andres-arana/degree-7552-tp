package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.DocumentModel;
import fiuba.mda.model.Project;
import fiuba.mda.ui.actions.validators.NameValidator;
import fiuba.mda.ui.dialogs.SimpleDialogController;
import fiuba.mda.ui.utilities.ImageLoader;

/**
 * {@link Action} implementation which represents the command of creating a new
 * project
 */
@Singleton
public class NewProjectAction extends Action {
	/**
	 * The document model on which the project will be created and open
	 */
	private final DocumentModel model;

	/**
	 * The dialog controller which displays the edit project action
	 */
	private final SimpleDialogController dialog;

	/**
	 * The validator which will validate the project name
	 */
	private final IInputValidator projectNameValidator;

	/**
	 * Creates a new {@link NewProjectAction} instancd
	 */
	@Inject
	public NewProjectAction(final DocumentModel model,
			final SimpleDialogController dialog, final ImageLoader imageLoader,
			final NameValidator projectNameValidator) {
		this.model = model;
		this.dialog = dialog;
		this.projectNameValidator = projectNameValidator;

		setupPresentation(imageLoader);
	}

	/**
	 * Sets the action presentation parameters
	 * 
	 * @param imageLoader
	 *            the {@link ImageLoader} instance to use to load the action
	 *            image
	 */
	private void setupPresentation(final ImageLoader imageLoader) {
		setText("&Nuevo Proyecto@Ctrl+Shift+N");
		setToolTipText("Cerrar el proyecto actual y crear un nuevo proyecto");
		setImageDescriptor(imageLoader.loadImageDescriptor("project-new"));
	}

	/**
	 * Overrides {@link Action#run()} to add a new project to the
	 * {@link DocumentModel}
	 */
	@Override
	public void run() {
		String name = dialog.showInput("Nuevo proyecto",
				"Nombre del nuevo proyecto", null, projectNameValidator);
		if (name != null) {
			Project newProject = new Project(name.trim());
			model.openProject(newProject);
		}
	}
}
