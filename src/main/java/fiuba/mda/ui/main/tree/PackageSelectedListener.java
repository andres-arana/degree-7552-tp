package fiuba.mda.ui.main.tree;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import com.google.inject.Inject;

import fiuba.mda.model.Application;
import fiuba.mda.model.ProjectComponent;

/**
 * {@link ISelectionChangedListener} implementation for the project tree, which
 * sets the currently selected package when a selection event occurs on the
 * project tree
 */
public class PackageSelectedListener implements ISelectionChangedListener {
	private final Application model;

	/**
	 * Creates a new {@link PackageSelectedListener} instance
	 * 
	 * @param model
	 *            the model on which the action will select the current package
	 */
	@Inject
	public PackageSelectedListener(Application model) {
		this.model = model;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		if (event.getSelection().isEmpty()) {
			model.clearActivePackage();
		} else {
			IStructuredSelection selection = (IStructuredSelection) event
					.getSelection();
			ProjectComponent selectedComponent = (ProjectComponent) selection
					.getFirstElement();
			model.activatePackage(selectedComponent.locateOwningPackage());
		}
	}
}