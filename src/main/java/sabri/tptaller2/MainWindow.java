package sabri.tptaller2;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;

import sabri.tptaller2.Canvas;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 8067086131711949686L;
	private static MainWindow instance;
	private Canvas canvas;
	private static final int DIVIDER_POSITION = 250;
	
	public static MainWindow getInstance(){
		if (instance == null){
			instance = new MainWindow("Diagrama de comportamiento");
		}
		return instance;
	}
	
	/**
	 * Constructor
	 * @param windowText
	 */
	private MainWindow(String windowText){
		super(windowText);
		this.setVisible(true);
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.PAGE_AXIS));
		this.setJMenuBar(getTopMenuBar());
		this.add(getSplitPane());

		// Set after adding every component to the frame
		this.setExtendedState(Frame.MAXIMIZED_BOTH); // show the frame in fullscreen
	}
	
	/**
	 * Returns the menu bar
	 * @return
	 */
	private JMenuBar getTopMenuBar(){
		// Create menu bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setVisible(true);
		
		// Create menu 1 for the menu bar
		JMenu menuArchivo = new JMenu("Archivo");
		menuArchivo.setMnemonic(KeyEvent.VK_A);
		menuArchivo.setVisible(true);
		
		// Create options for menu 1
		JMenuItem menuItem = new JMenuItem("Guardar",KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.setVisible(true);
		menuArchivo.add(menuItem);

		// Add menu item to menu bar
		menuBar.add(menuArchivo);
		
		return menuBar;
	}
	
	/**
	 * Returns a Canvas component
	 * @return
	 */
	private Canvas getCanvas(){
		this.canvas = Canvas.getInstance();
		return canvas;
	}
	
	/**
	 * Returns a split pane
	 * @return
	 */
	private JSplitPane getSplitPane(){
		// Create split pane
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(DIVIDER_POSITION);
		splitPane.setLeftComponent(getOptionsList());
		splitPane.setRightComponent(getCanvas());
		return splitPane;
	}
	
	/**
	 * Returns a list with components to choose
	 * @return
	 */
	private JList getOptionsList(){
		return OptionsList.getInstance();
	}
	
	
	// ENTRY POINT :)
	public static void main(String[] args) {
		MainWindow.getInstance();
	}

}
