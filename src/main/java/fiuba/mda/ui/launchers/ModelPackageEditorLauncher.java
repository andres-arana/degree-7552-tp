package fiuba.mda.ui.launchers;

import org.eclipse.jface.dialogs.IInputValidator;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.ModelPackage;
import fiuba.mda.ui.actions.validators.NameValidator;

/**
 * {@link EditorLauncher} implementation which allows editing a package
 */
@Singleton
public class ModelPackageEditorLauncher extends
		BaseEditorLauncher<ModelPackage> {
	private final SimpleDialogLauncher dialogs;
	private final IInputValidator packageNameValidator;

	/**
	 * Creates a new @{link PackageEditorLauncher} instance
	 * 
	 * @param dialogs
	 *            the dialog controller used to create the associated dialogs
	 * @param packageNameValidator
	 *            the validator used to validate the package name on the input
	 *            dialogs
	 */
	@Inject
	public ModelPackageEditorLauncher(SimpleDialogLauncher dialogs,
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
