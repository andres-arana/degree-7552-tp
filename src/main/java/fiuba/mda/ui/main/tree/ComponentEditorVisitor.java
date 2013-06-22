package fiuba.mda.ui.main.tree;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.ModelAspect;
import fiuba.mda.model.ModelEntity;
import fiuba.mda.model.ModelPackage;
import fiuba.mda.model.ProjectComponent;
import fiuba.mda.model.ProjectComponentVisitor;
import fiuba.mda.ui.launchers.BehaviorDiagramEditorLauncher;
import fiuba.mda.ui.launchers.EditorLauncher;
import fiuba.mda.ui.launchers.ModelPackageEditorLauncher;

/**
 * {@link ProjectComponentVisitor} which allows selecting the appropriate
 * {@link EditorLauncher} for a given {@link ProjectComponent}.
 */
public class ComponentEditorVisitor implements ProjectComponentVisitor {
	private final ModelPackageEditorLauncher packageController;
	private final BehaviorDiagramEditorLauncher behaviorDiagramController;

	private Optional<EditorLauncher> controller = Optional.absent();

	/**
	 * Creates a new @{link ComponentEditorVisitor} instance
	 * 
	 * @param packageController
	 *            the controller to use for {@link ModelPackage} instances
	 * @param behaviorDiagramController
	 *            the controller to use for {@link BehaviorDiagram} instances
	 */
	@Inject
	public ComponentEditorVisitor(
			final ModelPackageEditorLauncher packageController,
			final BehaviorDiagramEditorLauncher behaviorDiagramController) {
		this.packageController = packageController;
		this.behaviorDiagramController = behaviorDiagramController;
	}

	@Override
	public void visit(ModelPackage modelPackage) {
		controller = Optional.<EditorLauncher> of(packageController);
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
		controller = Optional.<EditorLauncher> of(behaviorDiagramController);
	}

	/**
	 * Obtains the {@link EditorLauncher} for a given {@link ProjectComponent}
	 * by visiting it and obtaining the configured editor through double
	 * dispatching
	 * 
	 * @param model
	 *            the model to obtain the controller for
	 * @return the controller, if any, or absent if none was configured for the
	 *         {@link ProjectComponent}
	 */
	public Optional<EditorLauncher> controllerFor(ProjectComponent model) {
		model.accept(this);
		return controller;
	}
}
