package fiuba.mda.model;

import fiuba.mda.model.ObservableEvent.Observer;

public class Project {
	/**
	 * An event raised when some components in the project hierarchy have
	 * changed
	 */
	private final ObservableEvent<Project, Object> hierarchyChangedEvent = new ObservableEvent<>(
			this);

	private String name;

	private final ModelPackage rootPackage;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		hierarchyChangedEvent.raise(null);
	}

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
