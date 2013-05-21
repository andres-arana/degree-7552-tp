package fiuba.mda.ui.dialogs;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Controller which exposes services to show simple dialogs, such as information
 * and input boxes
 */
@Singleton
public class SimpleDialogController {
	private final Shell shell;

	/**
	 * Creates a new {@link SimpleDialogController} instance
	 */
	@Inject
	public SimpleDialogController(final Shell shell) {
		this.shell = shell;
	}

	/**
	 * Shows a dialog to display an information message
	 * 
	 * @param message
	 *            the message to display
	 */
	public void showInfo(final String message) {
		MessageDialog.openInformation(shell, "Información", message);
	}

	/**
	 * Shows a dialog to ask for confirmation. The dialog contains standard
	 * yes/no buttons. Returns true if the user confirmed the dialog, false
	 * otherwise.
	 * 
	 * @param message
	 *            the message to display in the dialog
	 */
	public boolean showConfirm(final String message) {
		return MessageDialog
				.openConfirm(shell, "Confirme su elección", message);
	}

	/**
	 * Shows a dialog to display a warning.
	 * 
	 * @param message
	 *            the message to display
	 */
	public void showWarn(final String message) {
		MessageDialog.openWarning(shell, "Advertencia", message);
	}

	/**
	 * Shows a dialog which asks the user for a value. Returns the entered value
	 * or false if the user cancels the operation.
	 * 
	 * @param title
	 *            the title of the dialog
	 * @param message
	 *            the message to display
	 * @param initialValue
	 *            the initial value of the input on which the user enters the
	 *            new value. Can be null if no initial value should be set.
	 * @param validator
	 *            the validator instance which validates if the input is valid.
	 *            If the input is not valid, the user won't be able to accept
	 *            the dialog. Can be null if no validations should be made.
	 */
	public String showInput(final String title, final String message,
			final String initialValue, final IInputValidator validator) {
		InputDialog inputDialog = new InputDialog(shell, title, message,
				initialValue, validator);
		int result = inputDialog.open();
		if (result == Window.OK) {
			return inputDialog.getValue();
		} else {
			return null;
		}
	}

	/**
	 * Shows a dialog to display an error message
	 * 
	 * @param message
	 *            the message to display
	 */
	public void showError(final String message) {
		MessageDialog.openError(shell, "Ha ocurrido un error", message);
	}
}
