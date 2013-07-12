package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.BehaviorField;
import fiuba.mda.model.Representation;
import fiuba.mda.ui.launchers.SimpleDialogLauncher;
import fiuba.mda.ui.main.workspace.FieldDialog;
import fiuba.mda.ui.utilities.ImageLoader;

public class NewFieldAction extends Action {
	private final SimpleDialogLauncher dialog;
	private BehaviorDiagram boundDiagram;
	private int fieldNumber = 0;
	private final Shell shell;

	/**
	 * Creates a new {@link NewFieldAction} instance
	 * 
	 * @param imageLoader
	 *            the image loader used to provide the image of this action
	 * @param dialog
	 */
	@Inject
	public NewFieldAction(final Shell shell, final ImageLoader imageLoader, SimpleDialogLauncher dialog) {
		this.shell = shell;
		this.dialog = dialog;
		setupPresentation(imageLoader);
	}

	private void setupPresentation(final ImageLoader imageLoader) {
		setText("Nuevo Campo");
		setToolTipText("Crear un nuevo campo en el diagrama actual");
		setImageDescriptor(imageLoader.descriptorOf("textfield_add"));
	}

	/**
	 * Binds this action to work on a given {@link BehaviorDiagram} instance
	 * 
	 * @param diagram
	 *            the diagram to bind this action to
	 * @return this for method chaining
	 */
	public NewFieldAction boundTo(final BehaviorDiagram diagram) {
		boundDiagram = diagram;
		return this;
	}

	@Override
	public void run() {
		FieldDialog dialogo = new FieldDialog(shell);
		Optional<String> response = dialog.showDialog(dialogo);
		
		if (response.isPresent()) {
			BehaviorField field = new BehaviorField(!dialogo.getFieldName().equals("") ? dialogo.getFieldName():dialogo.getPropertyName());
			Representation<BehaviorField> representation = new Representation<>(field);
			representation.getPosition().setX(fieldNumber * 100);
			boundDiagram.addField(representation);
			fieldNumber++;
		}

	}
}
