package fiuba.mda.mer.interfaz.swt.dialogs;

import java.util.Set;

import fiuba.mda.mer.control.EntidadControl;
import fiuba.mda.mer.interfaz.swt.editores.Editor;
import fiuba.mda.mer.interfaz.swt.editores.EditorFactory;
import fiuba.mda.mer.modelo.Entidad;

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
