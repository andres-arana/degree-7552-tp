package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.Application;
import fiuba.mda.model.ModelPackage;
import fiuba.mda.ui.actions.validators.NameValidatorFactory;
import fiuba.mda.ui.launchers.SimpleDialogLauncher;
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

	private final SimpleDialogLauncher dialog;

	private final NameValidatorFactory validator;

	/**
	 * Creates a new {@link NewPackageAction} instance
	 * 
	 * @param model
	 *            the model on which this action will create a new package
	 * @param dialog
	 *            the dialog controller used to create the associated dialogs
	 * @param imageLoader
	 *            the image loader used to provide the image of this action
	 * @param validator
	 *            the validator used to validate the package name on the input
	 *            dialogs
	 */
	@Inject
	public NewPackageAction(final Application model,
			final SimpleDialogLauncher dialog, final ImageLoader imageLoader,
			final NameValidatorFactory validator) {
		this.model = model;
		this.dialog = dialog;
		this.validator = validator;

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
		setImageDescriptor(imageLoader.descriptorOf("package_add"));
	}

	@Override
	public void run() {
		ModelPackage activePackage = model.getActivePackage();
		Optional<String> name = askForName(activePackage);
		if (name.isPresent()) {
			ModelPackage newPackage = new ModelPackage(name.get());
			activePackage.addChild(newPackage);
		}
	}

	private Optional<String> askForName(ModelPackage parent) {
		return dialog.showInput(dialogTitle(), "Nombre", null,
				validator.validatorForNewNameInParent(parent));
	}

	private String dialogTitle() {
		return "Paquete en " + model.getActivePackage().getQualifiedName();
	}
}
