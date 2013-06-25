package fiuba.mda.ui.launchers.editors;

import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.ui.main.MainWindow;
import fiuba.mda.ui.main.tree.ComponentImageVisitor;
import fiuba.mda.ui.main.workspace.ControlBuilder;
import fiuba.mda.ui.main.workspace.DiagramEditor;

/**
 * {@link EditorLauncher} implementation which allows editing a behavior diagram
 */
@Singleton
public class BehaviorDiagramLauncher extends BaseLauncher<BehaviorDiagram>
		implements ControlBuilder {
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

		mainWindow.ensureEditor(name, image, this);
	}

	@Override
	public Control buildInto(Composite parent) {
		DiagramEditor diagramEditor = new DiagramEditor(parent, SWT.NONE);

		Ellipse ellipse = new Ellipse();
		ellipse.setAntialias(SWT.ON);
		ellipse.setSize(100, 50);
		ellipse.setOutline(true);
		ellipse.setLineWidth(2);
		ellipse.setLocation(new Point(300, 150));

		diagramEditor.addFigure(ellipse);

		return diagramEditor;
	}
}
