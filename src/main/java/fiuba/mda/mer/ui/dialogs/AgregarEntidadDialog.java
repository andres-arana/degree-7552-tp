package fiuba.mda.mer.ui.dialogs;

import java.util.Set;

import fiuba.mda.mer.control.EntidadControl;
import fiuba.mda.mer.modelo.Entidad;
import fiuba.mda.mer.ui.editores.Editor;
import fiuba.mda.mer.ui.editores.EditorFactory;

public class AgregarEntidadDialog extends AgregarComponenteDialog<Entidad> {

	public AgregarEntidadDialog() {
		super();
	}

	@Override
	protected Set<Entidad> loadComponentes() {
		// Obtener las entidades de los ancestros
		Set<Entidad> entidades = this.principal.getProyecto()
				.getEntidadesDisponibles();
		Set<Entidad> entidadesDiagrama = this.principal.getProyecto()
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
	protected String getNombreComponente() {
		return "Entidad";
	}

}
