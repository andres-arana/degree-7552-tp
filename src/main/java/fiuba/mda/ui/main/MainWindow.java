package fiuba.mda.ui.main;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.Application;
import fiuba.mda.ui.main.tree.ProjectTreeBuilder;
import fiuba.mda.ui.main.workspace.TabsFolder;

/**
 * Main application window
 */
@Singleton
public class MainWindow extends ApplicationWindow {
	private final ProjectTreeBuilder projectTreeBuilder;

	private final DiagramEditorBuilder diagramEditorBuilder;

	private final ToolBarActionProvider toolBarActions;

	private TabsFolder toolTabs;

	private TabsFolder editorTabs;

	/**
	 * Creates a new {@link MainWindow} instance
	 * 
	 * @param shell
	 *            the shell which will contain this window
	 * @param documentModel
	 *            the model on which this application window will operate
	 * @param projectTreeBuilder
	 *            the builder which will provide the project tree
	 * @param diagramEditorBuilder
	 *            the builder which will provide the diagram editor
	 * @param toolBarActions
	 *            the provider of all toolbar actions
	 */
	@Inject
	public MainWindow(final Shell shell, final Application documentModel,
			final ProjectTreeBuilder projectTreeBuilder,
			final DiagramEditorBuilder diagramEditorBuilder,
			final ToolBarActionProvider toolBarActions) {
		super(shell);
		this.projectTreeBuilder = projectTreeBuilder;
		this.diagramEditorBuilder = diagramEditorBuilder;
		this.toolBarActions = toolBarActions;

		this.addToolBar(SWT.FLAT | SWT.WRAP);
	}

	@Override
	protected void configureShell(final Shell shell) {
		super.configureShell(shell);
		shell.setText("MDA IDE");
		shell.setSize(800, 600);
	}

	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager result = new ToolBarManager(style);
		toolBarActions.provideActions(result);
		return result;
	}

	@Override
	protected Control createContents(final Composite parent) {
		SashForm sash = new SashForm(parent, SWT.HORIZONTAL);
		sash.setSashWidth(5);
		
		toolTabs = new TabsFolder(sash, SWT.None);
		toolTabs.addTab("Explorador de proyecto", projectTreeBuilder);
		
		editorTabs = new TabsFolder(sash, SWT.None);
		editorTabs.addTab("Editores", diagramEditorBuilder);
		

		sash.setWeights(new int[] { 3, 7 });

		return sash;
	}

}
