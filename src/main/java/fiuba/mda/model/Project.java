package fiuba.mda.model;

import fiuba.mda.model.ObservableEvent.Observer;

/**
 * Represents a single software project being modeled
 */
public class Project {
	/**
	 * An event raised when some components in the project hierarchy have
	 * changed
	 */
	private final ObservableEvent<Project, Object> hierarchyChangedEvent = new ObservableEvent<>(
			this);

	/**
	 * The project name
	 */
	private String name;

	/**
	 * The root model package
	 */
	private final ModelPackage rootPackage;

	/**
	 * Creates a new {@link Project} instance
	 * 
	 * @param name
	 *            the name of the project to create
	 */
	public Project(final String name) {
		this.name = name;
		this.rootPackage = new ModelPackage(name + " (Default package)");
		this.rootPackage.getHierarchyChangedEvent().observe(
				new Observer<ProjectComponent, Object>() {
					@Override
					public void notify(ProjectComponent observable,
							Object eventData) {
						hierarchyChangedEvent.raise(eventData);
					}
				});
	}

	/**
	 * Obtains the name of the project
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the project
	 */
	public void setName(String name) {
		this.name = name;
		hierarchyChangedEvent.raise(null);
	}

	/**
	 * Obtains the root package of the project
	 */
	public ModelPackage getRootPackage() {
		return rootPackage;
	}

	/**
	 * An event raised when some components in the project hierarchy have
	 * changed
	 */
	public ObservableEvent<Project, Object> getHierarchyChangedEvent() {
		return hierarchyChangedEvent;
	}
}
