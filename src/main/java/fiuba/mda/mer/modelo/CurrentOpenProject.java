package fiuba.mda.mer.modelo;

import com.google.inject.Singleton;


/**
 * Represents the currently opened project
 */
@Singleton
public class CurrentOpenProject {
	/**
	 * The currently open project
	 */
	private Proyecto project;

	/**
	 * Checks if you have a current open project
	 */
	public boolean hasProject() {
		return project != null;
	}

	/**
	 * Obtains the currently open project
	 */
	public Proyecto get() {
		return project;
	}

	/**
	 * Obtains a query interface for the current open project which allows
	 * clients to query for non-trivial data in the current project. Check the
	 * {@link ProyectoProxy} interface for more details.
	 */
	public ProyectoProxy getForQueries() {
		return project;
	}

	/**
	 * Opens a new project, setting it as the currently open one
	 * 
	 * @param project
	 *            the project to set as the currently open one
	 */
	public void open(final Proyecto project) {
		this.project = project;
	}
}
