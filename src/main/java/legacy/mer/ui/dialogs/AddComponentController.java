package legacy.mer.ui.dialogs;

import legacy.mer.modelo.base.Componente;

import org.eclipse.jface.window.Window;


/**
 * Controller in charge of managing the dialog flow when adding a new component
 */
public abstract class AddComponentController<T extends Componente> {
	/**
	 * Launches the process which allows a user to select a component to add.
	 * Returns the selected / created component, or null if the user cancels the
	 * process.
	 */
	public T launchAddComponent() {
		AddComponentDialog<T> dialog = getDialog();
		return dialog.open() == Window.OK ? dialog.getComponent() : null;
	}

	/**
	 * Create a new concrete dialog instance to be used in an add component flow
	 */
	protected abstract AddComponentDialog<T> getDialog();

}
