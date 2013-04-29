package fiuba.mda.injection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class JFaceModule extends AbstractModule {
	@Override
	protected void configure() {

	}

	@Provides
	@Singleton
	Shell provideShell() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display, SWT.SHELL_TRIM);
		return shell;
	}
}
