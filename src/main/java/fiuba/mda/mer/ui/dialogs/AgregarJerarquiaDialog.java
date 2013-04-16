package fiuba.mda.mer.ui.dialogs;

import java.util.Set;

import fiuba.mda.mer.control.JerarquiaControl;
import fiuba.mda.mer.modelo.Jerarquia;
import fiuba.mda.mer.ui.editores.Editor;
import fiuba.mda.mer.ui.editores.EditorFactory;

public class AgregarJerarquiaDialog extends AgregarComponenteDialog<Jerarquia> {

	public AgregarJerarquiaDialog() {
		super();
	}

	@Override
	protected Set<Jerarquia> loadComponentes() {
		// Obtener las entidades de los ancestros
		Set<Jerarquia> relaciones = this.principal.getProyecto()
				.getJerarquiasDisponibles();
		Set<Jerarquia> entidadesDiagrama = this.principal.getProyecto()
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
	protected String getNombreComponente() {
		return "Jerarquia";
	}

}
