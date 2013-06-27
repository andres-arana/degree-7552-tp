package fiuba.mda.model;

import java.util.List;

import com.google.common.base.Optional;

import fiuba.mda.utilities.SimpleEvent;

/**
 * Interface which represents an object in the project tree, such as an entity,
 * a relation, a package or a diagram
 */
public interface ProjectComponent {
	/**
	 * Obtains the name of the component, a short string identifying the
	 * component to the user
	 * 
	 * @return the name of the component
	 */
	String getName();

	/**
	 * Sets the name of the component, a short string identifying the component
	 * to the user
	 * 
	 * @param name
	 *            the new name of the component to set
	 */
	void setName(final String name);

	/**
	 * Obtains the full, package-qualified name of the project component
	 * 
	 * @return a textual representation of the full path of the component
	 */
	String getQualifiedName();

	/**
	 * Obtains an unmodifiable copy of the list of children components
	 * 
	 * @return the list of children components
	 */
	List<ProjectComponent> getChildren();

	/**
	 * Checks if this component has any children. Returns true if it has, false
	 * otherwise
	 * 
	 * @return true if this component has any children, false otherwise
	 */
	boolean hasChildren();

	/**
	 * Checks if this component is a leaf component which can't contain any
	 * children on their own
	 * 
	 * @return true if this component is a leaf component, false otherwise
	 */
	boolean isLeaf();

	/**
	 * Adds a new component as children of this component
	 * 
	 * @param component
	 *            the component to add as a children of this one
	 * @throws RuntimeException
	 *             if this is a leaf component, you can ensure it isn't by
	 *             checking {@link ProjectComponent#isLeaf()}
	 */
	void addChildren(final ProjectComponent component);

    void removeChildren(final ProjectComponent component);

    void removeChildrens();

    void deleteChildrenFromList(ProjectComponent o);

	/**
	 * Obtains the parent component
	 * 
	 * @throws RuntimeException
	 *             if this is a root component. Check with
	 *             {@link ProjectComponent#isRoot()} first to ensure this
	 *             exception isn't thrown.
	 * @return the parent component
	 */
	ProjectComponent getParent();

	/**
	 * Sets the parent of this component
	 * 
	 * @param parent
	 *            the new parent of this component
	 */
	void setParent(final ProjectComponent parent);

	/**
	 * Checks if this component is a root component having no parent. Returns
	 * true if it is, false otherwise
	 * 
	 * @return true if this is the root component, false otherwise
	 */
	boolean isRoot();

	/**
	 * Obtains the closest package component which owns this component
	 * 
	 * @return the closest package component owning this one.
	 */
	ModelPackage locateOwningPackage();

	/**
	 * Attempts to locate a named {@link ModelAspect} instance, returning it if
	 * this component is the {@link ModelAspect} instance in question.
	 * 
	 * @param name
	 *            the name of the aspect to locate
	 * @return an {@link Optional} instance, absent unless this component is the
	 *         named {@link ModelAspect} instance
	 */
	Optional<ModelAspect> locateAspect(final String name);

	/**
	 * An event raised when some components in the component hierarchy have
	 * changed
	 * 
	 * @return the event
	 */
	SimpleEvent<ProjectComponent> hierarchyChangedEvent();

	/**
	 * Visitor implementation method for {@link ProjectComponentVisitor}
	 * instances
	 * 
	 * @param visitor
	 *            the visitor to accept
	 */
	void accept(ProjectComponentVisitor visitor);

    void accept(ProjectComponentVisitor visitor,boolean isEditing);
}
