package fiuba.tptaller2;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import fiuba.tptaller2.components.ComponentType;

public class OptionsList extends JList implements MouseListener{
	
	private static final long serialVersionUID = -9170901810773988847L;
	private static OptionsList instance;
	private static final String[] options = {ComponentType.TEXT,ComponentType.BUTTON,ComponentType.FORM,ComponentType.FIELD};
	private String componentType;
	
	/**
	 * Singleton pattern
	 * @return an instance of the OptionsList class
	 */
	public static OptionsList getInstance(){
		if (instance == null){
			instance = new OptionsList();
		}
		return instance;
	}

	private OptionsList(){
		super(options);
		this.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.setLayoutOrientation(JList.VERTICAL_WRAP);
		this.setVisibleRowCount(8);
		this.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Gets the selected item and highlights it
		int selectedItemIndex = this.locationToIndex(new Point(e.getX(),e.getY()));
		this.setSelectedIndex(selectedItemIndex);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Don't care about its behaviour
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Don't care about its behaviour
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Don't care about its behaviour
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		componentType = options[this.locationToIndex(new Point(e.getX(),e.getY()))]; // sets the type of component to draw
	}
	
	/**
	 * Returns the type of the selected component
	 * @return
	 */
	public String getComponentType(){
		return this.componentType;
	}
	
	/**
	 * Sets the type of the selected component
	 * @param type
	 * @return
	 */
	public void setComponentType(String type){
		this.componentType = type;
	}
	
	/**
	 * Deselects the component in the list
	 * @param type
	 */
	public void deselectComponent(){
		this.setComponentType(null);
		this.clearSelection();
	}

}
