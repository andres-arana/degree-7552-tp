package fiuba.mda;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import fiuba.mda.injection.ApplicationModule;
import fiuba.mda.mer.interfaz.swt.Principal;

/**
 * Represents the executable application
 */
public class Application {
	/**
	 * The shell which will contain all application windows
	 */
	private final Shell shell;

	/**
	 * The display which contains the application shell
	 */
	private final Display display;

	/**
	 * Creates a new {@link Application} instance
	 * 
	 * @param shell
	 *            the shell on which to create the application
	 */
	@Inject
	public Application(Display display, Shell shell) {
		this.display = display;
		this.shell = shell;
	}

	/**
	 * Runs the executable application, launching the main window and entering
	 * the event loop until it is closed
	 */
	public void run() {
		Principal.inicializar(shell);

		while (!shell.isDisposed())
			while (!display.readAndDispatch())
				display.sleep();
	}

	/**
	 * Application entry point
	 * 
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String args[]) {
		Injector injector = Guice.createInjector(new ApplicationModule());
		Application application = injector.getInstance(Application.class);
		application.run();
	}

}
