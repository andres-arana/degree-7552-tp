package fiuba.mda.mer.interfaz.swt.dialogs;

import java.util.Set;


import org.eclipse.swt.widgets.Shell;

import fiuba.mda.mer.control.JerarquiaControl;
import fiuba.mda.mer.interfaz.swt.editores.Editor;
import fiuba.mda.mer.interfaz.swt.editores.EditorFactory;
import fiuba.mda.mer.modelo.Jerarquia;


public class AgregarJerarquiaDialog extends AgregarComponenteDialog<Jerarquia> {

	/**
	 * @wbp.parser.constructor
	 */
	protected AgregarJerarquiaDialog(Shell shell) {
		super(shell);
	}

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
