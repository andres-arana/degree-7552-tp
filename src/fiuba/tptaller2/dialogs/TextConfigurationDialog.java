package fiuba.tptaller2.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import fiuba.tptaller2.components.Text;

public class TextConfigurationDialog extends ConfigurationDialog implements ActionListener{
	
	private static final long serialVersionUID = 6359577509426136520L;
	
	private static int PANEL_WIDTH = 400;
	
	private static String TEXT_LABEL = "Texto: ";
	
	private Text text;
	private boolean isNew;
	
	private JTextField textContentField;
	
	/**
	 * Constructor
	 */
	public TextConfigurationDialog(Text text, boolean isNew){
		this.text = text;
		this.isNew = isNew;
		this.setTitle((this.isNew ? NEW : EDIT) + text.getText());
		this.setResizable(false);
		this.setLocationRelativeTo(null); // places the dialog in the middle of the screen
		this.setModal(true);
		this.setAlwaysOnTop(true); // disables outside clicking until a button is pressed
		this.add(getPropertiesPanel(text));
		this.pack(); // resizes the panel according to the components
		this.setVisible(true);
	}

	private JPanel getPropertiesPanel(Text text){
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel,BoxLayout.Y_AXIS));
		
		JSplitPane splitPane = getSplitPane();
		splitPane.setLeftComponent(getHeaderPanel(text.getText()));
		splitPane.setRightComponent(getInferiorPanel());
		
		propertiesPanel.add(splitPane);
		
		return propertiesPanel;
	}
	
	/**
	 * Returns the header panel
	 * @return
	 */
	private JPanel getHeaderPanel(String buttonLabel){
		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(255,255,255));
		topPanel.setVisible(true);
		topPanel.setSize(PANEL_WIDTH, 70);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
		
		JLabel mainLabel = new JLabel(buttonLabel);
		mainLabel.setFont(new Font("Arial", Font.BOLD, 16));
		
		JLabel secondaryLabel = new JLabel((this.isNew ? NEW : EDIT) + buttonLabel);
		
		topPanel.add(Box.createRigidArea(new Dimension(5,5)));
		topPanel.add(mainLabel);
		topPanel.add(Box.createRigidArea(new Dimension(0,5)));
		topPanel.add(secondaryLabel);
		
		return topPanel;
	}
	
	/**
	 * Returns the second panel, the one below
	 * @return
	 */
	private JPanel getInferiorPanel(){
		JPanel inferiorPanel = new JPanel();
		inferiorPanel.setBackground(new Color(192,192,192));
		
		inferiorPanel.setLayout(new BoxLayout(inferiorPanel,BoxLayout.PAGE_AXIS));
		inferiorPanel.add(getbuttonNameTextFieldPanel());
		inferiorPanel.add(getButtonsPanel());
		return inferiorPanel;
	}
	
	/**
	 * Returns an "OK" button
	 * @return a button
	 */
	private JButton getOKButton(){
		JButton okButton = new JButton(OK_BUTTON);
		okButton.addActionListener(this); // Add the action listener
		return okButton;
	}
	
	/**
	 * Returns an "OK" button
	 * @return a button
	 */
	private JButton getCancelButton(){
		JButton okButton = new JButton(CANCEL_BUTTON);
		okButton.addActionListener(this); // Add the action listener
		return okButton;
	}
	
	/**
	 * Returns a split pane
	 * @return
	 */
	private JSplitPane getSplitPane(){
		// Create split pane
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(55);
		splitPane.setDividerSize(1);
		splitPane.setEnabled(false);
		return splitPane;
	}
	
	/**
	 * Returns a panel that contains the "OK" and "Cancel" buttons
	 * @return a panel with buttons
	 */
	private JPanel getButtonsPanel(){
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(new Color(192,192,192));
		buttonsPanel.add(getOKButton());
		buttonsPanel.add(getCancelButton());
		buttonsPanel.setSize(PANEL_WIDTH,70);
		return buttonsPanel;
	}
	
	/**
	 * Returns a panel that contains an input field for the button name
	 * It completes the field in case it is an edition
	 * @return a panel with the a label + field to edit the button name
	 */
	private JPanel getbuttonNameTextFieldPanel(){
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192,192,192));
		
		panel.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		textContentField = new JTextField();
		textContentField.setPreferredSize(new Dimension(200,27));
		
		if (!this.isNew){ 
			textContentField.setText(this.text.getText());
		}
		
		panel.add(new JLabel(TEXT_LABEL));
		panel.add(textContentField);
		return panel;
	}
	
	/**
	 * Sets the text component properties
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setPressedButton(((JButton)e.getSource()).getText());
		
		if (OK_BUTTON.equals(this.getPressedButton())){
			// Update button properties
			if (!textContentField.getText().isEmpty()){
				this.text.setText(textContentField.getText());
				
				// Resize if necessary 
				this.text.setBounds(this.text.getX(), this.text.getY(), this.text.getText().length()*7 + 40, 30); // 7 pixels by character, 40 pixels of margin
			}
		}
		this.dispose(); // clear dialog
	}
	
}