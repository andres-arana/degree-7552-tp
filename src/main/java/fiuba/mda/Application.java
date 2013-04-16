package fiuba.mda;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import fiuba.mda.injection.InjectorConfiguration;
import fiuba.mda.mer.interfaz.swt.EventLoop;
import fiuba.mda.mer.interfaz.swt.Principal;

/**
 * Represents the executable application
 */
@Singleton
public class Application {
	/**
	 * The event loop which will dispatch application events until the main
	 * window has been closed
	 */
	private final EventLoop eventLoop;

	/**
	 * The main window to show after the application has launched
	 */
	private final Principal mainWindow;

	/**
	 * Creates a new {@link Application} instance
	 * 
	 * @param eventLoop
	 *            the event loop which will dispatch application events until
	 *            the main window has been closed
	 * @param mainWindow
	 *            the main window to show after the application has launched
	 */
	@Inject
	public Application(final EventLoop eventLoop, final Principal mainWindow) {
		this.eventLoop = eventLoop;
		this.mainWindow = mainWindow;
	}

	/**
	 * Runs the executable application, launching the main window and entering
	 * the event loop until it is closed
	 */
	public void run() {
		this.mainWindow.open();
		this.eventLoop.doEventLoop();
	}

	/**
	 * Application entry point
	 * 
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String args[]) {
		Injector injector = InjectorConfiguration.bootstrapInjector();
		Application application = injector.getInstance(Application.class);
		application.run();
	}

}
