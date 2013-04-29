package fiuba.mda.ui.dialogs;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SimpleDialogController {
	private final Shell shell;

	@Inject
	public SimpleDialogController(final Shell shell) {
		this.shell = shell;
	}

	public void showInfo(final String message) {
		MessageDialog.openInformation(shell, "Información", message);
	}

	public boolean showConfirm(final String message) {
		return MessageDialog
				.openConfirm(shell, "Confirme su elección", message);
	}

	public void showWarn(final String message) {
		MessageDialog.openWarning(shell, "Advertencia", message);
	}

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

	public void showError(final String message) {
		MessageDialog.openError(shell, "Ha ocurrido un error", message);
	}
}
