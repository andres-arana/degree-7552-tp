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
import fiuba.mda.ui.launchers.Launcher;

/**
 * {@link ProjectComponentVisitor} which allows selecting the appropriate
 * {@link Launcher} for a given {@link ProjectComponent}.
 */
public class ComponentDefaultActionVisitor implements ProjectComponentVisitor {
	private final BehaviorDiagramEditorLauncher behaviorDiagramController;

	private Optional<Launcher> controller = Optional.absent();

	@Inject
	public ComponentDefaultActionVisitor(
			final BehaviorDiagramEditorLauncher behaviorDiagramController) {
		this.behaviorDiagramController = behaviorDiagramController;
	}

	@Override
	public void visit(ModelPackage modelPackage) {
		// This type does not have default actions
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
		controller = Optional.<Launcher> of(behaviorDiagramController);
	}

	/**
	 * Obtains the {@link Launcher} for a given {@link ProjectComponent} by
	 * visiting it and obtaining the configured editor through double
	 * dispatching
	 * 
	 * @param model
	 *            the model to obtain the controller for
	 * @return the controller, if any, or absent if none was configured for the
	 *         {@link ProjectComponent}
	 */
	public Optional<Launcher> controllerFor(ProjectComponent model) {
		model.accept(this);
		return controller;
	}
}
