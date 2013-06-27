package fiuba.mda.model;

import com.google.common.base.Optional;

/**
 * Represents a package from the software being modeled
 */
public class ModelPackage extends AbstractContainerProjectComponent {
	/**
	 * Creates a new {@link ModelPackage} instance
	 * 
	 * @param name
	 *            the name which represents this package
	 */
	public ModelPackage(String name) {
		super(name);
	}

	@Override
	public ModelPackage locateOwningPackage() {
		return this;
	}
	
	@Override
	public void accept(ProjectComponentVisitor visitor) {
		visitor.visit(this);
	}

    @Override
    public void accept(ProjectComponentVisitor visitor, boolean isEditing) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
	 * Ensures that a particularly named {@link ModelAspect} instance exists
	 * under this package and returns it, creating it if it doesn't.
	 * 
	 * @param name
	 *            the name of the aspect
	 * @return the existing or newly created aspect
	 */
	public ModelAspect ensureAspect(final String name) {
		for (ProjectComponent component : getChildren()) {
			Optional<ModelAspect> aspect = component.locateAspect(name);
			if (aspect.isPresent()) {
				return aspect.get();
			}
		}
		ModelAspect aspect = new ModelAspect(name);
		addChildren(aspect);
		return aspect;
	}

	/**
	 * Qualifies the name of a child component, ensuring that the full package
	 * path to it is present
	 * 
	 * @param component
	 *            the component to qualify
	 * @return the qualified component name
	 */
	public String qualifiedNameFor(ProjectComponent component) {
		return getQualifiedName() + "." + component.getName();
	}
}
