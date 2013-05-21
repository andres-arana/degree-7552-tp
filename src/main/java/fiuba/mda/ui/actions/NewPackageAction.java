package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.DocumentModel;
import fiuba.mda.model.ModelPackage;
import fiuba.mda.model.ObservableEvent.Observer;
import fiuba.mda.model.Project;
import fiuba.mda.ui.actions.validators.NameValidator;
import fiuba.mda.ui.dialogs.SimpleDialogController;
import fiuba.mda.ui.utilities.ImageLoader;

/**
 * {@link Action} implementation which represents the command of creating a new
 * package in the project tree
 */
@Singleton
public class NewPackageAction extends Action {
	/**
	 * The document model on which the package will be created
	 */
	private final DocumentModel model;

	/**
	 * The dialog controller which displays the edit package action
	 */
	private final SimpleDialogController dialog;

	/**
	 * The validator which will validate the package name
	 */
	private final IInputValidator packageNameValidator;

	/**
	 * Creates a new {@link NewPackageAction} instance
	 */
	@Inject
	public NewPackageAction(final DocumentModel model,
			final SimpleDialogController dialog, final ImageLoader imageLoader,
			final NameValidator packageNameValidator) {
		this.model = model;
		this.dialog = dialog;
		this.packageNameValidator = packageNameValidator;

		setupPresentation(imageLoader);
		setupEventObservation(model);
	}

	/**
	 * Sets up event notifications to be notified when the {@link DocumentModel}
	 * changes
	 * 
	 * @param model
	 *            the model to set notifications on
	 */
	private void setupEventObservation(final DocumentModel model) {
		model.getProjectOpenEvent().observe(this.onProjectOpen);
	}

	/**
	 * Sets the action presentation parameters
	 * 
	 * @param imageLoader
	 *            the {@link ImageLoader} instance to use to load the action
	 *            image
	 */
	private void setupPresentation(final ImageLoader imageLoader) {
		setText("Nuevo Paquete");
		setToolTipText("Crear un nuevo paquete en el proyecto");
		setEnabled(false);
		setImageDescriptor(imageLoader.loadImageDescriptor("package"));
	}

	/**
	 * Overrides {@link Action#run()} to add a new package to the active package
	 * in the {@link DocumentModel}
	 */
	@Override
	public void run() {
		String name = dialog.showInput("Nuevo paquete",
				"Nombre del nuevo paquete", null, packageNameValidator);
		if (name != null) {
			ModelPackage newPackage = new ModelPackage(name);
			model.getActivePackage().addComponent(newPackage);
		}
	}

	/**
	 * Event observer which is invoked when a new project has been open,
	 * enabling or disabling the action as needed
	 */
	private Observer<DocumentModel, Project> onProjectOpen = new Observer<DocumentModel, Project>() {
		@Override
		public void notify(DocumentModel observable, Project eventData) {
			setEnabled(model.hasProject());
		}
	};

}
