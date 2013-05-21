package fiuba.mda.ui.main;

import org.eclipse.jface.action.ToolBarManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.ui.actions.NewPackageAction;
import fiuba.mda.ui.actions.NewProjectAction;

/**
 * Groups toolbar actions and provides the actions to a client
 */
@Singleton
public class ToolBarActionProvider {
	/**
	 * The new project action
	 */
	private final NewProjectAction newProject;

	/**
	 * The new package actino
	 */
	private final NewPackageAction newPackage;

	/**
	 * Creates a new {@link ToolBarActionProvider} instance
	 */
	@Inject
	public ToolBarActionProvider(final NewProjectAction newProject,
			final NewPackageAction newPackage) {
		this.newProject = newProject;
		this.newPackage = newPackage;
	}

	/**
	 * Provides the actions to a {@link ToolBarManager}
	 * 
	 * @param manager
	 *            the manager to add the actions to
	 */
	public void provideActions(final ToolBarManager manager) {
		manager.add(newProject);
		manager.add(newPackage);
	}
}
