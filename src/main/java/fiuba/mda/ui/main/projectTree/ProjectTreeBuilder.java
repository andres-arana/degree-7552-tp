package fiuba.mda.ui.main.projectTree;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.Application;

/**
 * Builder which creates a new project tree
 */
@Singleton
public class ProjectTreeBuilder {
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
	 */
	public void buildInto(Composite parent) {
		Composite result = new Composite(parent, SWT.NONE);
		result.setLayout(new FillLayout(SWT.VERTICAL));

		CTabFolder tabs = new CTabFolder(result, SWT.NONE);
		tabs.setSimple(false);
		tabs.setBorderVisible(true);

		CTabItem item = new CTabItem(tabs, SWT.NONE);
		item.setText("Explorador de proyecto");

		TreeViewer treeViewer = new TreeViewer(tabs);
		treeViewer.setContentProvider(contentProvider);
		treeViewer.setLabelProvider(labelProvider);
		treeViewer.addSelectionChangedListener(onSelectionChanged);
		treeViewer.addDoubleClickListener(onDoubleClick);
		treeViewer.setInput(model);
		treeViewer.expandAll();

		item.setControl(treeViewer.getControl());

		tabs.setSelection(item);
	}
}
