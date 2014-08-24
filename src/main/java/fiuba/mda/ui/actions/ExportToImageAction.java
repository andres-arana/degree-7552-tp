package fiuba.mda.ui.actions;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.google.inject.Inject;

import fiuba.mda.model.GraficInterfaceDiagram;
import fiuba.mda.ui.utilities.ImageLoader;

public class ExportToImageAction extends Action {
	private ExportableToImage boundDiagram;
	private final Shell shell;

	/**
	 * Creates a new {@link ExportToImageAction} instance
	 * 
	 * @param imageLoader
	 *            the image loader used to provide the image of this action
	 */
	@Inject
	public ExportToImageAction(final Shell shell, final ImageLoader imageLoader) {
		this.shell = shell;
		setupPresentation(imageLoader);
	}
	
	private void setupPresentation(final ImageLoader imageLoader) {
		setText("Exportar a imagen");
		setToolTipText("Exportar a imagen el diagrama actual");
		setImageDescriptor(imageLoader.descriptorOf("camera"));
	}

	/**
	 * Binds this action to work on a given {@link GraficInterfaceDiagram} instance 
	 * 
	 * @param diagram
	 *            the diagram to bind this action to
	 * @return this for method chaining
	 */
	public ExportToImageAction boundTo(final ExportableToImage diagram) {
        boundDiagram = diagram;
        return this;
    }

	@Override
	public void run() {
		exportImage(5, "diagrama.png", this.boundDiagram.getDiagramFigure());
    }

	public static void exportImage(int format, String filename, IFigure rootFigure) {
		// get the bounding rectangle around the figure's children
		Rectangle boundingBox = getBoundingBox(rootFigure);
		
		// don't export if the canvas is empty
		if (boundingBox == null)
			return;

		// obtain a painter and a
		Image diagramImage = new Image(Display.getDefault(),
		boundingBox.width, boundingBox.height);

		GC gc = new GC(diagramImage);

		SWTGraphics swtGraphics = new SWTGraphics(gc);

		// place the painter to the boundingBox's (0,0)
		swtGraphics.translate(boundingBox.x * -1, boundingBox.y * -1);
		paintChildren(swtGraphics, rootFigure);

		// save the image in the given format
		org.eclipse.swt.graphics.ImageLoader imgLoader = new org.eclipse.swt.graphics.ImageLoader();
		imgLoader.data = new ImageData[] { diagramImage.getImageData() };
		imgLoader.save(filename, format);
		swtGraphics.dispose();
		gc.dispose();
		diagramImage.dispose();
	}

	private static Rectangle getBoundingBox(IFigure figure) {
		Rectangle boundingBox = null;
		for (Object layer : figure.getChildren()) {
			Rectangle bounds;
			if (layer instanceof FreeformLayer) {
				bounds = getBoundingBox((IFigure) layer);
			} else {
				bounds = ((IFigure) layer).getBounds().getCopy();
			}
			if (boundingBox != null) {
				boundingBox.union(bounds);
			} else {
				boundingBox = bounds;
			}

		}
		return boundingBox;
	}

	private static void paintChildren(Graphics g, IFigure figure) {
		for (Object child : figure.getChildren()) {
			// ConnectionLayer does not contain children -> paint directly
			// else paint Children
			if (child instanceof FreeformLayer && !(child instanceof ConnectionLayer)) {
				paintChildren(g, (IFigure) child);
			} else {
				((IFigure) child).paint(g);
			}
		}
	}	
	
}
