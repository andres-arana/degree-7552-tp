package fiuba.mda.ui.main.workspace;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class TabsFolder extends Composite {
	private final CTabFolder tabs;

	public TabsFolder(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.VERTICAL));
		tabs = new CTabFolder(this, SWT.NONE);
		tabs.setSimple(false);
		tabs.setBorderVisible(true);
	}
	
	public void addTab(final String text, final ControlBuilder builder) {
		CTabItem item = new CTabItem(tabs, SWT.NONE);
		item.setText(text);
		item.setControl(builder.buildInto(tabs));
		tabs.setSelection(item);
	}
}
