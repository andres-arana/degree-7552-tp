package fiuba.mda.model;

import com.google.inject.Singleton;

/**
 * Represents the document model of the application
 */
@Singleton
public class DocumentModel {
	/**
	 * An event raised when a new project has been open
	 */
	private final ObservableEvent<DocumentModel, Project> projectOpenEvent = new ObservableEvent<>(
			this);

	/**
	 * An event raised when a project has been closed
	 */
	private final ObservableEvent<DocumentModel, Project> projectClosedEvent = new ObservableEvent<>(
			this);

	/**
	 * The current open project
	 */
	private Project project;

	/**
	 * Checks if you have a current open project
	 */
	public boolean hasProject() {
		return project != null;
	}

	/**
	 * Obtains the current open project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * Opens a new project, setting it as the current open one
	 */
	public void openProject(final Project project) {
		this.projectClosedEvent.raise(this.project);
		this.project = project;
		this.projectOpenEvent.raise(this.project);
	}

	/**
	 * An event raised when a new project has been open
	 */
	public ObservableEvent<DocumentModel, Project> getProjectOpenEvent() {
		return projectOpenEvent;
	}

	/**
	 * An event raised when a project has been closed
	 */
	public ObservableEvent<DocumentModel, Project> getProjectClosedEvent() {
		return projectClosedEvent;
	}

}
