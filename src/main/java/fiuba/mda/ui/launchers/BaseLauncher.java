package fiuba.mda.ui.launchers;

import fiuba.mda.model.ProjectComponent;

/**
 * Base abstract typed class which allows implementing {@link Launcher}
 * services strongly typed to a given type
 * 
 * @param <T>
 *            the type of the accepted components
 */
public abstract class BaseLauncher<T extends ProjectComponent>
		implements Launcher {

	@SuppressWarnings("unchecked")
	@Override
	public void launch(ProjectComponent component) {
		doLaunch((T) component);
	}

	protected abstract void doLaunch(final T component);
}
