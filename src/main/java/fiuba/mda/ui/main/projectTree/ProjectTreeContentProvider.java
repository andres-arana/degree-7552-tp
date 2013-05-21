package fiuba.mda.ui.main.projectTree;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import com.google.inject.Singleton;

import fiuba.mda.model.DocumentModel;
import fiuba.mda.model.ObservableEvent.Observer;
import fiuba.mda.model.Project;
import fiuba.mda.model.ProjectComponent;

/**
 * {@link ITreeContentProvider} implementation which provides content for a
 * project tree
 */
@Singleton
public class ProjectTreeContentProvider implements ITreeContentProvider {
	/**
	 * {@link Observer} implementation which refreshes the project tree viewer
	 */
	public class RefreshProjectTree implements Observer<DocumentModel, Project> {
		/**
		 * The viewer to refresh
		 */
		private final TreeViewer viewer;

		/**
		 * Creates a new {@link RefreshProjectTree} instance
		 * 
		 * @param viewer
		 *            the viewer to refresh
		 */
		public RefreshProjectTree(final Viewer viewer) {
			this.viewer = (TreeViewer) viewer;
		}

		/**
		 * Overrides {@link Observer#notify(Object, Object)} to refresh the
		 * project tree viewer
		 */
		@Override
		public void notify(DocumentModel observable, Project eventData) {
			viewer.refresh();
			viewer.expandAll();
		}
	}

	/**
	 * Overrides the {@link ITreeContentProvider#dispose()} method. No
	 * additional implementation is given.
	 */
	@Override
	public void dispose() {
	}

	/**
	 * Overrides the
	 * {@link ITreeContentProvider#inputChanged(Viewer, Object, Object)} method
	 * to set up event notifications.
	 */
	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput,
			final Object newInput) {
		if (newInput == null) {
			return;
		}

		DocumentModel newModel = (DocumentModel) newInput;
		newModel.getProjectOpenEvent().observe(new RefreshProjectTree(viewer));
		newModel.getProjectHierarchyChangedEvent().observe(
				new RefreshProjectTree(viewer));
	}

	/**
	 * Overrides {@link ITreeContentProvider#getChildren(Object)} to obtain the
	 * children of a given element.
	 */
	@Override
	public Object[] getChildren(final Object element) {
		if (element == null) {
			return new Object[] {};
		}

		ProjectComponent model = (ProjectComponent) element;
		return model.getChildren().toArray();

	}

	/**
	 * Override {@link ITreeContentProvider#getElements(Object)} to obtain the
	 * root elements of the current project.
	 */
	@Override
	public Object[] getElements(final Object input) {
		DocumentModel model = (DocumentModel) input;
		if (!model.hasProject()) {
			return new Object[] {};
		}
		Project project = model.getProject();
		return new Object[] { project.getRootPackage() };
	}

	/**
	 * Override {@link ITreeContentProvider#getParent(Object)} to obtain the
	 * parent component of an element.
	 */
	@Override
	public Object getParent(final Object element) {
		if (element == null) {
			return null;
		}

		ProjectComponent model = (ProjectComponent) element;
		return model.getParent();
	}

	/**
	 * Override {@link ITreeContentProvider#hasChildren(Object)} to check if an
	 * element has children.
	 */
	@Override
	public boolean hasChildren(final Object element) {
		if (element == null) {
			return false;
		}

		ProjectComponent model = (ProjectComponent) element;
		return model.hasChildren();
	}
}
