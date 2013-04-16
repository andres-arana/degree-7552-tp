package fiuba.mda.injection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import fiuba.mda.mer.modelo.CurrentOpenProject;
import fiuba.mda.mer.ui.MessageBoxes;
import fiuba.mda.mer.ui.Principal;
import fiuba.mda.mer.ui.dialogs.AddEntityController;
import fiuba.mda.mer.ui.dialogs.AddHierarchyController;
import fiuba.mda.mer.ui.dialogs.AddRelationController;
import fiuba.mda.mer.ui.dialogs.SelectComponentController;

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
	Principal providePrincipal(Shell shell, MessageBoxes messageBoxes,
			CurrentOpenProject currentProject,
			final AddEntityController addEntityController,
			final AddHierarchyController addHierarchyController,
			final AddRelationController addRelationController,
			final SelectComponentController selectComponentController) {
		Principal.inicializar(shell, messageBoxes, currentProject,
				addEntityController, addHierarchyController,
				addRelationController, selectComponentController);
		return Principal.getInstance();
	}
}
