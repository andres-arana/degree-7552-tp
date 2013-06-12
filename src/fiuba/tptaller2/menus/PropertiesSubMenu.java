package fiuba.tptaller2.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import fiuba.tptaller2.dialogs.ConfigurationDialogFactory;

public class PropertiesSubMenu extends JPopupMenu {
	private static final long serialVersionUID = 2507289186400470365L;

	private JMenuItem menuItem;
	
	private String MENU_ITEM_LABEL = "Propiedades";
	private String TOOLTIP_TEXT = "Configurar las propiedades del componente";
	
	/**
	 * Default constructor
	 */
	public PropertiesSubMenu(JComponent componentToEdit){
		final JComponent component = componentToEdit;
		
		menuItem = new JMenuItem(MENU_ITEM_LABEL);
		menuItem.setToolTipText(TOOLTIP_TEXT);
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				ConfigurationDialogFactory factory = ConfigurationDialogFactory.getInstance();
				factory.getConfigurationDialog(component.getName(), component, false);
			}
			
		});
		
	    this.add(menuItem);
	    this.setVisible(true);
	}

}
