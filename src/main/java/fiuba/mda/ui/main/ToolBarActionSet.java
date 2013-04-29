package fiuba.mda.ui.main;

import org.eclipse.jface.action.ToolBarManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.ui.actions.NewPackageAction;
import fiuba.mda.ui.actions.NewProjectAction;

@Singleton
public class ToolBarActionSet {
	private final NewProjectAction newProject;
	private final NewPackageAction newPackage;

	@Inject
	public ToolBarActionSet(final NewProjectAction newProject,
			final NewPackageAction newPackage) {
		this.newProject = newProject;
		this.newPackage = newPackage;
	}

	public void provideActions(final ToolBarManager manager) {
		manager.add(newProject);
		manager.add(newPackage);
	}

}
