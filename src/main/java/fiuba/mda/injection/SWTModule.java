package fiuba.mda.injection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import fiuba.mda.mer.ui.MessageBoxes;
import fiuba.mda.mer.ui.Principal;

/**
 * Injection module which configures SWT components
 */
public class SWTModule extends AbstractModule {
	@Override
	protected void configure() {

	}

	@Provides
	@Singleton
	Display provideDisplay() {
		return Display.getDefault();
	}

	@Provides
	@Singleton
	Shell provideShell(final Display display) {
		return new Shell(display, SWT.SHELL_TRIM);
	}

	@Provides
	@Singleton
	Principal providePrincipal(final Shell shell, final MessageBoxes boxes) {
		Principal.inicializar(shell, boxes);
		return Principal.getInstance();
	}
}
