package fiuba.mda.mer.ui.dialogs;

import java.util.Collection;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.mer.modelo.base.Componente;
import fiuba.mda.mer.ui.MessageBoxes;

/**
 * Controller in charge of selecting from a list of components a given one
 */
@Singleton
public class SelectComponentController {
	/**
	 * The shell used to display dialogs in
	 */
	private final Shell shell;

	/**
	 * The service used to display message boxes
	 */
	private final MessageBoxes messageBoxes;

	/**
	 * Creates a new {@link SelectComponentController}
	 */
	@Inject
	public SelectComponentController(final Shell shell,
			final MessageBoxes messageBoxes) {
		this.shell = shell;
		this.messageBoxes = messageBoxes;
	}

	/**
	 * Launchs a user interaction which allows the user to select a
	 * {@link Componente} from a collection of them. Returns the selected
	 * component, or null if the user canceled the operation.
	 * 
	 * @param components
	 *            the components to choose from
	 */
	public <T extends Componente> T launchSelection(
			final Collection<T> components) {
		SelectComponentDialog<T> dialog = new SelectComponentDialog<>(shell,
				messageBoxes, components);

		return dialog.open() == Window.OK ? dialog.getSelectedComponent()
				: null;
	}

}
