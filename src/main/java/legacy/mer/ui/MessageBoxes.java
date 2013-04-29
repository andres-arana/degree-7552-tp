package legacy.mer.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Exposes services to display short, styled message boxes.
 */
@Singleton
public class MessageBoxes {
	/**
	 * The shell where you want the message boxes to be displayed
	 */
	private Shell shell;

	/**
	 * Creates a new {@link MessageBoxes} instance
	 */
	@Inject
	public MessageBoxes(final Shell shell) {
		this.shell = shell;
	}

	/**
	 * Displays an error message
	 * 
	 * @param message
	 *            the message to display. If null, a generic error message will
	 *            be displayed instead.
	 */
	public void error(final String message) {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		messageBox.setText("Error");
		messageBox.setMessage(message != null ? message : "Ocurrió un error");
		messageBox.open();
	}

	/**
	 * Displays a warning message
	 * 
	 * @param message
	 *            the message to display.
	 */
	public void warning(final String message) {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
		messageBox.setText("Advertencia");
		messageBox.setMessage(message);
		messageBox.open();
	}
}
