package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.Application;
import fiuba.mda.model.ModelAspect;
import fiuba.mda.model.ModelEntity;
import fiuba.mda.model.ModelPackage;
import fiuba.mda.ui.actions.validators.NameValidatorFactory;
import fiuba.mda.ui.launchers.SimpleDialogLauncher;
import fiuba.mda.ui.utilities.ImageLoader;
import fiuba.mda.utilities.SimpleEvent.Observer;

@Singleton
public class NewEntityAction extends Action {
	private Observer<Application> onProjectOpen = new Observer<Application>() {
		@Override
		public void notify(Application observable) {
			setEnabled(model.hasCurrentProject());
		}
	};

	private final Application model;

	private final SimpleDialogLauncher dialog;

	private final NameValidatorFactory validator;

	@Inject
	public NewEntityAction(final Application model,
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
		setText("Nueva Entidad");
		setToolTipText("Crear una nueva entidad de modelo en el proyecto");
		setEnabled(false);
		setImageDescriptor(imageLoader.descriptorOf("table_add"));
	}

	@Override
	public void run() {
		ModelPackage activePackage = model.getActivePackage();
		ModelAspect aspect = activePackage.ensureAspect("Dominio");
		Optional<String> name = askForName(activePackage);
		if (name.isPresent()) {
			ModelEntity newEntity = new ModelEntity(name.get());
			aspect.addChild(newEntity);
		}
		aspect.removeIfUnnecessary();
	}

	private Optional<String> askForName(ModelPackage parent) {
		return dialog.showInput(dialogTitle(), "Nombre", null,
				validator.validatorForNewNameInParent(parent));
	}

	private String dialogTitle() {
		return "Entidad en " + model.getActivePackage().getQualifiedName();
	}
}
