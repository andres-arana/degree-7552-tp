package fiuba.mda.ui.launchers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.ui.actions.NewBehaviorDiagramRelationAction;
import fiuba.mda.ui.actions.NewBehaviorDiagramStateAction;
import fiuba.mda.ui.actions.NewButtonAction;
import fiuba.mda.ui.actions.NewFieldAction;
import fiuba.mda.ui.actions.NewTextAction;
import fiuba.mda.ui.figures.BehaviorDiagramFigure;
import fiuba.mda.ui.main.MainWindow;
import fiuba.mda.ui.main.tree.ComponentImageVisitor;
import fiuba.mda.ui.main.workspace.ControlBuilder;
import fiuba.mda.ui.main.workspace.DiagramEditor;

/**
 * {@link Launcher} implementation which allows editing a behavior diagram
 */
@Singleton
public class BehaviorDiagramEditorLauncher extends
		BaseLauncher<BehaviorDiagram> {
	private final MainWindow mainWindow;
	private final ComponentImageVisitor imageVisitor;
	private final Provider<NewBehaviorDiagramStateAction> newStateActionProvider;
	private final Provider<NewBehaviorDiagramRelationAction> newRelationActionProvider;

	/**
	 * Creates a new @{link BehaviorDiagramLauncher} instance
	 * 
	 * @param mainWindow
	 *            the main window on which behavior diagram editors will be
	 *            added
	 * @param imageVisitor
	 *            the image visitor to get the image for the tab
	 * @param newStateActionProvider
	 *            provider for the instances of new state actions bound to the
	 * @param newRelationActionProvider
	 */
	@Inject
	public BehaviorDiagramEditorLauncher(
			final MainWindow mainWindow,
			final ComponentImageVisitor imageVisitor,
			final Provider<NewBehaviorDiagramStateAction> newStateActionProvider,
			final Provider<NewBehaviorDiagramRelationAction> newRelationActionProvider) {
		this.mainWindow = mainWindow;
		this.imageVisitor = imageVisitor;
		this.newStateActionProvider = newStateActionProvider;
		this.newRelationActionProvider = newRelationActionProvider;
	}

	@Override
	protected void doLaunch(final BehaviorDiagram component) {
		final String name = component.getQualifiedName();
		final Optional<Image> optionalImage = imageVisitor.imageFor(component);
		final Image image = optionalImage.isPresent() ? optionalImage.get()
				: null;

		mainWindow.ensureEditor(name, image, new ControlBuilder() {
			@Override
			public Control buildInto(Composite parent) {
				DiagramEditor editor = new DiagramEditor(parent, SWT.NONE);

				editor.addFigure(new BehaviorDiagramFigure(component));

				editor.addAction(newStateActionProvider.get()
						.boundTo(component));

				editor.addAction(newRelationActionProvider.get().boundTo(
						component));
				return editor;
			}
		});
	}
}
