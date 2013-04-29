package fiuba.mda.ui.main;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.google.inject.Singleton;

@Singleton
public class DiagramEditorBuilder {
	public void buildInto(Composite parent) {
		Composite result = new Composite(parent, SWT.NONE);

		result.setLayout(new FillLayout(SWT.VERTICAL));

		CTabFolder tabs = new CTabFolder(result, SWT.CLOSE);
		tabs.setSimple(false);
		tabs.setBorderVisible(true);

		CTabItem sampleTabs = new CTabItem(tabs, SWT.NONE);
		sampleTabs.setText("Sample");

		Label label = new Label(tabs, SWT.NONE);
		label.setText("Here be diagrams!");
		label.setBackground(Display.getCurrent()
				.getSystemColor(SWT.COLOR_WHITE));
		label.pack();

		sampleTabs.setControl(label);

		tabs.setSelection(sampleTabs);
	}

}
