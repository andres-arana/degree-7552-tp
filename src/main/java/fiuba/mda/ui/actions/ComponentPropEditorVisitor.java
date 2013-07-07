package fiuba.mda.ui.actions;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.ModelAspect;
import fiuba.mda.model.ModelEntity;
import fiuba.mda.model.ModelPackage;
import fiuba.mda.model.ProjectComponent;
import fiuba.mda.model.ProjectComponentVisitor;
import fiuba.mda.ui.launchers.BehaviorDiagramPropsLauncher;
import fiuba.mda.ui.launchers.Launcher;
import fiuba.mda.ui.launchers.ModelPackagePropsLauncher;

public class ComponentPropEditorVisitor implements ProjectComponentVisitor {
	private final ModelPackagePropsLauncher packageLauncher;
	private final BehaviorDiagramPropsLauncher behaviorDiagramLauncher;

	private Optional<Launcher> launcher = Optional.absent();

	@Inject
	public ComponentPropEditorVisitor(
			final ModelPackagePropsLauncher packageLauncher,
			final BehaviorDiagramPropsLauncher behaviorDiagramLauncher) {
		this.packageLauncher = packageLauncher;
		this.behaviorDiagramLauncher = behaviorDiagramLauncher;
	}

	@Override
	public void visit(ModelPackage modelPackage) {
		launcher = Optional.<Launcher> of(packageLauncher);
	}

	@Override
	public void visit(ModelEntity modelEntity) {
		// TODO: Add the appropriate launcher for this entity
	}

	@Override
	public void visit(ModelAspect modelAspect) {
		// This type has no properties to edit
	}

	@Override
	public void visit(BehaviorDiagram behaviorDiagram) {
		launcher = Optional.<Launcher> of(behaviorDiagramLauncher);
	}

	public Optional<Launcher> launcherFor(ProjectComponent model) {
		model.accept(this);
		return launcher;
	}
}