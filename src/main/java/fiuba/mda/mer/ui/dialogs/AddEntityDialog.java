package fiuba.mda.mer.ui.dialogs;

import java.util.Set;

import org.eclipse.swt.widgets.Shell;

import com.google.inject.Inject;

import fiuba.mda.mer.control.EntidadControl;
import fiuba.mda.mer.modelo.Entidad;
import fiuba.mda.mer.ui.CurrentOpenProject;
import fiuba.mda.mer.ui.editores.Editor;
import fiuba.mda.mer.ui.editores.EditorFactory;

/**
 * Dialog to add an entity to a diagram
 */
public class AddEntityDialog extends AddComponentDialog<Entidad> {

	/**
	 * Creates a new {@link AddEntityDialog} instance
	 * 
	 * See the
	 * {@link AddComponentDialog#AddComponentDialog(Shell, CurrentOpenProject)}
	 * parameters for additional information.
	 * 
	 */
	@Inject
	public AddEntityDialog(final Shell shell,
			final CurrentOpenProject currentProject) {
		super(shell, currentProject);
	}

	@Override
	protected Set<Entidad> loadComponents() {
		// Obtener las entidades de los ancestros
		Set<Entidad> entidades = getCurrentProject().getEntidadesDisponibles();
		Set<Entidad> entidadesDiagrama = getCurrentProject()
				.getEntidadesDiagrama();
		// Quitar las que ya tiene
		entidades.removeAll(entidadesDiagrama);

		return entidades;
	}

	@Override
	protected Editor<?> getEditor() {
		return EditorFactory.getEditor(new EntidadControl());
	}

	@Override
	protected String getComponentName() {
		return "Entidad";
	}

}
