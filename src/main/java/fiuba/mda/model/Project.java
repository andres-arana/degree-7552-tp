package fiuba.mda.model;

import fiuba.mda.utilities.SimpleEvent;
import fiuba.mda.utilities.SimpleEvent.Observer;

/**
 * Represents a single software project being modeled
 */
public class Project {
	private final SimpleEvent<Project> hierarchyChangedEvent = new SimpleEvent<>(
			this);

	private Observer<ProjectComponent> onHierarchyChanged = new Observer<ProjectComponent>() {
		@Override
		public void notify(ProjectComponent observable) {
			hierarchyChangedEvent.raise();
		}
	};

	private String name;

	private final ModelPackage rootPackage;

	/**
	 * Creates a new {@link Project} instance with a default root package
	 * 
	 * @param name
	 *            the name of the project
	 */
	public Project(final String name) {
		this.name = name;
		rootPackage = new ModelPackage(name);
		rootPackage.hierarchyChangedEvent().observe(onHierarchyChanged);
	}

	/**
	 * Obtains the name of the project
	 * 
	 * @return the name of the project
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the project
	 * 
	 * @param name
	 *            the new name to set
	 */
	public void setName(final String name) {
		this.name = name;
		hierarchyChangedEvent.raise();
	}

	/**
	 * Obtains the root package of the project
	 * 
	 * @return the root package of the project
	 */
	public ModelPackage getRootPackage() {
		return rootPackage;
	}

	/**
	 * An event raised when some components in the project hierarchy have
	 * changed
	 * 
	 * @return the event
	 */
	public SimpleEvent<Project> hierarchyChangedEvent() {
		return hierarchyChangedEvent;
	}
}
