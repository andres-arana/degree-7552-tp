package fiuba.mda;

import org.eclipse.swt.widgets.Display;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import fiuba.mda.injection.InjectorConfiguration;
import fiuba.mda.ui.main.MainWindow;
import org.eclipse.swt.widgets.Shell;

/**
 * Represents the executable application
 */
@Singleton
public class Application {
    private static MainWindow mainWindowStatic;
    private final MainWindow mainWindow;

	/**
	 * Creates a new {@link Application} instance
	 * 
	 * @param mainWindow
	 *            the main window to show when the application is run
	 */
	@Inject
	public Application(final MainWindow mainWindow) {
		this.mainWindow = mainWindow;
        mainWindowStatic = mainWindow;
	}



    /**
	 * Runs the executable application, launching the main window and entering
	 * the event loop until it is closed
	 */
	public void run() {
		mainWindow.setBlockOnOpen(true);
		mainWindow.open();
        Display.getCurrent().dispose();
	}

    public static final Shell getShell(){
        return mainWindowStatic.getShell();
    }

	/**
	 * Application entry point
	 * 
	 * @param args
	 *            The application command line arguments
	 */
	public static void main(String args[]) {
		Injector injector = InjectorConfiguration.bootstrapInjector();
        Application application = injector.getInstance(Application.class);
		application.run();
	}
}
