package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.Application;
import fiuba.mda.model.ModelPackage;
import fiuba.mda.ui.actions.validators.NameValidator;
import fiuba.mda.ui.controllers.SimpleDialogController;
import fiuba.mda.ui.utilities.ImageLoader;
import fiuba.mda.utilities.SimpleEvent.Observer;

/**
 * {@link Action} implementation which represents the command of creating a new
 * package in the project tree
 */
@Singleton
public class NewPackageAction extends Action {
	private Observer<Application> onProjectOpen = new Observer<Application>() {
		@Override
		public void notify(Application observable) {
			setEnabled(model.hasCurrentProject());
		}
	};

	private final Application model;

	private final SimpleDialogController dialog;

	private final IInputValidator packageNameValidator;

	/**
	 * Creates a new {@link NewPackageAction} instance
	 * 
	 * @param model
	 *            the model on which this action will create a new package
	 * @param dialog
	 *            the dialog controller used to create the associated dialogs
	 * @param imageLoader
	 *            the image loader used to provide the image of this action
	 * @param packageNameValidator
	 *            the validator used to validate the package name on the input
	 *            dialogs
	 */
	@Inject
	public NewPackageAction(final Application model,
			final SimpleDialogController dialog, final ImageLoader imageLoader,
			final NameValidator packageNameValidator) {
		this.model = model;
		this.dialog = dialog;
		this.packageNameValidator = packageNameValidator;

		setupPresentation(imageLoader);
		setupEventObservation(model);
	}

	private void setupEventObservation(final Application model) {
		model.projectOpenEvent().observe(this.onProjectOpen);
	}

	private void setupPresentation(final ImageLoader imageLoader) {
		setText("Nuevo Paquete");
		setToolTipText("Crear un nuevo paquete en el proyecto");
		setEnabled(false);
		setImageDescriptor(imageLoader.descriptorOf("brick_add"));
	}

	@Override
	public void run() {
		final String title = "Nuevo paquete en "
				+ model.getActivePackage().getFullName();
		Optional<String> name = dialog.showInput(title,
				"Nombre del nuevo paquete", null, packageNameValidator);
		if (name.isPresent()) {
			ModelPackage newPackage = new ModelPackage(name.get());
			model.getActivePackage().addComponent(newPackage);
		}
	}
}
