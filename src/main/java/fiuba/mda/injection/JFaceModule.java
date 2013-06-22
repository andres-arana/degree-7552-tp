package fiuba.mda.injection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.google.inject.AbstractModule;
import com.google.inject.Module;

/**
 * {@link Module} which configures objects related to jface and swt
 */
public class JFaceModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(Shell.class).toInstance(buildShell());
	}

	private Shell buildShell() {
		Display display = Display.getDefault();
		return new Shell(display, SWT.SHELL_TRIM);
	}
}
