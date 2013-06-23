package fiuba.mda.ui.main.tree;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.Application;
import fiuba.mda.ui.main.workspace.ControlBuilder;

/**
 * Builder which creates a new project tree
 */
@Singleton
public class ProjectTreeBuilder implements ControlBuilder {
	private final Application model;

	private final ProjectTreeLabelProvider labelProvider;

	private final ProjectTreeContentProvider contentProvider;

	private final PackageSelectedListener onSelectionChanged;

	private final NodeDobleClickListener onDoubleClick;

	/**
	 * Creates a new {@link ProjectTreeBuilder} instance
	 * 
	 * @param model
	 *            the model which will be watched by the project tree
	 * @param labelProvider
	 *            the provider of labels for the project tree
	 * @param contentProvider
	 *            the provider of content for the project tree
	 * @param onSelectionChanged
	 *            the listener in charge of handling the selection of the
	 *            current package
	 * @param onDoubleClick
	 *            the listener in charge of handling the double click on a node
	 */
	@Inject
	public ProjectTreeBuilder(final Application model,
			final ProjectTreeLabelProvider labelProvider,
			final ProjectTreeContentProvider contentProvider,
			final PackageSelectedListener onSelectionChanged,
			final NodeDobleClickListener onDoubleClick) {
		this.model = model;
		this.labelProvider = labelProvider;
		this.contentProvider = contentProvider;
		this.onSelectionChanged = onSelectionChanged;
		this.onDoubleClick = onDoubleClick;
	}

	/**
	 * Creates a new project tree in the given parent
	 * 
	 * @param parent
	 *            the composite to build the new project tree into
	 * @return The built {@link TreeViewer} instance
	 */
	public Control buildInto(Composite parent) {
		TreeViewer treeViewer = new TreeViewer(parent);
		treeViewer.setContentProvider(contentProvider);
		treeViewer.setLabelProvider(labelProvider);
		treeViewer.addSelectionChangedListener(onSelectionChanged);
		treeViewer.addDoubleClickListener(onDoubleClick);
		treeViewer.setInput(model);
		treeViewer.expandAll();
		return treeViewer.getControl();
	}
}
