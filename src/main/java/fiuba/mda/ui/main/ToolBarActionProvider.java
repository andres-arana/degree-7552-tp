package fiuba.mda.ui.main;

import java.util.ArrayList;
import java.util.List;

import fiuba.mda.ui.actions.NewGraficInterfaceDiagramAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ToolBarManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.ui.actions.NewBehaviourDiagramAction;
import fiuba.mda.ui.actions.NewPackageAction;
import fiuba.mda.ui.actions.NewProjectAction;

/**
 * Groups toolbar actions and provides the actions to a client
 */
@Singleton
public class ToolBarActionProvider {
	private final List<IAction> actions = new ArrayList<>();

	/**
	 * Creates a new {@link ToolBarActionProvider} instance
	 * 
	 * @param newProject
	 *            the new project action
	 * @param newPackage
	 *            the new package action
	 * @param newBehaviorDiagram
	 *            the new behavior diagram action
	 */
	@Inject
	public ToolBarActionProvider(final NewProjectAction newProject,
			final NewPackageAction newPackage,
			final NewBehaviourDiagramAction newBehaviorDiagram,final NewGraficInterfaceDiagramAction newGraficInterfaceDiagramAction) {
		actions.add(newProject);
		actions.add(newPackage);
		actions.add(newBehaviorDiagram);
        actions.add(newGraficInterfaceDiagramAction);
	}

	/**
	 * Provides the actions to a {@link ToolBarManager}
	 * 
	 * @param manager
	 *            the manager to add the actions to
	 */
	public void provideActions(final ToolBarManager manager) {
		for (IAction action : actions) {
			manager.add(action);
		}
	}
}
