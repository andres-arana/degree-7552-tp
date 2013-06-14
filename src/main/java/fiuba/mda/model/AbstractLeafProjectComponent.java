package fiuba.mda.model;

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
}
