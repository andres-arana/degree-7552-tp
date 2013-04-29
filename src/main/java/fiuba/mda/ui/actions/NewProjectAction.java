package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.DocumentModel;
import fiuba.mda.model.ModelEntity;
import fiuba.mda.model.ModelPackage;
import fiuba.mda.model.Project;
import fiuba.mda.ui.utilities.ImageLoader;

@Singleton
public class NewProjectAction extends Action {
	private final DocumentModel model;

	@Inject
	public NewProjectAction(final DocumentModel model,
			final ImageLoader imageLoader) {
		this.model = model;

		setText("&Nuevo Proyecto@Ctrl+Shft+N");
		setToolTipText("Cerrar el proyecto actual y crear un nuevo proyecto");
		setImageDescriptor(ImageDescriptor.createFromImage(imageLoader
				.loadImage("project-new")));
	}

	@Override
	public void run() {
		ModelPackage root = new ModelPackage("(Default)");
		root.addComponent(new ModelEntity("User"));

		ModelPackage child = new ModelPackage("Sales");
		child.addComponent(new ModelEntity("Customer"));
		child.addComponent(new ModelEntity("Vendor"));

		root.addComponent(child);

		Project p = new Project("New Project", root);

		model.openProject(p);
	}

}
