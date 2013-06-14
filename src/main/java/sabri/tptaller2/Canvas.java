package sabri.tptaller2;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JPanel;

import sabri.tptaller2.components.Button;
import sabri.tptaller2.components.ComponentType;
import sabri.tptaller2.components.Text;
import sabri.tptaller2.dialogs.ConfigurationDialog;
import sabri.tptaller2.dialogs.ConfigurationDialogFactory;

public class Canvas extends JPanel implements MouseListener{
	private static final long serialVersionUID = 4198954178286958028L;
	
	private static Canvas instance;

	private OptionsList optionsList;
	private HashMap<String,Component> componentsHash;
	
	public static Canvas getInstance(){
		if (instance == null){
			instance = new Canvas();
		}
		return instance;
	}
	
	private Canvas(){
		this.setVisible(true);
		this.setBackground(new Color(255,255,255));
		this.setLayout(null); // to place components in an exact (x,y) position
		this.addMouseListener(this);
		optionsList = OptionsList.getInstance();
		componentsHash = new HashMap<String,Component>();
	}
	
	/**
	 * Builds and returns a customized component
	 * @param component the component to build
	 */
	public JComponent createComponent (String component, int x, int y){
		JComponent visualComponent = null;
		String componentKey = String.valueOf(componentsHash.size()+1);
		
		if (ComponentType.BUTTON.equals(component)){
			visualComponent = new Button(component);
			visualComponent.setBounds(x, y, component.length()*7 + 40, 30); // 7 pixels by character, 40 pixels of margin
			((Button)visualComponent).setId(componentKey);
		}else{
			if (ComponentType.TEXT.equals(component)){
				visualComponent = new Text(component);
				visualComponent.setBounds(x, y, component.length()*7 + 40, 30); // 7 pixels by character, 40 pixels of margin
				((Text)visualComponent).setId(componentKey);
			}else{
				if (ComponentType.FORM.equals(component)){
					visualComponent = new Text(component); //TODO default for "Formulario" = Texto
					visualComponent.setBounds(x, y, 100, 30);
					((Text)visualComponent).setId(componentKey);
				}else{
					visualComponent = new Text(component); //TODO default for "Campo" = Texto.
					visualComponent.setBounds(x, y, 100, 30);
					((Text)visualComponent).setId(componentKey);
				}
			} 
		}
		
		componentsHash.put(componentKey, visualComponent);
		optionsList.deselectComponent();
		return visualComponent;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Draws the selected item into the canvas. If no item was selected, nothing is drawn
		String componentType = optionsList.getComponentType();
		if (componentType != null){
			JComponent newComponent = this.createComponent(componentType, e.getX(), e.getY());

			// Component configuration dialog
			ConfigurationDialogFactory factory = ConfigurationDialogFactory.getInstance(); 
			ConfigurationDialog dialog = factory.getConfigurationDialog(componentType, newComponent, true);
			String pressedButton = dialog.getPressedButton();
			
			if (ConfigurationDialog.OK_BUTTON.equals(pressedButton)){
				// If "OK", then show the component, if "Cancel" do not add the component to the canvas
				newComponent.setVisible(true);
				this.add(newComponent);
				this.repaint();
			}
		}
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
		// Don't care about its behaviour			
	}

}
