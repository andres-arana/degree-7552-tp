package fiuba.mda.injection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * {@link Module} which configures objects related to jface and swt
 */
public class JFaceModule extends AbstractModule {
	@Override
	protected void configure() {
	}

	/**
	 * Provider method which creates a {@link Shell} instance when required to
	 * inject as a dependency
	 */
	@Provides
	@Singleton
	Shell provideShell() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display, SWT.SHELL_TRIM);
		return shell;
	}
}
