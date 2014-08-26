package fiuba.mda.model;

import java.util.ArrayList;
import java.util.Collection;

import com.google.common.base.Optional;

/**
 * Represents an aspect of the software being modeled, such as its UI flow
 * diagrams, it's domain modeling diagrams or it's logic diagram diagrams. The
 * {@link ModelAspect} works as a grouping parent entity for the various other
 * components under the aspect.
 */
public class ModelAspect extends AbstractContainerProjectComponent {
	/**
	 * Creates a new @{link ModelAspect} instance
	 * 
	 * @param name
	 *            the name representing this model aspect
	 */
	public ModelAspect(String name) {
		super(name);
	}
	
	public void removeIfUnnecessary() {
		if (!hasChildren() && !isRoot()) {
			getParent().removeChild(this);
		}		
	}

	@Override
	public void removeChild(final ProjectComponent child) {
		super.removeChild(child);
		removeIfUnnecessary();
	}

	@Override
	public Optional<ModelAspect> locateAspect(final String name) {
		if (name.equals(getName())) {
			return Optional.of(this);
		} else {
			return Optional.absent();
		}
	}

	@Override
	public void accept(ProjectComponentVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public Collection<? extends String> getOwnProperties() {
		ArrayList<String> result = new ArrayList<String>();
		
		for (ProjectComponent c : getChildren()) {
			result.addAll(c.getOwnProperties());
		}
		
		return result;
	}
}
