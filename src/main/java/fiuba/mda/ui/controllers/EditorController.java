package fiuba.mda.ui.controllers;

import fiuba.mda.model.ProjectComponent;

/**
 * Generic controller which allows editing a given instance of a
 * {@link ProjectComponent}
 */
public interface EditorController {
	/**
	 * Launches the process which allows editing the given component
	 * 
	 * @param component
	 *            the component to edit
	 */
	void launch(ProjectComponent component);
}
