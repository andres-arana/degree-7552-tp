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

@Singleton
public class MainWindow extends ApplicationWindow {
	private final ProjectTreeBuilder projectTreeBuilder;
	private final DiagramEditorBuilder diagramEditorBuilder;
	private final ToolBarActionSet toolBarActions;

	@Inject
	public MainWindow(final Shell shell, final DocumentModel documentModel,
			final ProjectTreeBuilder projectTreeBuilder,
			final DiagramEditorBuilder diagramEditorBuilder,
			final ToolBarActionSet toolBarActions) {
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

		projectTreeBuilder.buildInto(sash);
		diagramEditorBuilder.buildInto(sash);

		sash.setWeights(new int[] { 3, 7 });

		return sash;
	}

}
