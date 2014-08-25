package fiuba.mda.ui.actions;

import fiuba.mda.model.*;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import fiuba.mda.ui.launchers.SimpleDialogLauncher;
import fiuba.mda.ui.main.workspace.StateDialog;
import fiuba.mda.ui.utilities.ImageLoader;
import fiuba.mda.ui.figures.GraficInterfaceDiagramFigure;

import fiuba.mda.model.GraficInterfaceDiagram;

import java.util.List;

/**
 * {@link Action} implementation which represents the command of creating a new
 * behavior state in the behavior diagram
 */
public class DeleteSelectedGraphicDiagramAction extends Action {
    private final Application model;
    private final SimpleDialogLauncher dialog;
	private GraficInterfaceDiagram boundDiagram;
	private GraficInterfaceDiagramFigure boundFigure;
	private int stateNumber = 0;
	private final Shell shell;

	/**
	 * Creates a new {@link DeleteSelectedGraphicDiagramAction} instance
	 *
     * @param imageLoader
     *            the image loader used to provide the image of this action
     * @param model
     * @param dialog
     */
	@Inject
	public DeleteSelectedGraphicDiagramAction(final Shell shell,
                                         final ImageLoader imageLoader, Application model, SimpleDialogLauncher dialog) {
		this.shell = shell;
        this.model = model;
        this.dialog = dialog;
		setupPresentation(imageLoader);
	}

	private void setupPresentation(final ImageLoader imageLoader) {
		setText("Eliminar");
		setToolTipText("Elimina elementos seleccionados del digrama actual");
		setImageDescriptor(imageLoader.descriptorOf("delete"));
	}

	/**
	 * Binds this action to work on a given {@link GraficInterfaceDiagram} instance
	 * 
	 * @param diagram
	 *            the diagram to bind this action to
	 * @return this for method chaining
	 */
	public DeleteSelectedGraphicDiagramAction boundTo(final GraficInterfaceDiagram diagram, final GraficInterfaceDiagramFigure figure) {
		boundDiagram = diagram;
		boundFigure = figure;
		return this;
	}

	@Override
	public void run() {
		boundFigure.removeSelectedObjects();
	}
}
