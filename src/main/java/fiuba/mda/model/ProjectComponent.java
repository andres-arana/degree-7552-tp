package fiuba.mda.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fiuba.mda.model.ObservableEvent.Observer;

public abstract class ProjectComponent {
	/**
	 * An event raised when some components in the component hierarchy have
	 * changed
	 */
	private final ObservableEvent<ProjectComponent, Object> hierarchyChangedEvent = new ObservableEvent<>(
			this);

	private String name;
	
	private ProjectComponent parent;
	
	private final List<ProjectComponent> children = new ArrayList<>();

	public ProjectComponent(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.hierarchyChangedEvent.raise(null);
	}

	public List<ProjectComponent> getChildren() {
		return Collections.unmodifiableList(children);
	}

	public ProjectComponent getParent() {
		return parent;
	}

	public boolean hasChildren() {
		return !children.isEmpty();
	}

	public boolean isRoot() {
		return parent == null;
	}

	public void addComponent(ProjectComponent component) {
		component.parent = this;
		component.getHierarchyChangedEvent().observe(new Observer<ProjectComponent, Object>() {
			@Override
			public void notify(ProjectComponent observable, Object eventData) {
				hierarchyChangedEvent.raise(null);
			}
		});
		this.children.add(component);
		this.hierarchyChangedEvent.raise(null);
	}

	public abstract ModelPackage closestOwningPackage();

	/**
	 * An event raised when some components in the component hierarchy have
	 * changed
	 */
	public ObservableEvent<ProjectComponent, Object> getHierarchyChangedEvent() {
		return hierarchyChangedEvent;
	}
}
