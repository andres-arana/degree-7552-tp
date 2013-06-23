package fiuba.mda.ui.launchers.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.ui.main.MainWindow;
import fiuba.mda.ui.main.tree.ComponentImageVisitor;
import fiuba.mda.ui.main.workspace.ControlBuilder;

/**
 * {@link EditorLauncher} implementation which allows editing a behavior diagram
 */
@Singleton
public class BehaviorDiagramLauncher extends BaseLauncher<BehaviorDiagram> {
	private final MainWindow mainWindow;
	private final ComponentImageVisitor imageVisitor;

	/**
	 * Creates a new @{link BehaviorDiagramLauncher} instance
	 * 
	 * @param mainWindow
	 *            the main window on which behavior diagram editors will be
	 *            added
	 * @param imageVisitor
	 *            the image visitor to get the image for the tab
	 */
	@Inject
	public BehaviorDiagramLauncher(final MainWindow mainWindow,
			final ComponentImageVisitor imageVisitor) {
		this.mainWindow = mainWindow;
		this.imageVisitor = imageVisitor;
	}

	@Override
	protected void doLaunch(BehaviorDiagram component) {
		final String name = component.getQualifiedName();
		final Optional<Image> optionalImage = imageVisitor.imageFor(component);
		final Image image = optionalImage.isPresent() ? optionalImage.get()
				: null;

		mainWindow.ensureEditor(name, image, new ControlBuilder() {
			@Override
			public Control buildInto(Composite parent) {
				Label result = new Label(parent, SWT.NONE);
				result.setText("Behavior diagram");
				result.pack(true);
				return result;
			}
		});
	}
}
