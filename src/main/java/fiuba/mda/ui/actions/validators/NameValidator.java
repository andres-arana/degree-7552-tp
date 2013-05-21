package fiuba.mda.ui.actions.validators;

import org.eclipse.jface.dialogs.IInputValidator;

/**
 * {@link IInputValidator} which validates an input which should be used to
 * enter a name
 */
public class NameValidator implements IInputValidator {
	/**
	 * Implements {@link IInputValidator#isValid(String)} to validate a value as
	 * a name
	 */
	@Override
	public String isValid(String value) {
		if (value == null || value.trim().isEmpty()) {
			return "No puede ser vacio";
		}

		if (value.contains(" ")) {
			return "No puede contener espacios";
		}
		return null;
	}

}
