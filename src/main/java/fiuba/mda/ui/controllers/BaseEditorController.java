package fiuba.mda.ui.controllers;

import fiuba.mda.model.ProjectComponent;

/**
 * Base abstract typed class which allows implementing {@link EditorController}
 * services strongly typed to a given type
 * 
 * @param <T>
 *            the type of the accepted components
 */
public abstract class BaseEditorController<T extends ProjectComponent>
		implements EditorController {

	@SuppressWarnings("unchecked")
	@Override
	public void launch(ProjectComponent component) {
		doLaunch((T) component);
	}

	protected abstract void doLaunch(final T component);
}
