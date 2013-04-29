package fiuba.mda.ui.main;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.google.inject.Singleton;

import fiuba.mda.model.DocumentModel;
import fiuba.mda.model.ObservableEvent.Observer;
import fiuba.mda.model.Project;
import fiuba.mda.model.ProjectComponent;

@Singleton
public class ProjectTreeContentProvider implements ITreeContentProvider {
	public class RefreshProjectTree implements Observer<DocumentModel, Project> {
		private final Viewer viewer;

		public RefreshProjectTree(Viewer viewer) {
			this.viewer = viewer;
		}

		@Override
		public void notify(DocumentModel observable, Project eventData) {
			viewer.refresh();
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

		DocumentModel newModel = (DocumentModel) newInput;
		newModel.getProjectOpenEvent().observe(new RefreshProjectTree(viewer));
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
		DocumentModel model = (DocumentModel) input;
		if (!model.hasProject()) {
			return new Object[] {};
		}
		Project project = model.getProject();
		return new Object[] { project.getRootPackage() };
	}

	@Override
	public Object getParent(final Object element) {
		if (element == null) {
			return null;
		}

		ProjectComponent model = (ProjectComponent) element;
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
