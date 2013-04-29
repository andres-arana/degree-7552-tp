package legacy.mer.ui.editores;

import legacy.mer.modelo.Atributo;
import legacy.mer.modelo.Diagrama;
import legacy.mer.modelo.Entidad;
import legacy.mer.modelo.Jerarquia;
import legacy.mer.modelo.Relacion;
import legacy.mer.modelo.base.Componente;

public class EditorFactory {

	public static Editor<?> getEditor(Componente componente) {
		if (Diagrama.class.isInstance(componente))
			return new DiagramaEditor((Diagrama) componente);

		if (Entidad.class.isInstance(componente))
			return new EntidadEditor((Entidad) componente);

		if (Relacion.class.isInstance(componente))
			return new RelacionEditor((Relacion) componente);
		
		if (Atributo.class.isInstance(componente))
			return new AtributoEditor((Atributo) componente);

		if (Jerarquia.class.isInstance(componente))
			return new JerarquiaEditor((Jerarquia) componente);
		

		throw new RuntimeException("No existe un editor para el componente de tipo " + componente.getClass().getName());
	}
}
