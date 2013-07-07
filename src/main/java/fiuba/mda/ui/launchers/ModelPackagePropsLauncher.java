package fiuba.mda.ui.launchers;

import org.eclipse.jface.dialogs.IInputValidator;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.ModelPackage;
import fiuba.mda.ui.actions.validators.NameValidatorFactory;

@Singleton
public class ModelPackagePropsLauncher extends BaseLauncher<ModelPackage> {
	private final SimpleDialogLauncher dialogs;
	private final NameValidatorFactory validatorFactory;

	@Inject
	public ModelPackagePropsLauncher(final SimpleDialogLauncher dialogs,
			final NameValidatorFactory validatorFactory) {
		this.dialogs = dialogs;
		this.validatorFactory = validatorFactory;
	}

	@Override
	protected void doLaunch(final ModelPackage component) {
		Optional<String> name = askForName(component);
		if (name.isPresent()) {
			component.setName(name.get());
		}
	}

	private Optional<String> askForName(final ModelPackage component) {
		IInputValidator validator = component.isRoot() ? validatorFactory
				.validatorForRenameRoot(component) : validatorFactory
				.validatorForRenameInParent(component.getParent(), component);

		return dialogs.showInput(dialogTitle(component), "Nombre",
				component.getName(), validator);
	}

	private String dialogTitle(final ModelPackage component) {
		return "Paquete " + component.getQualifiedName();
	}
}
