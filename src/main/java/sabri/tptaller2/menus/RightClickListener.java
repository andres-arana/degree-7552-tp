package sabri.tptaller2.menus;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

/**
 * Detects a right click on a component and shows a contextual menu
 * @author sabrina
 *
 */
public class RightClickListener extends MouseAdapter {

	public void mousePressed(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e){
    	PropertiesSubMenu menu = new PropertiesSubMenu((JComponent)e.getSource());
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}
