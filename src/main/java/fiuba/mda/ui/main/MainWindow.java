package fiuba.mda.ui.main;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.DocumentModel;
import fiuba.mda.ui.main.projectTree.ProjectTreeBuilder;

/**
 * Main application window
 */
@Singleton
public class MainWindow extends ApplicationWindow {
	/**
	 * Builder for the project tree
	 */
	private final ProjectTreeBuilder projectTreeBuilder;

	/**
	 * Builder for the diagram editor
	 */
	private final DiagramEditorBuilder diagramEditorBuilder;

	/**
	 * Set of actions to include in the toolbar
	 */
	private final ToolBarActionProvider toolBarActions;

	/**
	 * Creates a new {@link MainWindow} instance
	 */
	@Inject
	public MainWindow(final Shell shell, final DocumentModel documentModel,
			final ProjectTreeBuilder projectTreeBuilder,
			final DiagramEditorBuilder diagramEditorBuilder,
			final ToolBarActionProvider toolBarActions) {
		super(shell);
		this.projectTreeBuilder = projectTreeBuilder;
		this.diagramEditorBuilder = diagramEditorBuilder;
		this.toolBarActions = toolBarActions;

		this.addToolBar(SWT.FLAT | SWT.WRAP);
	}

	/**
	 * Overrides {@link ApplicationWindow#configureShell} to configure the
	 * window container
	 */
	@Override
	protected void configureShell(final Shell shell) {
		super.configureShell(shell);
		shell.setText("MDA IDE");
		shell.setSize(800, 600);
	}

	/**
	 * Overrides {@link ApplicationWindow#createToolBarManager} to configure the
	 * window toolbar
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager result = new ToolBarManager(style);
		toolBarActions.provideActions(result);
		return result;
	}

	/**
	 * Overrides {@link ApplicationWindow#createControls} to configure the
	 * window contents
	 */
	@Override
	protected Control createContents(final Composite parent) {
		SashForm sash = new SashForm(parent, SWT.HORIZONTAL);
		sash.setSashWidth(5);

		projectTreeBuilder.buildInto(sash);
		diagramEditorBuilder.buildInto(sash);

		sash.setWeights(new int[] { 3, 7 });

		return sash;
	}

}
