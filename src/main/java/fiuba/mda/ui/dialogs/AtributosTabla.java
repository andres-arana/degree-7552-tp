package fiuba.mda.ui.dialogs;

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import fiuba.mda.model.ModelAttribute;
import fiuba.mda.model.ModelAttribute.Kind;
import fiuba.mda.model.ModelEntity;

public class AtributosTabla extends Tabla<ModelAttribute> {
	private final ModelEntity entidad;

	public AtributosTabla(Composite parent, ModelEntity entidad) {
		super(parent);
		this.entidad = entidad;
	}

	@Override
	protected void initColumnas() {
		this.columnas.add("Nombre");
		this.columnas.add("Tipo");
		this.columnas.add("Cardinalidad Mín.");
		this.columnas.add("Cardinalidad Max.");
	}

	@Override
	protected void initEditorsCeldas(Table table) {
		this.editoresCeldas.add(new TextCellEditor(table));
		this.editoresCeldas.add(new ComboBoxCellEditor(table,
				ModelAttribute.KINDS, SWT.READ_ONLY));
		this.editoresCeldas.add(new TextCellEditor(table));
		this.editoresCeldas.add(new TextCellEditor(table));
	}

	@Override
	protected String getTextoColumna(ModelAttribute element, int columnIndex) {
		String nombreColumna = this.columnas.get(columnIndex);
		if (nombreColumna.equals("Tipo"))
			return element.getKind().name();
		else
			return (String) this.getValorCelda(element,
					this.columnas.get(columnIndex));
	}

	@Override
	protected Object getValorCelda(ModelAttribute element, String property) {
		switch (property) {
		case "Nombre":
			return element.getName();
		case "Tipo":
			return element.getKind().ordinal();
		case "Cardinalidad Mín.":
			return element.getMin();
		case "Cardinalidad Max.":
			return element.getMax();
		default:
			return null;
		}
	}

	@Override
	protected void setValorCelda(ModelAttribute element, String property,
			Object value) {
		switch (property) {
		case "Nombre":
			element.setName((String) value);
			break;
		case "Tipo":
			element.setKind(ModelAttribute.Kind.class.getEnumConstants()[(int) value]);
			break;
		case "Cardinalidad Mín.":
			element.setMin((String) value);
			break;
		case "Cardinalidad Max.":
			element.setMax((String) value);
			break;
		}

		refresh();
	}

	@Override
	protected ModelAttribute nuevoElemento() {
		ModelAttribute result = new ModelAttribute(entidad, "Atributo");
		return result;
	}

	@Override
	protected void abrirEditor(ModelAttribute elemento) {
		// Nothing to do here
	}
}
