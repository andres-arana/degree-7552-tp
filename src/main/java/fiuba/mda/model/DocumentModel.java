package fiuba.mda.model;

import com.google.inject.Singleton;

import fiuba.mda.model.ObservableEvent.Observer;

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
	 * An event raised when a package has been activated
	 */
	private final ObservableEvent<DocumentModel, ModelPackage> packageActivatedEvent = new ObservableEvent<>(
			this);

	/**
	 * An event raised when anything in the component hierarchy of the project
	 * has changed
	 */
	private final ObservableEvent<DocumentModel, Project> projectHierarchyChangedEvent = new ObservableEvent<>(
			this);

	/**
	 * The current open project
	 */
	private Project project;

	/**
	 * The current active package
	 */
	private ModelPackage activePackage;

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
	 * Checks if there is an active package
	 */
	public boolean hasActivePackage() {
		return activePackage != null;
	}

	/**
	 * Obtains the current active package
	 */
	public ModelPackage getActivePackage() {
		return activePackage;
	}

	/**
	 * Deactivates the current active package
	 */
	public void clearActivePackage() {
		this.activePackage = null;
	}

	/**
	 * Sets a given package as the active one
	 */
	public void activatePackage(final ModelPackage p) {
		this.activePackage = p;
	}

	/**
	 * Opens a new project, setting it as the current open one
	 */
	public void openProject(final Project project) {
		this.project = project;
		this.project.getHierarchyChangedEvent().observe(
				new Observer<Project, Object>() {
					@Override
					public void notify(Project observable, Object eventData) {
						projectHierarchyChangedEvent.raise(project);
					}
				});
		this.projectOpenEvent.raise(this.project);
	}

	/**
	 * An event raised when a new project has been open
	 */
	public ObservableEvent<DocumentModel, Project> getProjectOpenEvent() {
		return projectOpenEvent;
	}

	/**
	 * An event raised when a package has been activated
	 */
	public ObservableEvent<DocumentModel, ModelPackage> getPackageActivatedEvent() {
		return packageActivatedEvent;
	}

	/**
	 * An event raised when anything in the component hierarchy of the project
	 * has changed
	 */
	public ObservableEvent<DocumentModel, Project> getProjectHierarchyChangedEvent() {
		return projectHierarchyChangedEvent;
	}
}
