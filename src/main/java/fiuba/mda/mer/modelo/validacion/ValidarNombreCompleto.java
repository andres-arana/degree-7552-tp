package fiuba.mda.mer.modelo.validacion;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang3.StringUtils;

import fiuba.mda.mer.modelo.base.ComponenteNombre;

public class ValidarNombreCompleto implements Validacion {

	@Override
	public List<Observacion> validar(Validable componente) {
		List<Observacion> observaciones = new ArrayList<>();

		ComponenteNombre componenteNombre = (ComponenteNombre) componente;

		if (StringUtils.isEmpty(componenteNombre.getNombre()))
			observaciones.add(new Observacion("Nombre " + Observacion.NO_DEFINIDO));

		return observaciones;
	}
}
