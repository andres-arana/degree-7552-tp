package fiuba.mda.mer.ui.editores;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import fiuba.mda.mer.modelo.Entidad;
import fiuba.mda.mer.ui.Principal;
import fiuba.mda.mer.ui.dialogs.SelectComponentController;

public class EntidadTabla extends Tabla<Entidad> {
	public EntidadTabla(Composite parent) {
		super(parent);
	}

	@Override
	protected void initColumnas() {
		this.columnas.add(Editor.NOMBRE);
	}

	@Override
	protected void initEditorsCeldas(Table table) {
	}

	@Override
	protected String getTextoColumna(Entidad element, int columnIndex) {
		return element.getNombre();
	}

	@Override
	protected Object getValorCelda(Entidad element, String property) {
		return element.getNombre();
	}

	@Override
	protected void setValorCelda(Entidad element, String property, Object value) {
	}

	@Override
	protected Entidad nuevoElemento() {
		// No se debe poder agregar nuevas entidades desde esta tabla.
		return null;
	}

	@Override
	protected void abrirEditor(Entidad elemento) {
		// No hace nada porque no se debería poder editar la tabla por medio de
		// esta tabla.
	}

	@Override
	protected Entidad agregarElemento() {
		SelectComponentController controller = Principal.getInstance()
				.getSelectComponentController();

		return controller.launchSelection(Principal.getInstance().getProyecto()
				.getEntidadesDiagrama());
	}
}
