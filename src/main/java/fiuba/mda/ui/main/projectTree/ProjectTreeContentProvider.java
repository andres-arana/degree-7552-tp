package fiuba.mda.ui.main.projectTree;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import com.google.inject.Singleton;

import fiuba.mda.model.Application;
import fiuba.mda.model.Project;
import fiuba.mda.model.ProjectComponent;
import fiuba.mda.utilities.SimpleEvent.Observer;

/**
 * {@link ITreeContentProvider} implementation which provides content for a
 * project tree
 */
@Singleton
public class ProjectTreeContentProvider implements ITreeContentProvider {
	/**
	 * {@link Observer} implementation which refreshes the project tree viewer
	 */
	public class RefreshProjectTree implements Observer<Application> {
		private final TreeViewer viewer;

		/**
		 * Creates a new @{link RefreshProjectTree} instance
		 * 
		 * @param viewer
		 *            the viewer which will be refreshed
		 */
		public RefreshProjectTree(final Viewer viewer) {
			this.viewer = (TreeViewer) viewer;
		}

		@Override
		public void notify(Application observable) {
			viewer.refresh();
			viewer.expandAll();
		}
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput,
			final Object newInput) {
		if (newInput == null) {
			return;
		}

		Application newModel = (Application) newInput;
		newModel.projectOpenEvent().observe(new RefreshProjectTree(viewer));
		newModel.hierarchyChangedEvent()
				.observe(new RefreshProjectTree(viewer));
	}

	@Override
	public Object[] getChildren(final Object element) {
		if (element == null) {
			return new Object[] {};
		}

		ProjectComponent model = (ProjectComponent) element;
		return model.getChildren().toArray();

	}

	@Override
	public Object[] getElements(final Object input) {
		Application model = (Application) input;
		if (!model.hasCurrentProject()) {
			return new Object[] {};
		}
		Project project = model.getCurrentProject();
		return new Object[] { project.getRootPackage() };
	}

	@Override
	public Object getParent(final Object element) {
		if (element == null) {
			return null;
		}

		ProjectComponent model = (ProjectComponent) element;
		if (model.isRoot()) {
			return null;
		}
		
		return model.getParent();
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element == null) {
			return false;
		}

		ProjectComponent model = (ProjectComponent) element;
		return model.hasChildren();
	}
}
