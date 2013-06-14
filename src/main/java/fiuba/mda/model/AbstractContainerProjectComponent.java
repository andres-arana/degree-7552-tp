package fiuba.mda.model;

/**
 * Base abstract class useful for implementing {@link ProjectComponent} for
 * non-leaf components
 */
public abstract class AbstractContainerProjectComponent extends
		AbstractProjectComponent {

	/**
	 * Creates a new @{link AbstractContainerProjectComponent} instance
	 * 
	 * @param name
	 *            the name which represents this component
	 */
	public AbstractContainerProjectComponent(final String name) {
		super(name);
	}

	@Override
	public boolean isLeaf() {
		return false;
	}
}
