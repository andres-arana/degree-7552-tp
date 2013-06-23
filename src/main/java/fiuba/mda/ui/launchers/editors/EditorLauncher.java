package fiuba.mda.ui.launchers.editors;

import fiuba.mda.model.ProjectComponent;

/**
 * Generic controller which allows editing a given instance of a
 * {@link ProjectComponent}
 */
public interface EditorLauncher {
	/**
	 * Launches the process which allows editing the given component
	 * 
	 * @param component
	 *            the component to edit
	 */
	void launch(final ProjectComponent component);
}
