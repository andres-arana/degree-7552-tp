package fiuba.mda.ui.launchers.editors;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayeredPane;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.ui.main.MainWindow;
import fiuba.mda.ui.main.tree.ComponentImageVisitor;
import fiuba.mda.ui.main.workspace.ControlBuilder;
import fiuba.mda.ui.utilities.ImageLoader;

/**
 * {@link EditorLauncher} implementation which allows editing a behavior diagram
 */
@Singleton
public class BehaviorDiagramLauncher extends BaseLauncher<BehaviorDiagram>
		implements ControlBuilder {
	private final MainWindow mainWindow;
	private final ComponentImageVisitor imageVisitor;
	private final ImageLoader imageLoader;

	/**
	 * Creates a new @{link BehaviorDiagramLauncher} instance
	 * 
	 * @param mainWindow
	 *            the main window on which behavior diagram editors will be
	 *            added
	 * @param imageVisitor
	 *            the image visitor to get the image for the tab
	 * @param imageLoader
	 *            the image loader to use for toolbar images
	 */
	@Inject
	public BehaviorDiagramLauncher(final MainWindow mainWindow,
			final ComponentImageVisitor imageVisitor,
			final ImageLoader imageLoader) {
		this.mainWindow = mainWindow;
		this.imageVisitor = imageVisitor;
		this.imageLoader = imageLoader;
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
		Composite result = new Composite(parent, SWT.NONE);
		Layout layout = new GridLayout(1, false);
		GridData gridData = null;
		result.setLayout(layout);

		ToolBar toolBar = new ToolBar(result, SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		toolBar.setLayoutData(gridData);

		// TODO: Define actions for this editor here
		ToolItem item = new ToolItem(toolBar, SWT.PUSH);
		item.setImage(imageLoader.of("application_form_add"));
		item.setToolTipText("Agregar Estado");

		FigureCanvas canvas = new FigureCanvas(result, SWT.V_SCROLL
				| SWT.H_SCROLL | SWT.DOUBLE_BUFFERED);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		canvas.setLayoutData(gridData);
		canvas.setHorizontalScrollBarVisibility(FigureCanvas.ALWAYS);
		canvas.setVerticalScrollBarVisibility(FigureCanvas.ALWAYS);
		canvas.setViewport(new FreeformViewport());

		FreeformLayeredPane root = new FreeformLayeredPane();
		FreeformLayer primaryLayer = new FreeformLayer();
		primaryLayer.setLayoutManager(new FreeformLayout());
		root.add(primaryLayer);

		ConnectionLayer connectionsLayer = new ConnectionLayer();
		connectionsLayer.setConnectionRouter(new ShortestPathConnectionRouter(
				connectionsLayer));
		root.add(connectionsLayer);

		// TODO: Define a way to display the diagram states here

		Ellipse ellipse = new Ellipse();
		ellipse.setAntialias(SWT.ON);
		ellipse.setSize(100, 50);
		ellipse.setOutline(true);
		ellipse.setLineWidth(2);

		primaryLayer.add(ellipse);

		canvas.setContents(root);

		ellipse.setLocation(new Point(300, 0));

		return result;
	}
}
