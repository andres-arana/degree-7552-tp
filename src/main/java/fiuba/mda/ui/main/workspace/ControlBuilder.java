package fiuba.mda.ui.main.workspace;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public interface ControlBuilder {
	Control buildInto(final Composite parent);
}
