package legacy.mer.control;

import java.util.ArrayList;
import java.util.Collection;

import legacy.mer.modelo.Proyecto;
import legacy.mer.ui.figuras.Figura;
import legacy.mrel.modelo.DiagramaLogico;
import legacy.mrel.modelo.Tabla;


import org.eclipse.draw2d.Figure;


public class DiagramaLogicoControl extends DiagramaLogico implements Control<DiagramaLogico>{

	public DiagramaLogicoControl(Proyecto proyecto) {
		super(proyecto);
	}
	
	public DiagramaLogicoControl(DiagramaLogico dia) {
		super(dia);
	}
	
	@Override
	public Figura<DiagramaLogico> getFigura(String idDiagrama) {
		return null;
	}

	@Override
	public void dibujar(Figure contenedor, String idDiagrama) {
		idDiagrama = idDiagrama != null ? idDiagrama : this.id;

		this.dibujar(contenedor, idDiagrama, this.tablas);
		
		this.dibujarRelaciones(contenedor, idDiagrama, this.tablas);
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList<Figura> getListaObjetosLogicos() {
		ArrayList<Figura> objetosLogicos= new ArrayList<Figura>();
		
		for(Tabla tabla: tablas){
			objetosLogicos.add(((TablaControl)tabla).getFigura(id));
		}
		return objetosLogicos;
	}
	
	private void dibujar(Figure contenedor, String id, Collection<?> componentes) {
		for (Object componente : componentes)
			((Control<?>) componente).dibujar(contenedor, id);
	}
	
	private void dibujarRelaciones(Figure contenedor, String id, ArrayList<Tabla> tablas) {
		for (Tabla componente : tablas)
			((TablaControl) componente).dibujarRelaciones(contenedor, id, this);
	}
	
	public void dibujar(Figure contenedor) {
		this.dibujar(contenedor, this.id);
	}
	
	@Override
	public String getNombreIcono() {
		return "logicdiagram.png";
	}

}
