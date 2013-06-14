package fiuba.mda.ui.main.projectTree;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import fiuba.mda.model.ProjectComponent;
import fiuba.mda.ui.controllers.EditorController;

/**
 * {@link IDoubleClickListener} implementation which launches the editor for a
 * given double clicked component on the project tree.
 */
@Singleton
public class NodeDobleClickListener implements IDoubleClickListener {
	private final Provider<ComponentEditorVisitor> editorProvider;

	/**
	 * Creates a new @{link NodeDobleClickListener} instance
	 * 
	 * @param editorProvider
	 *            the {@link ComponentEditorVisitor} provider used to get the
	 *            associated editor of a selected model
	 */
	@Inject
	public NodeDobleClickListener(
			final Provider<ComponentEditorVisitor> editorProvider) {
		this.editorProvider = editorProvider;
	}

	@Override
	public void doubleClick(DoubleClickEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event
				.getSelection();
		ProjectComponent model = (ProjectComponent) selection.getFirstElement();

		Optional<EditorController> controller = editorProvider.get()
				.controllerFor(model);
		if (controller.isPresent()) {
			controller.get().launch(model);
		}
	}
}
