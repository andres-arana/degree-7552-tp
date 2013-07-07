package fiuba.mda.ui.actions.validators;

import org.eclipse.jface.dialogs.IInputValidator;

import fiuba.mda.model.ProjectComponent;

/**
 * {@link IInputValidator} which validates an input which should be used to
 * enter a name
 */
public class NameValidatorFactory {
	private class Validator implements IInputValidator {
		private ProjectComponent parent;
		private ProjectComponent element;

		public Validator(ProjectComponent parent, ProjectComponent element) {
			this.parent = parent;
			this.element = element;
		}

		@Override
		public String isValid(String value) {
			if (value == null || value.trim().isEmpty()) {
				return "No puede ser vacio";
			}

			if (value.contains(" ")) {
				return "No puede contener espacios";
			}

			if (element != null) {
				// If we have a current element, it means that we are
				// updating an element to a new name. Validate if the
				// element's new name is in conflict
				if (element.isNewNameInConflict(parent, value)) {
					return "No puede ser repetido";
				}
			} else {
				// If we don't have a current element, it means that we are
				// creating a new element instance.
				if (parent != null) {
					// If have a parent, the new element is not a root
					// element, so we need to validate the new name with the
					// siblings
					if (parent.findChildrenNamed(value).isPresent()) {
						return "No puede ser repetido";
					}
				}
			}

			return null;
		}
	}

	public IInputValidator validatorForRenameInParent(
			final ProjectComponent parent, final ProjectComponent element) {
		return new Validator(parent, element);
	}

	public IInputValidator validatorForNewNameInParent(
			final ProjectComponent parent) {
		return new Validator(parent, null);
	}

	public IInputValidator validatorForRenameRoot(final ProjectComponent element) {
		return new Validator(null, element);
	}

	public IInputValidator validatorForNewNameRoot() {
		return new Validator(null, null);
	}
}
