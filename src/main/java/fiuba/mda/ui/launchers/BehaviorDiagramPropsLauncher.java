package fiuba.mda.ui.launchers;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.ui.actions.validators.NameValidatorFactory;

@Singleton
public class BehaviorDiagramPropsLauncher extends BaseLauncher<BehaviorDiagram> {
	private final SimpleDialogLauncher dialogs;
	private final NameValidatorFactory validatorFactory;

	@Inject
	public BehaviorDiagramPropsLauncher(final SimpleDialogLauncher dialogs,
			final NameValidatorFactory validatorFactory) {
		this.dialogs = dialogs;
		this.validatorFactory = validatorFactory;
	}

	@Override
	protected void doLaunch(final BehaviorDiagram component) {
		Optional<String> name = askForName(component);
		if (name.isPresent()) {
			component.setName(name.get());
		}
	}

	private Optional<String> askForName(final BehaviorDiagram component) {
		return dialogs.showInput(
				dialogTitle(component),
				"Nombre",
				component.getName(),
				validatorFactory.validatorForRenameInParent(
						component.getParent(), component));
	}

	private String dialogTitle(final BehaviorDiagram component) {
		return "Diagrama de comportamiento " + component.getQualifiedName();
	}
}
