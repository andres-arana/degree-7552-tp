package fiuba.mda.ui.main.projectTree;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.ProjectComponent;
import fiuba.mda.ui.controllers.EditorController;

/**
 * {@link IDoubleClickListener} implementation which launches the editor for a
 * given double clicked component on the project tree.
 */
@Singleton
public class NodeDobleClickListener implements IDoubleClickListener {
	private final ComponentEditorSelector selector;

	/**
	 * Creates a new @{link NodeDobleClickListener} instance
	 * 
	 * @param selector
	 *            the selector which provides the editors for a double clicked
	 *            component
	 */
	@Inject
	public NodeDobleClickListener(ComponentEditorSelector selector) {
		this.selector = selector;
	}

	@Override
	public void doubleClick(DoubleClickEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event
				.getSelection();
		ProjectComponent selectedComponent = (ProjectComponent) selection
				.getFirstElement();
		
		Optional<EditorController> controller = selector.fromInstance(selectedComponent);
		if (controller.isPresent()) {
			controller.get().launch(selectedComponent);
		}
	}
}
