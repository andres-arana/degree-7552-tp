package fiuba.mda.ui.actions;

import org.eclipse.draw2d.FreeformLayer;

public interface ExportableToImage {
	
    public void setDiagramFigure(FreeformLayer diagram);
    
    public FreeformLayer getDiagramFigure();

}
