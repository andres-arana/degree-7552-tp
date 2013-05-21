package fiuba.mda.ui.main.projectTree;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.DocumentModel;

/**
 * Builder which creates a new project tree
 */
@Singleton
public class ProjectTreeBuilder {
	/**
	 * The {@link DocumentModel} instance to watch and update the tree from
	 */
	private final DocumentModel model;

	/**
	 * The {@link LabelProvider} instance for the project tree
	 */
	private final ProjectTreeLabelProvider labelProvider;

	/**
	 * The {@link ITreeContentProvider} instance for the project tree
	 */
	private final ProjectTreeContentProvider contentProvider;

	/**
	 * The {@link ISelectionChangedListener} instance which handles selection
	 * changed events on the project tree
	 */
	private final PackageSelectedListener onSelectionChanged;

	/**
	 * Creates a new {@link ProjectTreeBuilder} instance
	 */
	@Inject
	public ProjectTreeBuilder(final DocumentModel model,
			final ProjectTreeLabelProvider labelProvider,
			final ProjectTreeContentProvider contentProvider,
			final PackageSelectedListener onSelectionChanged) {
		this.model = model;
		this.labelProvider = labelProvider;
		this.contentProvider = contentProvider;
		this.onSelectionChanged = onSelectionChanged;
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
		treeViewer.setInput(model);
		treeViewer.expandAll();

		item.setControl(treeViewer.getControl());

		tabs.setSelection(item);
	}
}
