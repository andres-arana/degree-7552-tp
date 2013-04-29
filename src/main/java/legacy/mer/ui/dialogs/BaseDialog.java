package legacy.mer.ui.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * Base class for all dialogs.
 */
public abstract class BaseDialog extends org.eclipse.jface.dialogs.Dialog {
	/**
	 * Creates a new {@link BaseDialog} instance
	 */
	public BaseDialog(final Shell shell) {
		super(shell);
	}

	/**
	 * Obtains the dialog title to use on the title bar
	 */
	protected abstract String getTitle();

	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);

		Button btnOK = this.getButton(IDialogConstants.OK_ID);
		Button btnCancel = this.getButton(IDialogConstants.CANCEL_ID);
		btnOK.setText("Aceptar");
		btnCancel.setText("Cancelar");

		return control;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(getTitle());
	}
}
