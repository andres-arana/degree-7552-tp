package fiuba.mda.ui.main.projectTree;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import com.google.inject.Inject;

import fiuba.mda.model.DocumentModel;
import fiuba.mda.model.ProjectComponent;

/**
 * {@link ISelectionChangedListener} implementation for the project tree
 */
public class PackageSelectedListener implements ISelectionChangedListener {
	/**
	 * The {@link DocumentModel} instance to modify on selection events
	 */
	private final DocumentModel model;

	/**
	 * Creates a new {@link PackageSelectedListener} instance
	 */
	@Inject
	public PackageSelectedListener(DocumentModel model) {
		this.model = model;
	}

	/**
	 * Override
	 * {@link ISelectionChangedListener#selectionChanged(SelectionChangedEvent)}
	 * to set the active package after a package is selected on the project
	 * tree.
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		if (event.getSelection().isEmpty()) {
			model.clearActivePackage();
		} else {
			IStructuredSelection selection = (IStructuredSelection) event
					.getSelection();
			ProjectComponent selectedComponent = (ProjectComponent) selection
					.getFirstElement();
			model.activatePackage(selectedComponent.closestOwningPackage());
		}
	}
}