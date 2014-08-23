package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;

import com.google.inject.Inject;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.GraficInterfaceDiagram;
import fiuba.mda.model.Representation;
import fiuba.mda.ui.utilities.ImageLoader;

public class ExportToImage extends Action {
	private GraficInterfaceDiagram boundDiagram;
	private int formNumber = 0;
	private final Shell shell;

	/**
	 * Creates a new {@link ExportToImage} instance
	 * 
	 * @param imageLoader
	 *            the image loader used to provide the image of this action
	 */
	@Inject
	public ExportToImage(final Shell shell, final ImageLoader imageLoader) {
		this.shell = shell;
		setupPresentation(imageLoader);
	}

	private void setupPresentation(final ImageLoader imageLoader) {
		setText("Exportar a imagen");
		setToolTipText("Genera una imagen del diagrama actual");
		setImageDescriptor(imageLoader.descriptorOf("camera"));
	}

	/**
	 * Binds this action to work on a given {@link BehaviorDiagram} instance
	 * 
	 * @param diagram
	 *            the diagram to bind this action to
	 * @return this for method chaining
	 */
	public ExportToImage boundTo(final GraficInterfaceDiagram diagram) {
        boundDiagram = diagram;
        return this;
    }

	@Override
	public void run() {

	}
}
