package fiuba.mda.ui.actions;

import fiuba.mda.model.GraficInterfaceDiagram;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import fiuba.mda.model.BehaviorButton;
import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.Representation;
import fiuba.mda.ui.launchers.SimpleDialogLauncher;
import fiuba.mda.ui.main.workspace.ButtonDialog;
import fiuba.mda.ui.utilities.ImageLoader;

public class NewButtonAction extends Action {
	private final SimpleDialogLauncher dialog;
	private GraficInterfaceDiagram boundDiagram;
	private int buttonNumber = 0;
	private final Shell shell;

	/**
	 * Creates a new {@link NewButtonAction} instance
	 * 
	 * @param imageLoader
	 *            the image loader used to provide the image of this action
	 * @param dialog
	 */
	@Inject
	public NewButtonAction(final Shell shell, final ImageLoader imageLoader, SimpleDialogLauncher dialog) {
		this.shell = shell;
		this.dialog = dialog;
		setupPresentation(imageLoader);
	}

	private void setupPresentation(final ImageLoader imageLoader) {
		setText("Nuevo Botón");
		setToolTipText("Crear un nuevo botón en el diagrama actual");
		setImageDescriptor(imageLoader.descriptorOf("keyboard_add"));
	}

	/**
	 * Binds this action to work on a given {@link BehaviorDiagram} instance
	 * 
	 * @param diagram
	 *            the diagram to bind this action to
	 * @return this for method chaining
	 */
	public NewButtonAction boundTo(final GraficInterfaceDiagram diagram) {
		boundDiagram = diagram;
		return this;
	}

	@Override
	public void run() {
		ButtonDialog dialogo = new ButtonDialog(shell);
		Optional<String> stringOptional = dialog.showDialog(dialogo);
		
		if (stringOptional.isPresent()) {
			BehaviorButton button = new BehaviorButton(dialogo.getLabelString());
			Representation<BehaviorButton> representation = new Representation<>(button);
			representation.getPosition().setX(buttonNumber * 100);
			boundDiagram.addButton(representation);
			buttonNumber++;
		}

	}
}
