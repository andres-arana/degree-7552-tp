package fiuba.mda.mer.ui.editores;

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import fiuba.mda.mer.control.AtributoControl;
import fiuba.mda.mer.modelo.Atributo;
import fiuba.mda.mer.modelo.Atributo.TipoAtributo;
import fiuba.mda.mer.modelo.Entidad;
import fiuba.mda.mer.ui.Principal;
import fiuba.mda.mer.ui.dialogs.SelectComponentController;

public class AtributosTabla extends Tabla<Atributo> {
	private Entidad entidad;

	public AtributosTabla(Composite parent) {
		super(parent);
	}

	public AtributosTabla(Composite parent, Entidad entidad) {
		super(parent);
		this.entidad = entidad;
	}

	@Override
	protected void initColumnas() {
		this.columnas.add(Editor.NOMBRE);
		this.columnas.add(Editor.TIPO);
		this.columnas.add(Editor.CARDINALIDAD_MIN);
		this.columnas.add(Editor.CARDINALIDAD_MAX);
	}

	@Override
	protected void initEditorsCeldas(Table table) {
		this.editoresCeldas.add(new TextCellEditor(table));
		this.editoresCeldas.add(new ComboBoxCellEditor(table,
				Editor.TiposAtributo, SWT.READ_ONLY));
		this.editoresCeldas.add(new TextCellEditor(table));
		this.editoresCeldas.add(new TextCellEditor(table));
	}

	@Override
	protected String getTextoColumna(Atributo element, int columnIndex) {
		String nombreColumna = this.columnas.get(columnIndex);
		if (nombreColumna.equals(EntidadEditor.TIPO))
			return element.getTipo().name();
		else
			return (String) this.getValorCelda(element,
					this.columnas.get(columnIndex));
	}

	@Override
	protected Object getValorCelda(Atributo element, String property) {
		switch (property) {
		case Editor.NOMBRE:
			return element.getNombre();
		case Editor.TIPO:
			return element.getTipo().ordinal();
		case Editor.CARDINALIDAD_MIN:
			return element.getCardinalidadMinima();
		case Editor.CARDINALIDAD_MAX:
			return element.getCardinalidadMaxima();
		default:
			return null;
		}
	}

	@Override
	protected void setValorCelda(Atributo element, String property, Object value) {
		switch (property) {
		case Editor.NOMBRE:
			element.setNombre((String) value);
			break;
		case Editor.TIPO:
			element.setTipo(TipoAtributo.class.getEnumConstants()[(int) value]);
			break;
		case Editor.CARDINALIDAD_MIN:
			element.setCardinalidadMinima((String) value);
			break;
		case Editor.CARDINALIDAD_MAX:
			element.setCardinalidadMaxima((String) value);
			break;
		}

		refresh();
	}

	@Override
	protected Atributo nuevoElemento() {
		AtributoControl atributo = new AtributoControl();
		atributo.setNombre("Nombre");
		atributo.setTipo(TipoAtributo.CARACTERIZACION);
		return atributo;
	}

	@Override
	protected void abrirEditor(Atributo elemento) {
		new AtributoEditor(elemento).open();
	}

	@Override
	protected Atributo agregarElemento() {
		SelectComponentController controller = Principal.getInstance()
				.getSelectComponentController();

		return controller.launchSelection(this.entidad.getAtributos());
	}
}
