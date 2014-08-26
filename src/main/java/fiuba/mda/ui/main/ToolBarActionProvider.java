package fiuba.mda.ui.main;

import java.util.ArrayList;
import java.util.List;

import fiuba.mda.ui.actions.NewGraficInterfaceDiagramAction;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.ui.actions.NewBehaviourDiagramAction;
import fiuba.mda.ui.actions.LoadProjectAction;
import fiuba.mda.ui.actions.NewPackageAction;
import fiuba.mda.ui.actions.NewProjectAction;
import fiuba.mda.ui.actions.SaveProjectAction;
import fiuba.mda.ui.actions.SaveProjectAsAction;

/**
 * Groups toolbar actions and provides the actions to a client
 */
@Singleton
public class ToolBarActionProvider {
	private NewProjectAction newProject;
	private LoadProjectAction loadProject;
	private SaveProjectAction saveProject;
	private SaveProjectAsAction saveProjectAs;
	private NewPackageAction newPackage;
	private NewBehaviourDiagramAction newBehaviorDiagram;
	private NewGraficInterfaceDiagramAction newGraphicInterfaceDiagram;

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
			final LoadProjectAction loadProjectAction,
			final NewPackageAction newPackage,
			final NewBehaviourDiagramAction newBehaviorDiagram,
			final NewGraficInterfaceDiagramAction newGraficInterfaceDiagramAction,
			final SaveProjectAction saveProjectAction,
			final SaveProjectAsAction saveProjectAsAction) {
		this.newProject = newProject;
		this.loadProject = loadProjectAction;
		this.saveProject = saveProjectAction;
		this.saveProjectAs = saveProjectAsAction;
		this.newPackage = newPackage;
		this.newBehaviorDiagram = newBehaviorDiagram;
		this.newGraphicInterfaceDiagram = newGraficInterfaceDiagramAction;
	}

	/**
	 * Provides the actions to a {@link ToolBarManager}
	 * 
	 * @param manager
	 *            the manager to add the actions to
	 */
	public void provideActions(final ToolBarManager manager) {
		manager.add(newProject);
		manager.add(loadProject);
		manager.add(saveProject);
		manager.add(saveProjectAs);
		manager.add(new Separator());
		manager.add(newPackage);
		manager.add(newBehaviorDiagram);
		manager.add(newGraphicInterfaceDiagram);
	}
}
