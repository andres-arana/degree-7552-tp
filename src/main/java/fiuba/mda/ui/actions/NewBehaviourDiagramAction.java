package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.Application;
import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.ModelAspect;
import fiuba.mda.model.ModelPackage;
import fiuba.mda.ui.actions.validators.NameValidator;
import fiuba.mda.ui.controllers.SimpleDialogController;
import fiuba.mda.ui.utilities.ImageLoader;
import fiuba.mda.utilities.SimpleEvent.Observer;

/**
 * {@link Action} implementation which represents the command of creating a new
 * behavior diagram on the current active package
 */
@Singleton
public class NewBehaviourDiagramAction extends Action {
	private Observer<Application> onProjectOpen = new Observer<Application>() {
		@Override
		public void notify(Application observable) {
			setEnabled(model.hasCurrentProject());
		}
	};

	private final Application model;

	private final SimpleDialogController dialog;

	private final IInputValidator dialogNameValidator;

	/**
	 * Creates a new {@link NewBehaviourDiagramAction} instance
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
	public NewBehaviourDiagramAction(final Application model,
			final SimpleDialogController dialog, final ImageLoader imageLoader,
			final NameValidator packageNameValidator) {
		this.model = model;
		this.dialog = dialog;
		this.dialogNameValidator = packageNameValidator;

		setupPresentation(imageLoader);
		setupEventObservation(model);
	}

	private void setupEventObservation(final Application model) {
		model.projectOpenEvent().observe(this.onProjectOpen);
	}

	private void setupPresentation(final ImageLoader imageLoader) {
		setText("Nuevo Diagrama de Comportamiento");
		setToolTipText("Crear un nuevo diagrama de comportamiento en el proyecto");
		setEnabled(false);
		// TODO: Define real image to use
		setImageDescriptor(imageLoader.descriptorOf("chart_line_add"));
	}

	@Override
	public void run() {
		final String title = "Nuevo diagrama de comportamiento en "
				+ model.getActivePackage().getQualifiedName();
		Optional<String> name = dialog.showInput(title,
				"Nombre del nuevo diagrama", null, dialogNameValidator);
		if (name.isPresent()) {
			ModelPackage activePackage = model.getActivePackage();
			ModelAspect aspect = activePackage.ensureAspect("Comportamiento");
			BehaviorDiagram newDiagram = new BehaviorDiagram(name.get());
			aspect.addChildren(newDiagram);
		}
	}
}
