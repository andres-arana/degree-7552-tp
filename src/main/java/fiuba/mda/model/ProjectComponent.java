package fiuba.mda.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fiuba.mda.model.ObservableEvent.Observer;

/**
 * Base class for everything in the model of the software
 */
public abstract class ProjectComponent {
	/**
	 * An event raised when some components in the component hierarchy have
	 * changed
	 */
	private final ObservableEvent<ProjectComponent, Object> hierarchyChangedEvent = new ObservableEvent<>(
			this);

	/**
	 * The component name, a short string identifying the component to the user
	 */
	private String name;

	/**
	 * The parent component, or null if this component has no parent
	 */
	private ProjectComponent parent;

	/**
	 * The list of children components
	 */
	private final List<ProjectComponent> children = new ArrayList<>();

	/**
	 * Creates a new {@link ProjectComponent} instance
	 * 
	 * @param name
	 *            the name of the component to create, a short string
	 *            identifying the component to the user
	 */
	public ProjectComponent(final String name) {
		this.name = name;
	}

	/**
	 * Obtains the name of the component, a short string identifying the
	 * component to the user
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the component, a short string identifying the component
	 * to the user
	 */
	public void setName(String name) {
		this.name = name;
		this.hierarchyChangedEvent.raise(null);
	}

	/**
	 * Obtains an unmodifiable copy of the list of children components
	 */
	public List<ProjectComponent> getChildren() {
		return Collections.unmodifiableList(children);
	}

	/**
	 * Obtains the parent component, or null if this component has no parent
	 */
	public ProjectComponent getParent() {
		return parent;
	}

	/**
	 * Checks if this component has any children. Returns true if it has, false
	 * otherwise
	 */
	public boolean hasChildren() {
		return !children.isEmpty();
	}

	/**
	 * Checks if this component is a root component having no parent. Returns
	 * true if it is, false otherwise
	 */
	public boolean isRoot() {
		return parent == null;
	}

	/**
	 * Adds a new component as children of this component
	 * 
	 * @param component
	 *            the component to add as a children of this one
	 */
	public void addComponent(ProjectComponent component) {
		component.parent = this;
		component.getHierarchyChangedEvent().observe(
				new Observer<ProjectComponent, Object>() {
					@Override
					public void notify(ProjectComponent observable,
							Object eventData) {
						hierarchyChangedEvent.raise(null);
					}
				});
		this.children.add(component);
		this.hierarchyChangedEvent.raise(null);
	}

	/**
	 * Obtains the closest package component which owns this component
	 */
	public abstract ModelPackage closestOwningPackage();

	/**
	 * An event raised when some components in the component hierarchy have
	 * changed
	 */
	public ObservableEvent<ProjectComponent, Object> getHierarchyChangedEvent() {
		return hierarchyChangedEvent;
	}
}
