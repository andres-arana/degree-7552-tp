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

import java.util.List;

/**
 * Figure which displays a state in a behavior diagram
 */
public class BehaviorStateFigure extends Figure implements MouseListener,
		MouseMotionListener {
	private Point moveStartedLocation;

    private CompartmentFigure nameCompartment;
    private CompartmentFigure typeCompartment;
    private CompartmentFigure elemntsCompartment;

	private final Representation<BehaviorState> state;



	public BehaviorStateFigure(final Representation<BehaviorState> state) {
		this.state = state;
        BehaviorState entity = state.getEntity();
        String name = entity.getName();
        String type = entity.getType();
        GraficInterfaceDiagram interfazGrafica = entity.getInterfazGrafica();
        List<String> iElements = interfazGrafica.getElementsStringList();

        addMouseListener(this);
        addMouseMotionListener(this);
        StackLayout manager = new StackLayout();
        setLayoutManager(manager);
        setBorder(new BehaviourStateFigureBorder());
        setOpaque(true);


        Color color = ColorConstants.black;
        if (type.equals(BehaviorState.FORM_ENTRADA) || type.equals(BehaviorState.FORM_SALIDA)){
            color = ColorConstants.white;
        }

        nameCompartment = new CompartmentFigure(color,true,false);
        if (iElements.isEmpty()){
            typeCompartment = new CompartmentFigure(color,false,true);
        } else {
            typeCompartment = new CompartmentFigure(color,false,false);
            elemntsCompartment = new CompartmentFigure(color,false,true);
        }


        addLabellToCompartment(nameCompartment,type,name);
        addLabellToCompartment(typeCompartment,type,type);
        for (String s : iElements){
            addLabellToCompartment(elemntsCompartment,type,s);
        }

        RoundedRectangle roundedRectangle = new RoundedRectangle();
        roundedRectangle.setSize(100, 200);
        if (type.equals(BehaviorState.FORM_ENTRADA)){
            roundedRectangle.setForegroundColor(ColorConstants.darkGreen);
            roundedRectangle.setBackgroundColor(ColorConstants.darkGreen);
        }
        if (type.equals(BehaviorState.FORM_SALIDA)){
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

    private void addLabellToCompartment(CompartmentFigure compartment, String type,String labelTxt) {
        Label label = new Label(labelTxt);
        label.setTextAlignment(SWT.CENTER);
        label.setLabelAlignment(SWT.WRAP);
        label.setForegroundColor(ColorConstants.black);
        if (type.equals(BehaviorState.FORM_ENTRADA)){
            label.setForegroundColor(ColorConstants.white);
        }
        if (type.equals(BehaviorState.FORM_SALIDA)){
            label.setForegroundColor(ColorConstants.white);
        }
        compartment.add(label);
    }

    @Override
	public void setParent(IFigure p) {
		super.setParent(p);
		if (p != null) {
			Position position = state.getPosition();
			Rectangle constraint = buildPositionalBound(position);
            constraint.setWidth(100);
            constraint.setHeight(200);
            p.getLayoutManager().setConstraint(this, constraint);
            //Dimension labelSize = label.getPreferredSize();
			//setPreferredSize(labelSize.width + 40, labelSize.height + 20);
           /* elipse.setPreferredSize(label.getText().length() * 2 + 80, label.getText().length()  + 20 );
		    elipse.setSize(label.getText().length() * 2 + 80, label.getText().length()  + 20);*/
        }
	}

	private Rectangle buildPositionalBound(final Position position) {
        return new Rectangle(position.getX(), position.getY(), -1,-1);
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		if (moveStartedLocation == null) {
			return;
		}
		Point moveEndedLocation = me.getLocation();
		if (moveEndedLocation == null) {
			return;
		}
		Dimension offset = moveEndedLocation.getDifference(moveStartedLocation);
		if (offset.width == 0 && offset.height == 0)
			return;

		moveStartedLocation = moveEndedLocation;
		state.getPosition().translate(offset.width, offset.height);

		UpdateManager updateMgr = this.getUpdateManager();
		LayoutManager layoutMgr = this.getParent().getLayoutManager();
		Rectangle bounds = this.getBounds();
		updateMgr.addDirtyRegion(this.getParent(), bounds);
		bounds = bounds.getCopy().translate(offset.width, offset.height);
		layoutMgr.setConstraint(this, bounds);
		this.translate(offset.width, offset.height);
		updateMgr.addDirtyRegion(this.getParent(), bounds);
		me.consume();
	}

	@Override
	public void mouseEntered(MouseEvent me) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent me) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseHover(MouseEvent me) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent me) {
		moveStartedLocation = me.getLocation();
		me.consume();
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseDoubleClicked(MouseEvent me) {

	}

    public Representation<BehaviorState> getState() {
        return state;
    }

    public class BehaviourStateFigureBorder extends AbstractBorder {
        public Insets getInsets(IFigure figure) {
            return new Insets(1,1,1,1);
        }
        public void paint(IFigure figure, Graphics graphics, Insets insets) {
            Rectangle paintRectangle = getPaintRectangle(figure, insets);
            /*paintRectangle.performScale(2);*/
            graphics.setLineWidth(2);
            graphics.setBackgroundColor(ColorConstants.darkGreen);
            graphics.setForegroundColor(ColorConstants.darkGreen);
            graphics.drawRoundRectangle(paintRectangle, 20, 50);
        }
    }

    public class MultiCompartmentFigure extends Figure {

        public MultiCompartmentFigure() {
            ToolbarLayout layout = new ToolbarLayout();
            layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
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
                if (isFirst){
                    graphics.drawLine(getPaintRectangle(figure, insets).getBottomLeft(),
                            tempRect.getBottomRight());
                }
                else if (isLast){
                    graphics.drawLine(getPaintRectangle(figure, insets).getTopLeft(),
                            tempRect.getTopRight());
                }
                else {
                    graphics.drawLine(getPaintRectangle(figure, insets).getTopLeft(),
                            tempRect.getTopRight());
                    graphics.drawLine(getPaintRectangle(figure, insets).getBottomLeft(),
                            tempRect.getBottomRight());
                }

            }
        }
    }
}


