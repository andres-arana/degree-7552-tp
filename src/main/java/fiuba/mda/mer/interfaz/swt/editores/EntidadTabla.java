package fiuba.mda.mer.interfaz.swt.editores;


import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import fiuba.mda.mer.interfaz.swt.Principal;
import fiuba.mda.mer.interfaz.swt.dialogs.SeleccionarComponenteDialog;
import fiuba.mda.mer.modelo.Entidad;

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
		SeleccionarComponenteDialog<Entidad> dialog = new SeleccionarComponenteDialog<Entidad>(
				Principal.getInstance().getProyecto().getEntidadesDiagrama());

		if (dialog.open() == Window.OK)
			return dialog.getComponente();
		else
			return null;
	}
}
