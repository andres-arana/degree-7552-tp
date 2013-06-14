package sabri.tptaller2.dialogs;

import javax.swing.JDialog;

public class ConfigurationDialog extends JDialog {
	
	private static final long serialVersionUID = -9069507253047807047L;
	
	private String pressedButton;
	
	public static final String OK_BUTTON = "Aceptar";
	public static final String CANCEL_BUTTON = "Cancelar";
	
	static final String NEW = "Crear nuevo ";
	static final String EDIT = "Editar ";

	/**
	 * Returns the button that has been pressed
	 * @return the pressed button
	 */
	public String getPressedButton(){
		return pressedButton;
	}
	
	/**
	 * Sets the button that has been pressed
	 * @param pressedButton
	 */
	public void setPressedButton(String pressedButton){
		this.pressedButton = pressedButton;
	}
	
}
