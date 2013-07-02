package fiuba.mda.ui.main.workspace;

import fiuba.mda.Application;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

import com.google.common.base.Optional;

/**
 * Custom {@link Composite} which contains a number of tab folders, each with a
 * name and a displayed {@link Control}. This class wraps the {@link CTabFolder}
 * custom SWT control.
 */
public class TabsFolder extends Composite {
	private final CTabFolder tabs;

	/**
	 * Creates a new @{link TabsFolder} instance
	 * 
	 * @param parent
	 *            the parent {@link Composite} to build the control into
	 * @param style
	 *            the control style
	 */
	public TabsFolder(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.VERTICAL));
		tabs = new CTabFolder(this, SWT.NONE);
		tabs.setSimple(false);
		tabs.setBorderVisible(true);
        tabs.addCTabFolder2Listener(new CTabFolder2Listener() {
            @Override
            public void close(CTabFolderEvent cTabFolderEvent) {
                Shell shell = Application.getShell();
                if (shell.getModified()) {
                    MessageDialog dialog = new MessageDialog(shell, "Diagrama Modificado", null,
                            "Desea Guardar?", MessageDialog.CONFIRM, new String[]{"Aceptar",
                            "Cancelar"}, 0);

                    int result = dialog.open();

                    if (result == 0) {
                        //Hacer lo necesario para guardar!
                    }
                }
            }

            @Override
            public void minimize(CTabFolderEvent cTabFolderEvent) {
            }

            @Override
            public void maximize(CTabFolderEvent cTabFolderEvent) {
            }

            @Override
            public void restore(CTabFolderEvent cTabFolderEvent) {
            }

            @Override
            public void showList(CTabFolderEvent cTabFolderEvent) {
            }
        });
	}

	/**
	 * Adds a new tab folder for a given control
	 * 
	 * @param text
	 *            the name of the tab folder
	 * @param image
	 *            the image to display for the tab
	 * @param builder
	 *            the builder in charge of building the control of the tab
	 *            folder
	 */
    public void ensureTab(final String text, final Image image,
			final ControlBuilder builder, int type) {
		Optional<CTabItem> tab = tabExists(text);
		if (tab.isPresent()) {
			tabs.setSelection(tab.get());
		} else {
			CTabItem item = new CTabItem(tabs, type);
			item.setText(text);
			item.setImage(image);
			item.setControl(builder.buildInto(tabs));
            tabs.setSelection(item);
			tabs.layout();
		}
	}

    public void ensureTab(final String text, final Image image,
                          final ControlBuilder builder) {
        ensureTab(text,image,builder,SWT.CLOSE);
    }

    public void renameTab(final String oldText,final String newText) {
        Optional<CTabItem> tab = tabExists(oldText);
        if (tab.isPresent()) {
            tab.get().setText(newText);
        }
    }

	private Optional<CTabItem> tabExists(String text) {
		for (CTabItem item : tabs.getItems()) {
			if (item.getText().equals(text)) {
				return Optional.of(item);
			}
		}
		return Optional.absent();
	}
}
