package fiuba.mda.ui.actions.validators;

import fiuba.mda.model.ProjectComponent;
import org.eclipse.jface.dialogs.IInputValidator;

/**
 * {@link IInputValidator} which validates an input which should be used to
 * enter a name
 */
public class NameAndExistenceValidator implements IInputValidator {

    private ProjectComponent parent;

    public ProjectComponent getParent() {
        return parent;
    }

    public void setParent(ProjectComponent parent) {
        this.parent = parent;
    }

    @Override
	public String isValid(String value) {
		if (value == null || value.trim().isEmpty()) {
			return "No puede ser vacio";
		}

		if (value.contains(" ")) {
			return "No puede contener espacios";
		}

        //Chekear por existencia de Componentes
        //Existe si teine el mismo tipo de componente y si tiene el mismo nombre.
        if (parent != null){
        for (ProjectComponent pc :parent.getChildren()){
            if (pc.getName().equals(value)){
                return "El nombre ya existe en el paquete, elija otro";
            }
        }
        }

        return null;
	}

}
