package sabri.tptaller2.dialogs;

import javax.swing.JComponent;

import sabri.tptaller2.components.Button;
import sabri.tptaller2.components.ComponentType;
import sabri.tptaller2.components.Text;

public class ConfigurationDialogFactory {
	
	private static ConfigurationDialogFactory instance;
	
	/**
	 * Singleton pattern
	 * @return an instance of the ConfigurationDialogFactory class
	 */
	public static ConfigurationDialogFactory getInstance(){
		if (instance == null){
			instance = new ConfigurationDialogFactory();
		}
		return instance;
	}

	private ConfigurationDialogFactory(){
	}
	
	/**
	 * Returns an instance of the needed dialog
	 * @param type the type of component whose dialog is going to be created
	 * @param component the component containing the properties to be displayed
	 * @param isNew a boolean to determine if the dialog is for creating a new component or not
	 * @return a customized configuration dialog
	 */
	public ConfigurationDialog getConfigurationDialog(String type, JComponent component, boolean isNew){
		if (ComponentType.BUTTON.equals(type))
			return (new ButtonConfigurationDialog((Button)component, isNew));
		else
			if (ComponentType.TEXT.equals(type))
				return (new TextConfigurationDialog((Text)component, isNew));
			else
				return (new TextConfigurationDialog((Text)component, isNew));
	}
	
}
