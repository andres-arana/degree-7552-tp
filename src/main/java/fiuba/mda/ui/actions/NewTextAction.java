package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.BehaviorText;
import fiuba.mda.model.Representation;
import fiuba.mda.ui.launchers.SimpleDialogLauncher;
import fiuba.mda.ui.main.workspace.TextDialog;
import fiuba.mda.ui.utilities.ImageLoader;

public class NewTextAction extends Action {
	private final SimpleDialogLauncher dialog;
	private BehaviorDiagram boundDiagram;
	private int textNumber = 0;
	private final Shell shell;

	/**
	 * Creates a new {@link NewTextAction} instance
	 * 
	 * @param imageLoader
	 *            the image loader used to provide the image of this action
	 * @param dialog
	 */
	@Inject
	public NewTextAction(final Shell shell,
			final ImageLoader imageLoader, SimpleDialogLauncher dialog) {
		this.shell = shell;
		this.dialog = dialog;
		setupPresentation(imageLoader);
	}

	private void setupPresentation(final ImageLoader imageLoader) {
		setText("Nuevo Texto");
		setToolTipText("Crear un nuevo texto en el diagrama actual");
		setImageDescriptor(imageLoader.descriptorOf("text_align_left"));
	}

	/**
	 * Binds this action to work on a given {@link BehaviorDiagram} instance
	 * 
	 * @param diagram
	 *            the diagram to bind this action to
	 * @return this for method chaining
	 */
	public NewTextAction boundTo(final BehaviorDiagram diagram) {
		boundDiagram = diagram;
		return this;
	}

	@Override
	public void run() {
		TextDialog dialogo = new TextDialog(shell);
		Optional<String> stringOptional = dialog.showDialog(dialogo);
		
		if (stringOptional.isPresent()) {
			BehaviorText text = new BehaviorText(dialogo.getTextString());
			Representation<BehaviorText> representation = new Representation<>(text);
			representation.getPosition().setX(textNumber * 100);
			boundDiagram.addText(representation);
			textNumber++;
		}

	}
}
