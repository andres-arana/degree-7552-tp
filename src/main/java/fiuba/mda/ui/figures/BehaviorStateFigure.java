package fiuba.mda.ui.figures;

import com.google.inject.Injector;
import fiuba.mda.injection.InjectorConfiguration;
import fiuba.mda.model.GraficInterfaceDiagram;
import fiuba.mda.ui.actions.EditBehaviorDiagramRelationAction;
import fiuba.mda.ui.actions.NewBehaviorDiagramStateAction;
import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import fiuba.mda.model.BehaviorState;
import fiuba.mda.model.Representation;
import fiuba.mda.model.Representation.Position;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * Figure which displays a state in a behavior diagram
 */
public class BehaviorStateFigure extends Figure {
    private int espaciadoPalabraTitulo = 20;

    private CompartmentFigure nameCompartment;
    private CompartmentFigure typeCompartment;
    private CompartmentFigure elemntsCompartment;

    private int heidth;

    private int width;

	private final Representation<BehaviorState> state;



	public BehaviorStateFigure(final Representation<BehaviorState> state) {
		this.state = state;
        BehaviorState entity = state.getEntity();
        String name = entity.getName();
        String type = entity.getType();
        GraficInterfaceDiagram interfazGrafica = entity.getInterfazGrafica();
        List<String> iElements = interfazGrafica.getElementsStringList();

        StackLayout manager = new StackLayout();
        setLayoutManager(manager);
        setOpaque(true);


        Color color = ColorConstants.black;
        boolean isEntradaOrSalida = entity.isFormEntrada() || entity.isFormSalida();
        if (isEntradaOrSalida){
            color = ColorConstants.white;
        }

        List<String> stateStrings = new ArrayList<>();
        List<String> titleStrings = new ArrayList<>();

        nameCompartment = new CompartmentFigure(color,true,false);
        String nombre = "Nombre";
        addTittleToCompartment(nameCompartment, isEntradaOrSalida, nombre);
        titleStrings.add(nombre);
        addTextToCompartment(nameCompartment, isEntradaOrSalida, name);
        stateStrings.add(name);

        if (iElements.isEmpty()){
            typeCompartment = new CompartmentFigure(color,false,true);
        } else {
            typeCompartment = new CompartmentFigure(color,false,false);
            elemntsCompartment = new CompartmentFigure(color,false,true);
            String componentes = "Componentes";
            addTittleToCompartment(elemntsCompartment, isEntradaOrSalida, componentes);
            titleStrings.add(componentes);
            for (String s : iElements){
                addTextToCompartment(elemntsCompartment, isEntradaOrSalida, s);
                stateStrings.add(s);
            }
        }


        String tipo = "Tipo";
        addTittleToCompartment(typeCompartment, isEntradaOrSalida, tipo);
        titleStrings.add(tipo);
        addTextToCompartment(typeCompartment, isEntradaOrSalida, type);
        stateStrings.add(type);


        calculateFigureHeidthAndWidth(titleStrings,stateStrings);

        RoundedRectangle roundedRectangle = new RoundedRectangle();
        roundedRectangle.setSize(width, heidth);
        if (entity.isFormEntrada()){
            roundedRectangle.setForegroundColor(ColorConstants.darkGreen);
            roundedRectangle.setBackgroundColor(ColorConstants.darkGreen);
        }
        if (entity.isFormSalida()){
            roundedRectangle.setForegroundColor(ColorConstants.black);
            roundedRectangle.setBackgroundColor(ColorConstants.black);
        }
        add(roundedRectangle);

        MultiCompartmentFigure multiCompartmentFigure = new MultiCompartmentFigure();

        multiCompartmentFigure.add(nameCompartment);
        multiCompartmentFigure.add(typeCompartment);
        if (!iElements.isEmpty()){
            multiCompartmentFigure.add(elemntsCompartment);
        }

        add(multiCompartmentFigure);

	}

    private void calculateFigureHeidthAndWidth(List<String> titleStrings ,List<String> stateStrings) {
        int cantidadDeLineas = stateStrings.size() + titleStrings.size();
        int heidthPorLinea = 20;
        heidth = cantidadDeLineas * heidthPorLinea;

        int maxCantidadCharDeStrings = 0;
        for (String s : stateStrings){
            if (maxCantidadCharDeStrings < s.length()) maxCantidadCharDeStrings = s.length();
        }
        int maxCantChar = maxCantidadCharDeStrings + espaciadoPalabraTitulo;
        width = maxCantChar *4+ maxCantChar /2;
    }


    private void addTittleToCompartment(CompartmentFigure compartment, boolean isEntradaOrSalida,String labelTxt) {
        String txt = labelTxt.substring(0,1).toUpperCase() + labelTxt.substring(1).toLowerCase() + ":";
        Color color = defineLabelColor(isEntradaOrSalida);
        addLabellToCompartment(compartment, txt, color, SWT.LEFT);
    }

    private void addTextToCompartment(CompartmentFigure compartment, Boolean isEntradaOrSalida,String labelTxt) {
        String txt = "";
        for (int i = 0 ; i< espaciadoPalabraTitulo; i++){
            txt = txt + " ";
        }
        txt = txt + labelTxt.toUpperCase();
        Color color = defineLabelColor(isEntradaOrSalida);
        addLabellToCompartment(compartment,txt,color,SWT.LEFT);
    }

    private void addLabellToCompartment(CompartmentFigure compartment, String labelTxt,Color color,int aligment) {
        Label label = new Label(labelTxt);
        label.setTextAlignment(aligment);
        label.setForegroundColor(color);
        label.setLabelAlignment(SWT.WRAP);
        compartment.add(label);
    }

    private Color defineLabelColor(boolean isEntradaOrSalida) {
        if (isEntradaOrSalida){
            return ColorConstants.white;
        }
        return ColorConstants.black;
    }

    @Override
	public void setParent(IFigure p) {
		super.setParent(p);
		if (p != null) {
			Position position = state.getPosition();
			Rectangle constraint = buildPositionalBound(position);
            constraint.setWidth(width);
            constraint.setHeight(heidth);
            p.getLayoutManager().setConstraint(this, constraint);
        }
	}

	private Rectangle buildPositionalBound(final Position position) {
        return new Rectangle(position.getX(), position.getY(), -1,-1);
	}

    public int getHeidth() {
        return heidth;
    }

    public int getWidth() {
        return width;
    }

    public Representation<BehaviorState> getState() {
        return state;
    }


    public class MultiCompartmentFigure extends Figure {

        public MultiCompartmentFigure() {
            ToolbarLayout layout = new ToolbarLayout();
            layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
            layout.setStretchMinorAxis(false);
            layout.setSpacing(2);
            setLayoutManager(layout);
        }


    }



    public class CompartmentFigure extends Figure {

        public CompartmentFigure(Color color,Boolean isFirst, Boolean isLast) {
            ToolbarLayout layout = new ToolbarLayout();
            layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
            layout.setStretchMinorAxis(false);
            layout.setSpacing(2);
            setLayoutManager(layout);
            setBorder(new CompartmentFigureBorder(color,isFirst,isLast));
        }

        public class CompartmentFigureBorder extends AbstractBorder {
            private boolean isFirst;
            private boolean isLast;
            private Color color;
            public CompartmentFigureBorder(Color color,Boolean first, Boolean last) {
                isFirst = first;
                isLast = last;
                this.color = color;
            }

            public Insets getInsets(IFigure figure) {
                if (isFirst){
                    return new Insets(0,0,1,0);
                }
                if (isLast){
                    return new Insets(1,0,0,0);
                }

                return new Insets(1,0,1,0);
            }
            public void paint(IFigure figure, Graphics graphics, Insets insets) {
                graphics.setForegroundColor(color);
                graphics.setBackgroundColor(color);
                Rectangle paintRectangle = getPaintRectangle(figure, insets);

                if (isFirst){
                    int x = paintRectangle.getBottomLeft().x();
                    int y = paintRectangle.getBottomLeft().y();
                    graphics.drawLine(x, y,x+width,y);
                }
                else if (isLast){
                    int x = paintRectangle.getTopLeft().x();
                    int y = paintRectangle.getTopLeft().y();
                    graphics.drawLine(x, y,x+width,y);

                }
                else {
                    int x = paintRectangle.getTopLeft().x();
                    int y = paintRectangle.getTopLeft().y();
                    graphics.drawLine(x, y,x+width,y);
                    x = paintRectangle.getBottomLeft().x();
                    y = paintRectangle.getBottomLeft().y();
                    graphics.drawLine(x, y,x+width,y);
                }
            }
        }
    }
}


