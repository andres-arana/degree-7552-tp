package fiuba.mda.ui.main.workspace;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Service in charge of building a complex {@link Control} inside a
 * {@link Composite}
 */
public interface ControlBuilder {
	/**
	 * Builds the {@link Control} inside a parent {@link Composite}
	 * 
	 * @param parent
	 *            the parent to build the control into
	 * @return the built {@link Control}
	 */
	Control buildInto(final Composite parent);
}
