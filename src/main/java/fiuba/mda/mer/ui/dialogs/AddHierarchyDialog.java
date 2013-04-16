package fiuba.mda.mer.ui.dialogs;

import java.util.Set;

import org.eclipse.swt.widgets.Shell;

import com.google.inject.Inject;

import fiuba.mda.mer.control.JerarquiaControl;
import fiuba.mda.mer.modelo.Jerarquia;
import fiuba.mda.mer.ui.CurrentOpenProject;
import fiuba.mda.mer.ui.editores.Editor;
import fiuba.mda.mer.ui.editores.EditorFactory;

/**
 * Dialog to add a hierarchy to a diagram
 */
public class AddHierarchyDialog extends AddComponentDialog<Jerarquia> {

	/**
	 * Creates a new {@link AddHierarchyDialog} instance
	 * 
	 * See the
	 * {@link AddComponentDialog#AddComponentDialog(Shell, CurrentOpenProject)}
	 * parameters for additional information.
	 * 
	 */
	@Inject
	public AddHierarchyDialog(final Shell shell,
			final CurrentOpenProject currentProject) {
		super(shell, currentProject);
	}

	@Override
	protected Set<Jerarquia> loadComponents() {
		// Obtener las entidades de los ancestros
		Set<Jerarquia> relaciones = getCurrentProject()
				.getJerarquiasDisponibles();
		Set<Jerarquia> entidadesDiagrama = getCurrentProject()
				.getJerarquiasDiagrama();
		// Quitar las que ya tiene
		relaciones.removeAll(entidadesDiagrama);

		return relaciones;
	}

	@Override
	protected Editor<?> getEditor() {
		return EditorFactory.getEditor(new JerarquiaControl());
	}

	@Override
	protected String getComponentName() {
		return "Jerarquia";
	}

}
