package fiuba.mda.ui.main;

import org.eclipse.jface.action.MenuManager;
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
import fiuba.mda.ui.actions.NewProjectAction;

@Singleton
public class MainWindow extends ApplicationWindow {
	private final ProjectTreeBuilder projectTreeBuilder;
	private final DiagramEditorBuilder diagramEditorBuilder;
	private final NewProjectAction newProjectAction;

	@Inject
	public MainWindow(final DocumentModel documentModel,
			final ProjectTreeBuilder projectTreeBuilder,
			final DiagramEditorBuilder diagramEditorBuilder,
			final NewProjectAction newProjectAction) {
		super(null);
		this.projectTreeBuilder = projectTreeBuilder;
		this.diagramEditorBuilder = diagramEditorBuilder;
		this.newProjectAction = newProjectAction;

		this.addToolBar(SWT.FLAT | SWT.WRAP);
		this.addMenuBar();
	}

	@Override
	protected void configureShell(final Shell shell) {
		super.configureShell(shell);
		shell.setText("MDA IDE");
		shell.setSize(800, 600);
		shell.setMaximized(true);
	}

	@Override
	protected MenuManager createMenuManager() {
		MenuManager result = new MenuManager();
		result.add(newProjectAction);
		return result;
	}

	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager result = new ToolBarManager(style);
		result.add(newProjectAction);
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
