package legacy.mer.ui.dialogs;

import java.util.Set;

import legacy.mer.control.JerarquiaControl;
import legacy.mer.modelo.CurrentOpenProject;
import legacy.mer.modelo.Jerarquia;
import legacy.mer.ui.editores.Editor;
import legacy.mer.ui.editores.EditorFactory;

import org.eclipse.swt.widgets.Shell;

import com.google.inject.Inject;


/**
 * Dialog to add a hierarchy to a diagram
 */
public class AddHierarchyDialog extends AddComponentDialog<Jerarquia> {

	/**
	 * Creates a new {@link AddHierarchyDialog} instance
	 */
	@Inject
	public AddHierarchyDialog(final Shell shell,
			final SelectComponentController selectComponentController,
			final CurrentOpenProject currentProject) {
		super(shell, selectComponentController, currentProject);
	}

	@Override
	protected Set<Jerarquia> loadComponents() {
		// Obtener las entidades de los ancestros
		Set<Jerarquia> relaciones = queryCurrentProject()
				.getJerarquiasDisponibles();
		Set<Jerarquia> entidadesDiagrama = queryCurrentProject()
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
