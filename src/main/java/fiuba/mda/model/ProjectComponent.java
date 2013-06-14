package fiuba.mda.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fiuba.mda.utilities.SimpleEvent;
import fiuba.mda.utilities.SimpleEvent.Observer;

/**
 * Base class for everything in the model of the software
 */
public abstract class ProjectComponent {
	private final SimpleEvent<ProjectComponent> hierarchyChangedEvent = new SimpleEvent<>(
			this);

	private String name;

	private ProjectComponent parent;

	private final List<ProjectComponent> children = new ArrayList<>();

	private Observer<ProjectComponent> onChildrenHierarchyChanged = new Observer<ProjectComponent>() {
		@Override
		public void notify(ProjectComponent observable) {
			hierarchyChangedEvent.raise();
		}
	};

	/**
	 * Creates a new {@link ProjectComponent} instance
	 * 
	 * @param name
	 *            the name which represents the project component
	 */
	public ProjectComponent(final String name) {
		this.name = name;
	}

	/**
	 * Obtains the name of the component, a short string identifying the
	 * component to the user
	 * 
	 * @return the name of the component
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the component, a short string identifying the component
	 * to the user
	 * 
	 * @param name
	 *            the new name of the component to set
	 */
	public void setName(final String name) {
		this.name = name;
		this.hierarchyChangedEvent.raise();
	}

	/**
	 * Obtains an unmodifiable copy of the list of children components
	 * 
	 * @return the list of children components
	 */
	public List<ProjectComponent> getChildren() {
		return Collections.unmodifiableList(children);
	}

	/**
	 * Obtains the parent component
	 * 
	 * @throws RuntimeException
	 *             if this is a root component. Check with
	 *             {@link ProjectComponent#isRoot()} first to ensure this
	 *             exception isn't thrown.
	 * @return the parent component
	 */
	public ProjectComponent getParent() {
		if (isRoot()) {
			throw new RuntimeException("This is a root component");
		}
		return parent;
	}

	/**
	 * Checks if this component has any children. Returns true if it has, false
	 * otherwise
	 * 
	 * @return true if this component has any children, false otherwise
	 */
	public boolean hasChildren() {
		return !children.isEmpty();
	}

	/**
	 * Checks if this component is a root component having no parent. Returns
	 * true if it is, false otherwise
	 * 
	 * @return true if this is the root component, false otherwise
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
	public void addComponent(final ProjectComponent component) {
		component.parent = this;
		component.hierarchyChangedEvent().observe(onChildrenHierarchyChanged);
		this.children.add(component);
		this.hierarchyChangedEvent.raise();
	}

	/**
	 * Obtains the closest package component which owns this component
	 * 
	 * @return the closest package component owning this one
	 */
	public abstract ModelPackage closestOwningPackage();

	/**
	 * An event raised when some components in the component hierarchy have
	 * changed
	 * 
	 * @return the event
	 */
	public SimpleEvent<ProjectComponent> hierarchyChangedEvent() {
		return hierarchyChangedEvent;
	}
}
