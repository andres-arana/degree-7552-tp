package fiuba.tptaller2.dialogs;
//package fiuba.tptaller2.menus;
//
//import java.awt.Color;
//import java.awt.Component;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.Font;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.Box;
//import javax.swing.BoxLayout;
//import javax.swing.JButton;
//import javax.swing.JDialog;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JSplitPane;
//import javax.swing.JTextField;
//
//public class PropertiesDialog extends JDialog implements ActionListener{
//	private static final long serialVersionUID = 1543417210050982078L;
//	
//	private static String OK_BUTTON = "Aceptar";
//	private static String CANCEL_BUTTON = "Cancelar";
//	private JTextField nameTextField;
//
//	/**
//	 * Constructor
//	 * @param component the type of the component
//	 */
//	public PropertiesDialog(String component){
//		this.setTitle("Nuevo " + component);
//		this.setVisible(true);
//		this.setSize(400, 500);
//		this.setResizable(false);
//		this.setLocationRelativeTo(null);
//		this.setModal(true);
//		this.setAlwaysOnTop(true); // disables outside clicking until a button is pressed
//		this.add(getPropertiesPanel(component));
//	}
//	
//	/**
//	 * Constructor
//	 * @param component the component object with all its properties
//	 * @param componentType the type of the component
//	 */
//	public PropertiesDialog(Component component, String componentType){
//		this.setTitle("Nuevo " + componentType);
//		this.setVisible(true);
//		this.setSize(400, 500);
//		this.setResizable(false);
//		this.setLocationRelativeTo(null);
//		this.setModal(true);
//		this.setAlwaysOnTop(true); // disables outside clicking until a button is pressed
//		this.add(getPropertiesPanel(componentType));
//	}
//	
//	private JPanel getPropertiesPanel(String component){
//		JPanel propertiesPanel = new JPanel();
//		propertiesPanel.setLayout(new BoxLayout(propertiesPanel,BoxLayout.Y_AXIS));
//		
//		JSplitPane splitPane = getSplitPane();
//		splitPane.setLeftComponent(getTopPanel(component));
//		splitPane.setRightComponent(getInferiorPanel());
//		
//		propertiesPanel.add(splitPane);
//		
//		return propertiesPanel;
//	}
//	
//	/**
//	 * Returns the top panel
//	 * @return
//	 */
//	private JPanel getTopPanel(String component){
//		JPanel topPanel = new JPanel();
//		topPanel.setBackground(new Color(255,255,255));
//		topPanel.setVisible(true);
//		topPanel.setSize(400, 70);
//		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
//		
//		JLabel mainLabel = new JLabel(component);
//		mainLabel.setFont(new Font("Arial", Font.BOLD, 16));
//		
//		JLabel secondaryLabel = new JLabel("Crear nuevo " + component);
//		
//		topPanel.add(Box.createRigidArea(new Dimension(5,5)));
//		topPanel.add(mainLabel);
//		topPanel.add(Box.createRigidArea(new Dimension(0,5)));
//		topPanel.add(secondaryLabel);
//		
//		return topPanel;
//	}
//	
//	/**
//	 * Returns the second panel, the one below
//	 * @return
//	 */
//	private JPanel getInferiorPanel(){
//		JPanel inferiorPanel = new JPanel();
//		inferiorPanel.setBackground(new Color(192,192,192));
//		
//		inferiorPanel.setLayout(new BoxLayout(inferiorPanel,BoxLayout.PAGE_AXIS));
//		inferiorPanel.add(getNameTextFieldPanel());
//		inferiorPanel.add(getButtonsPanel());
//		return inferiorPanel;
//	}
//	
//	/**
//	 * Returns an "OK" button
//	 * @return a button
//	 */
//	private JButton getOKButton(){
//		JButton okButton = new JButton(OK_BUTTON);
//		okButton.addActionListener(this); // Add the action listener
//		return okButton;
//	}
//	
//	/**
//	 * Returns an "OK" button
//	 * @return a button
//	 */
//	private JButton getCancelButton(){
//		JButton okButton = new JButton(CANCEL_BUTTON);
//		okButton.addActionListener(this); // Add the action listener
//		return okButton;
//	}
//	
//	/**
//	 * Returns a split pane
//	 * @return
//	 */
//	private JSplitPane getSplitPane(){
//		// Create split pane
//		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
//		splitPane.setDividerLocation(55);
//		splitPane.setDividerSize(1);
//		splitPane.setEnabled(false);
//		return splitPane;
//	}
//	
//	/**
//	 * Returns a panel that contains the "OK" and "Cancel" buttons
//	 * @return a panel with buttons
//	 */
//	private JPanel getButtonsPanel(){
//		JPanel buttonsPanel = new JPanel();
//		buttonsPanel.setBackground(new Color(192,192,192));
//		buttonsPanel.add(getOKButton());
//		buttonsPanel.add(getCancelButton());
//		return buttonsPanel;
//	}
//	
//	/**
//	 * Returns a panel that contains the name property
//	 * @return a panel with the name property
//	 */
//	private JPanel getNameTextFieldPanel(){
//		JPanel namePanel = new JPanel();
//		namePanel.setBackground(new Color(192,192,192));
//		
//		namePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
//		
//		nameTextField = new JTextField();
//		nameTextField.setPreferredSize(new Dimension(200,27));
//		
//		namePanel.add(new JLabel("Nombre: "));
//		namePanel.add(nameTextField);
//		return namePanel;
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		String buttonName = ((JButton)e.getSource()).getText();
//		System.out.println(buttonName);
//		
//		if (OK_BUTTON.equals(buttonName)){
//			System.out.println(nameTextField.getText());
//		}
//		
//		this.dispose(); // clear dialog
//	}
//}
