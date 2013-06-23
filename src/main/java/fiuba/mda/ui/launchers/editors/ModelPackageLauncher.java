package fiuba.mda.ui.launchers.editors;

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
public class ModelPackageLauncher extends BaseLauncher<ModelPackage> {
	private final SimpleDialogLauncher dialogs;
	private final IInputValidator packageNameValidator;

	/**
	 * Creates a new @{link {@link ModelPackageLauncher} instance
	 * 
	 * @param dialogs
	 *            the dialog controller used to create the associated dialogs
	 * @param packageNameValidator
	 *            the validator used to validate the package name on the input
	 *            dialogs
	 */
	@Inject
	public ModelPackageLauncher(SimpleDialogLauncher dialogs,
			final NameValidator packageNameValidator) {
		this.dialogs = dialogs;
		this.packageNameValidator = packageNameValidator;
	}

	@Override
	protected void doLaunch(ModelPackage component) {
		final String title = "Paquete " + component.getQualifiedName();
		Optional<String> name = dialogs.showInput(title, "Nombre",
				component.getName(), packageNameValidator);

		if (name.isPresent()) {
			component.setName(name.get());
		}
	}
}
