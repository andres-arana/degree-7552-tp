package fiuba.mda.ui.main.tree;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

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

	private final NodeMenuListener onMenu;

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
	 */
	@Inject
	public ProjectTreeBuilder(final Application model,
			final ProjectTreeLabelProvider labelProvider,
			final ProjectTreeContentProvider contentProvider,
			final PackageSelectedListener onSelectionChanged,
			final NodeDobleClickListener onDoubleClick,
			final NodeMenuListener onMenu) {
		this.model = model;
		this.labelProvider = labelProvider;
		this.contentProvider = contentProvider;
		this.onSelectionChanged = onSelectionChanged;
		this.onDoubleClick = onDoubleClick;
		this.onMenu = onMenu;
	}

	/**
	 * Creates a new project tree in the given parent
	 * 
	 * @param parent
	 *            the composite to build the new project tree into
	 * @return The built {@link TreeViewer} instance
	 */
	public Control buildInto(Composite parent) {
		final TreeViewer treeViewer = new TreeViewer(parent);
		treeViewer.setContentProvider(contentProvider);
		treeViewer.setLabelProvider(labelProvider);
		treeViewer.addSelectionChangedListener(onSelectionChanged);
		treeViewer.addDoubleClickListener(onDoubleClick);

		MenuManager menuManager = new MenuManager();
		menuManager.addMenuListener(onMenu);
		menuManager.setRemoveAllWhenShown(true);
		Menu menu = menuManager.createContextMenu(treeViewer.getControl());

		treeViewer.getControl().setMenu(menu);
		treeViewer.setInput(model);
		treeViewer.expandAll();
		return treeViewer.getControl();
	}
}
