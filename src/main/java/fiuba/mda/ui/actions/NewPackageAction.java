package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.DocumentModel;
import fiuba.mda.model.ModelPackage;
import fiuba.mda.model.ObservableEvent.Observer;
import fiuba.mda.model.Project;
import fiuba.mda.ui.dialogs.SimpleDialogController;
import fiuba.mda.ui.utilities.ImageLoader;

@Singleton
public class NewPackageAction extends Action {
	private final DocumentModel model;
	private final SimpleDialogController dialog;

	@Inject
	public NewPackageAction(final DocumentModel model,
			final SimpleDialogController dialog, final ImageLoader imageLoader) {
		this.model = model;
		this.dialog = dialog;

		setText("Nuevo Pa&quete");
		setToolTipText("Crear un nuevo paquete en el proyecto");
		setEnabled(false);

		Image image = imageLoader.loadImage("package");
		ImageDescriptor imageDescriptor = ImageDescriptor
				.createFromImage(image);
		setImageDescriptor(imageDescriptor);

		model.getProjectOpenEvent().observe(
				new Observer<DocumentModel, Project>() {
					@Override
					public void notify(DocumentModel observable,
							Project eventData) {
						setEnabled(true);
					}
				});
	}

	@Override
	public void run() {
		String name = dialog.showInput("Nuevo paquete",
				"Nombre del nuevo paquete", null, packageNameValidator);
		if (name != null) {
			ModelPackage newPackage = new ModelPackage(name);
			model.getActivePackage().addComponent(newPackage);
		}
	}

	private final IInputValidator packageNameValidator = new IInputValidator() {
		@Override
		public String isValid(final String value) {
			if (value == null || value.trim().isEmpty()) {
				return "El nombre del nuevo paquete no puede ser vacio";
			}

			if (value.contains(" ")) {
				return "El nombre del nuevo paquete no puede contener espacios";
			}
			return null;
		}
	};

}
