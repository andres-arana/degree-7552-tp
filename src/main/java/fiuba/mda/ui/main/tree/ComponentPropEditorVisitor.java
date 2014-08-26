package fiuba.mda.ui.main.tree;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.GraficInterfaceDiagram;
import fiuba.mda.model.ModelAspect;
import fiuba.mda.model.ModelEntity;
import fiuba.mda.model.ModelPackage;
import fiuba.mda.model.ProjectComponent;
import fiuba.mda.model.ProjectComponentVisitor;
import fiuba.mda.ui.launchers.BehaviorDiagramPropsLauncher;
import fiuba.mda.ui.launchers.GraficInterfaceDiagramPropsLauncher;
import fiuba.mda.ui.launchers.Launcher;
import fiuba.mda.ui.launchers.ModelEntityPropsLauncher;
import fiuba.mda.ui.launchers.ModelPackagePropsLauncher;

public class ComponentPropEditorVisitor implements ProjectComponentVisitor {
	private final ModelPackagePropsLauncher packageLauncher;
	private final BehaviorDiagramPropsLauncher behaviorDiagramLauncher;
	private final GraficInterfaceDiagramPropsLauncher graficInterfaceDiagramLauncher;
	private final ModelEntityPropsLauncher entityLauncher;

	private Optional<Launcher> launcher = Optional.absent();

	@Inject
	public ComponentPropEditorVisitor(
			final ModelPackagePropsLauncher packageLauncher,
			final BehaviorDiagramPropsLauncher behaviorDiagramLauncher,
			final GraficInterfaceDiagramPropsLauncher graficInterfaceDiagramLauncher,
			final ModelEntityPropsLauncher entityLauncher) {
		this.packageLauncher = packageLauncher;
		this.behaviorDiagramLauncher = behaviorDiagramLauncher;
		this.graficInterfaceDiagramLauncher = graficInterfaceDiagramLauncher;
		this.entityLauncher = entityLauncher;
	}

	@Override
	public void visit(ModelPackage modelPackage) {
		launcher = Optional.<Launcher> of(packageLauncher);
	}

	@Override
	public void visit(ModelEntity modelEntity) {
		launcher = Optional.<Launcher> of(entityLauncher);
	}

	@Override
	public void visit(ModelAspect modelAspect) {
		// This type has no properties to edit
	}

	@Override
	public void visit(BehaviorDiagram behaviorDiagram) {
		launcher = Optional.<Launcher> of(behaviorDiagramLauncher);
	}

	@Override
	public void visit(GraficInterfaceDiagram behaviorDiagram) {
		launcher = Optional.<Launcher> of(graficInterfaceDiagramLauncher);
	}

	public Optional<Launcher> launcherFor(ProjectComponent model) {
		model.accept(this);
		return launcher;
	}
}
