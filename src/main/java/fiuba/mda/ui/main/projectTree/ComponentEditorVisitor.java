package fiuba.mda.ui.main.projectTree;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.ModelAspect;
import fiuba.mda.model.ModelEntity;
import fiuba.mda.model.ModelPackage;
import fiuba.mda.model.ProjectComponent;
import fiuba.mda.model.ProjectComponentVisitor;
import fiuba.mda.ui.controllers.EditorController;
import fiuba.mda.ui.controllers.PackageEditorController;

/**
 * {@link ProjectComponentVisitor} which allows selecting the appropriate
 * {@link EditorController} for a given {@link ProjectComponent}.
 */
public class ComponentEditorVisitor implements ProjectComponentVisitor {
	private final PackageEditorController packageController;

	private Optional<EditorController> controller = Optional.absent();

	/**
	 * Creates a new @{link ComponentEditorVisitor} instance
	 * 
	 * @param packageController
	 *            the controller to use for {@link ModelPackage} instances
	 */
	@Inject
	public ComponentEditorVisitor(
			final PackageEditorController packageController) {
		this.packageController = packageController;
	}

	@Override
	public void visit(ModelPackage modelPackage) {
		controller = Optional.<EditorController> of(packageController);
	}

	@Override
	public void visit(ModelEntity modelEntity) {
		// TODO: Add the proper editor controller for this type
	}

	@Override
	public void visit(ModelAspect modelAspect) {
		// This type never has any editor
	}

	@Override
	public void visit(BehaviorDiagram behaviorDiagram) {
		// TODO: Add the proper editor controller for this type
	}

	/**
	 * Obtains the {@link EditorController} for a given {@link ProjectComponent}
	 * by visiting it and obtaining the configured editor through double
	 * dispatching
	 * 
	 * @param model
	 *            the model to obtain the controller for
	 * @return the controller, if any, or absent if none was configured for the
	 *         {@link ProjectComponent}
	 */
	public Optional<EditorController> controllerFor(ProjectComponent model) {
		model.accept(this);
		return controller;
	}
}
