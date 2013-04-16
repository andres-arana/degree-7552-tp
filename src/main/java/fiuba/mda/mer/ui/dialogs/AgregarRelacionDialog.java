package fiuba.mda.mer.ui.dialogs;

import java.util.Set;

import fiuba.mda.mer.control.RelacionControl;
import fiuba.mda.mer.modelo.Relacion;
import fiuba.mda.mer.ui.editores.Editor;
import fiuba.mda.mer.ui.editores.EditorFactory;

public class AgregarRelacionDialog extends AgregarComponenteDialog<Relacion> {

	public AgregarRelacionDialog() {
		super();
	}

	@Override
	protected Set<Relacion> loadComponentes() {
		// Obtener las entidades de los ancestros
		Set<Relacion> relaciones = this.principal.getProyecto()
				.getRelacionesDisponibles();
		Set<Relacion> entidadesDiagrama = this.principal.getProyecto()
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
	protected String getNombreComponente() {
		return "Relacion";
	}
}
