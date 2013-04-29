package legacy.mer.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Manages the SWT event loop to keep the application going until the main shell
 * is closed.
 */
@Singleton
public class EventLoop {
	/**
	 * The shell which will contain all application windows
	 */
	private final Shell shell;

	/**
	 * The display which contains the application shell
	 */
	private final Display display;

	/**
	 * Creates a new {@link EventLoop} instance
	 */
	@Inject
	public EventLoop(final Shell shell, final Display display) {
		this.shell = shell;
		this.display = display;
	}

	/**
	 * Loops dispatching application events until the shell is closed
	 */
	public void doEventLoop() {
		while (!shell.isDisposed())
			while (!display.readAndDispatch())
				display.sleep();
	}

}
