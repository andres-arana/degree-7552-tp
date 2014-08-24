package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;

import com.google.inject.Inject;

import fiuba.mda.model.GraficInterfaceDiagram;
import fiuba.mda.ui.utilities.ImageLoader;

public class PrintDiagramAction extends Action {
	private ExportableToImage boundDiagram;
	private final Shell shell;

	/**
	 * Creates a new {@link PrintDiagramAction} instance
	 * 
	 * @param imageLoader
	 *            the image loader used to provide the image of this action
	 */
	@Inject
	public PrintDiagramAction(final Shell shell, final ImageLoader imageLoader) {
		this.shell = shell;
		setupPresentation(imageLoader);
	}
	
	private void setupPresentation(final ImageLoader imageLoader) {
		setText("Imprimir");
		setToolTipText("Imprimir el diagrama actual");
		setImageDescriptor(imageLoader.descriptorOf("printer"));
	}

	/**
	 * Binds this action to work on a given {@link GraficInterfaceDiagram} instance 
	 * 
	 * @param diagram
	 *            the diagram to bind this action to
	 * @return this for method chaining
	 */
	public PrintDiagramAction boundTo(final ExportableToImage diagram) {
        boundDiagram = diagram;
        return this;
    }

	@Override
	public void run() {
		ExportToImageAction.exportImage(5, "diagrama.png", this.boundDiagram.getDiagramFigure());
    }

}
