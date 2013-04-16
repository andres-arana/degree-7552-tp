package fiuba.mda.mer.ui.dialogs;

import java.util.Set;

import org.eclipse.swt.widgets.Shell;

import com.google.inject.Inject;

import fiuba.mda.mer.control.RelacionControl;
import fiuba.mda.mer.modelo.Relacion;
import fiuba.mda.mer.ui.CurrentOpenProject;
import fiuba.mda.mer.ui.editores.Editor;
import fiuba.mda.mer.ui.editores.EditorFactory;

/**
 * Dialog to add a relation to a diagram
 */
public class AddRelationDialog extends AddComponentDialog<Relacion> {

	/**
	 * Creates a new {@link AddRelationDialog} instance
	 * 
	 * See the
	 * {@link AddComponentDialog#AddComponentDialog(Shell, CurrentOpenProject)}
	 * parameters for additional information.
	 * 
	 */
	@Inject
	public AddRelationDialog(final Shell shell,
			final CurrentOpenProject currentProject) {
		super(shell, currentProject);
	}

	@Override
	protected Set<Relacion> loadComponents() {
		// Obtener las entidades de los ancestros
		Set<Relacion> relaciones = getCurrentProject()
				.getRelacionesDisponibles();
		Set<Relacion> entidadesDiagrama = getCurrentProject()
				.getRelacionesDiagrama();
		// Quitar las que ya tiene
		relaciones.removeAll(entidadesDiagrama);

		return relaciones;
	}

	@Override
	protected Editor<?> getEditor() {
		return EditorFactory.getEditor(new RelacionControl());
	}

	@Override
	protected String getComponentName() {
		return "Relacion";
	}
}
