package fiuba.mda.injection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import fiuba.mda.mer.ui.CurrentOpenProject;
import fiuba.mda.mer.ui.MessageBoxes;
import fiuba.mda.mer.ui.Principal;
import fiuba.mda.mer.ui.dialogs.AddEntityDialog;
import fiuba.mda.mer.ui.dialogs.AddHierarchyDialog;
import fiuba.mda.mer.ui.dialogs.AddRelationDialog;

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
	Principal providePrincipal(final Shell shell, final MessageBoxes boxes,
			final CurrentOpenProject currentProject,
			final Provider<AddEntityDialog> entityDialog,
			final Provider<AddHierarchyDialog> hierarchyDialog,
			final Provider<AddRelationDialog> relationDialog) {
		Principal.inicializar(shell, boxes, currentProject, entityDialog,
				hierarchyDialog, relationDialog);
		return Principal.getInstance();
	}
}
