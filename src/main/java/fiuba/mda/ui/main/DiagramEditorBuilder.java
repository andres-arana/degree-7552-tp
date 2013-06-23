package fiuba.mda.ui.main;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.google.inject.Singleton;

import fiuba.mda.ui.main.workspace.ControlBuilder;

/**
 * Builder which creates a new diagram editor in a given composite
 */
@Singleton
public class DiagramEditorBuilder implements ControlBuilder {
	/**
	 * Builds a new diagram editor in the given composite
	 * 
	 * @param parent
	 *            the parent of the new diagram editor
	 * @return 
	 */
	public Control buildInto(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		label.setText("Here be diagrams!");
		label.setBackground(Display.getCurrent()
				.getSystemColor(SWT.COLOR_WHITE));
		label.pack();
		return label;
	}

}
