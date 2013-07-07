package fiuba.mda.ui.main.tree;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;

import com.google.inject.Inject;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.ModelAspect;
import fiuba.mda.model.ModelEntity;
import fiuba.mda.model.ModelPackage;
import fiuba.mda.model.ProjectComponent;
import fiuba.mda.model.ProjectComponentVisitor;
import fiuba.mda.ui.actions.DeleteSelectionAction;
import fiuba.mda.ui.actions.EditSelectionPropertiesAction;
import fiuba.mda.ui.actions.NewBehaviourDiagramAction;
import fiuba.mda.ui.actions.NewPackageAction;

public class ComponentContextualActionsVisitor implements
		ProjectComponentVisitor {
	private final NewBehaviourDiagramAction newBehaviorDiagram;
	private final NewPackageAction newPackage;
	private final EditSelectionPropertiesAction editProperties;
	private final DeleteSelectionAction delete;

	private IMenuManager menu;

	@Inject
	public ComponentContextualActionsVisitor(
			final EditSelectionPropertiesAction editProperties,
			final DeleteSelectionAction delete,
			final NewBehaviourDiagramAction newBehaviorDiagram,
			final NewPackageAction newPackage) {
		this.editProperties = editProperties;
		this.delete = delete;
		this.newBehaviorDiagram = newBehaviorDiagram;
		this.newPackage = newPackage;
	}

	@Override
	public void visit(ModelPackage modelPackage) {
		menu.add(newPackage);
		menu.add(newBehaviorDiagram);
		menu.add(new Separator());
		menu.add(editProperties);
		if (!modelPackage.isRoot()) {
			menu.add(new Separator());
			menu.add(delete);	
		}
	}

	@Override
	public void visit(ModelEntity modelEntity) {
		// TODO: Add contextual actions for this type
	}

	@Override
	public void visit(ModelAspect modelAspect) {
		// No contextual actions for this type
	}

	@Override
	public void visit(BehaviorDiagram behaviorDiagram) {
		menu.add(editProperties);
		menu.add(new Separator());
		menu.add(delete);
	}

	public void fillActionsFor(final IMenuManager menu,
			ProjectComponent component) {
		this.menu = menu;
		component.accept(this);
	}
}
