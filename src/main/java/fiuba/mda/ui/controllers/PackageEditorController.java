package fiuba.mda.ui.controllers;

import org.eclipse.jface.dialogs.IInputValidator;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.ModelPackage;
import fiuba.mda.ui.actions.validators.NameValidator;

/**
 * {@link EditorController} implementation which allows editing a package
 */
@Singleton
public class PackageEditorController extends BaseEditorController<ModelPackage> {
	private final SimpleDialogController dialogs;
	private final IInputValidator packageNameValidator;

	/**
	 * Creates a new @{link PackageEditorController} instance
	 * 
	 * @param dialogs
	 *            the dialog controller used to create the associated dialogs
	 * @param packageNameValidator
	 *            the validator used to validate the package name on the input
	 *            dialogs
	 */
	@Inject
	public PackageEditorController(SimpleDialogController dialogs,
			final NameValidator packageNameValidator) {
		this.dialogs = dialogs;
		this.packageNameValidator = packageNameValidator;
	}

	@Override
	protected void doLaunch(ModelPackage component) {
		if (component.isRoot()) {
			return;
		}

		final String title = "Editando Paquete " + component.getQualifiedName();
		Optional<String> name = dialogs.showInput(title, "Nombre del paquete",
				component.getName(), packageNameValidator);

		if (name.isPresent()) {
			component.setName(name.get());
		}
	}
}