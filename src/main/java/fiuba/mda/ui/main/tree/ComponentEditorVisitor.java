package fiuba.mda.ui.main.tree;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.ModelAspect;
import fiuba.mda.model.ModelEntity;
import fiuba.mda.model.ModelPackage;
import fiuba.mda.model.ProjectComponent;
import fiuba.mda.model.ProjectComponentVisitor;
import fiuba.mda.ui.launchers.editors.BehaviorDiagramEditLauncher;
import fiuba.mda.ui.launchers.BehaviorDiagramLauncher;
import fiuba.mda.ui.launchers.EditorLauncher;
import fiuba.mda.ui.launchers.ModelPackageLauncher;

/**
 * {@link ProjectComponentVisitor} which allows selecting the appropriate
 * {@link EditorLauncher} for a given {@link ProjectComponent}.
 */
public class ComponentEditorVisitor implements ProjectComponentVisitor {
	private final ModelPackageLauncher packageController;
	private final BehaviorDiagramLauncher behaviorDiagramController;
    private final BehaviorDiagramEditLauncher behaviorDiagramEditController;

	private Optional<EditorLauncher> controller = Optional.absent();

	/**
	 * Creates a new @{link ComponentEditorVisitor} instance
	 *
     * @param packageController
     *            the controller to use for {@link fiuba.mda.model.ModelPackage} instances
     * @param behaviorDiagramController
     *            the controller to use for {@link fiuba.mda.model.BehaviorDiagram} instances
     * @param behaviorDiagramEditController
     */
	@Inject
	public ComponentEditorVisitor(
            final ModelPackageLauncher packageController,
            final BehaviorDiagramLauncher behaviorDiagramController, BehaviorDiagramEditLauncher behaviorDiagramEditController) {
		this.packageController = packageController;
		this.behaviorDiagramController = behaviorDiagramController;
        this.behaviorDiagramEditController = behaviorDiagramEditController;
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

	@Override
	public void visit(BehaviorDiagram behaviorDiagram,boolean isEditing) {
		controller = Optional.<EditorLauncher> of(behaviorDiagramEditController);
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

    public Optional<EditorLauncher> controllerFor(ProjectComponent model,boolean isEditing) {
        model.accept(this,isEditing);
        return controller;
    }
}
