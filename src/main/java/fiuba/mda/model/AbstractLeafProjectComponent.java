package fiuba.mda.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Optional;

/**
 * Base abstract class useful for implementing {@link ProjectComponent} for leaf
 * components
 */
public abstract class AbstractLeafProjectComponent extends
		AbstractProjectComponent {

	/**
	 * Creates a new @{link AbstractLeafProjectComponent} instance
	 * 
	 * @param name
	 *            the name which represents this component
	 */
	public AbstractLeafProjectComponent(final String name) {
		super(name);
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public List<ProjectComponent> getChildren() {
		return new ArrayList<>();
	}

	@Override
	public Optional<ProjectComponent> findChildrenNamed(final String name) {
		return Optional.absent();
	}

	@Override
	public boolean hasChildren() {
		return false;
	}

	@Override
	public void addChild(final ProjectComponent component) {
		throw new RuntimeException("Unable to add children to a leaf component");
	}

	@Override
	public void removeChild(final ProjectComponent child) {
		throw new RuntimeException(
				"Unable to remove chidlren from a leaf component");
	}
	@Override
	public String[] getAccessibleProperties() {
		return getParent().getAccessibleProperties();
	}
	
	@Override
	public Collection<? extends String> getOwnProperties() {
		return new ArrayList<>();
	}
}
