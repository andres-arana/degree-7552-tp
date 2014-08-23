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
import fiuba.mda.model.IPositionable.Position;
import fiuba.mda.model.IPositionable;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * Figure which displays a state in a behavior diagram
 */
public class SelectableElementFigure extends Figure implements MouseListener,
		MouseMotionListener {

	public interface ISelectEvent {
		void select(SelectableElementFigure selectable);
		void multiSelect(SelectableElementFigure selectable);

		void drag(SelectableElementFigure selectable, Dimension offset);
	}

    private int height;
    private int width;
    private Position position;
	private Point moveStartedLocation;
	private IPositionable state;
	private boolean selected = false;
	private ISelectEvent selectEvent;
	private IFigure innerFigure;
	private MouseListener receptor;

	private Rectangle buildPositionalBound(final Position position) {
        return new Rectangle(position.getX(), position.getY(), -1, -1);
	}

	public SelectableElementFigure(final IPositionable s, final IFigure inner, ISelectEvent selectEvent) {
        StackLayout manager = new StackLayout();
        setLayoutManager(manager);

        addMouseListener(this);
        addMouseMotionListener(this);

        this.selectEvent = selectEvent; 

        add(inner);

        setBorder(new SelectionFigureBorder(ColorConstants.red));

        this.innerFigure = inner;
        this.state = s;
	}

	public void setMouseListenerReceptor(MouseListener receptor) {
		this.receptor = receptor;
	}

    @Override
	public void setParent(IFigure p) {
		super.setParent(p);
		if (p != null) {
			Position position = state.getPosition();
			Rectangle constraint = buildPositionalBound(position);
            constraint.setWidth(this.innerFigure.getPreferredSize().width);
            constraint.setHeight(this.innerFigure.getPreferredSize().height);
            p.getLayoutManager().setConstraint(this, constraint);
        }
	}

	public void setSelected(boolean s) {
		selected = s;
		UpdateManager updateMgr = this.getUpdateManager();
		Rectangle bounds = this.getBounds();
		updateMgr.addDirtyRegion(this.getParent(), bounds);
	}

	public void drag(Dimension offset) {
		if (selected) {
			state.getPosition().translate(offset.width, offset.height);

			UpdateManager updateMgr = this.getUpdateManager();
			LayoutManager layoutMgr = this.getParent().getLayoutManager();
			Rectangle bounds = this.getBounds();
			updateMgr.addDirtyRegion(this.getParent(), bounds);
			bounds = bounds.getCopy().translate(offset.width, offset.height);
			layoutMgr.setConstraint(this, bounds);
			this.translate(offset.width, offset.height);
			updateMgr.addDirtyRegion(this.getParent(), bounds);
		}
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
		selectEvent.drag(this, offset);
		me.consume();
	}

	@Override
	public void mouseEntered(MouseEvent me) {
	}

	@Override
	public void mouseExited(MouseEvent me) {
	}

	@Override
	public void mouseHover(MouseEvent me) {
	}

	@Override
	public void mouseMoved(MouseEvent me) {
	}

	@Override
	public void mousePressed(MouseEvent me) {

		if (me.button == 1) {
			selectEvent.select(this); 
		} else if (me.button == 3) {
			selectEvent.multiSelect(this);
		}

		moveStartedLocation = me.getLocation();
		me.consume();
	}

	@Override
	public void mouseReleased(MouseEvent me) {
	}

	@Override
	public void mouseDoubleClicked(MouseEvent me) {
		if (receptor != null) {
			receptor.mouseDoubleClicked(me);
		}
	}


	public class SelectionFigureBorder extends AbstractBorder {
        private Color color;
        public SelectionFigureBorder(Color color) {
            this.color = color;
        }

        public Insets getInsets(IFigure figure) {
            return new Insets(0,0,0,0);
        }
        public void paint(IFigure figure, Graphics graphics, Insets insets) {
            if (selected) {
	        	int thickness = 5;

	            graphics.setForegroundColor(color);
	            graphics.setBackgroundColor(color);
	            Rectangle paintRectangle = getPaintRectangle(figure, insets);

	            int x = paintRectangle.getTopLeft().x();
	            int y = paintRectangle.getTopLeft().y();
	            graphics.fillRectangle(new Rectangle(x, y, innerFigure.getPreferredSize().width, thickness));
	            graphics.fillRectangle(new Rectangle(x, y, thickness, innerFigure.getPreferredSize().height));
	            x = paintRectangle.getBottomLeft().x();
	            y = paintRectangle.getBottomLeft().y();
	            graphics.fillRectangle(new Rectangle(x, y-thickness, innerFigure.getPreferredSize().width, thickness));
	            x = paintRectangle.getTopRight().x();
	            y = paintRectangle.getTopRight().y();
	            graphics.fillRectangle(new Rectangle(x-thickness, y, thickness, innerFigure.getPreferredSize().height));
	        }
        }
    }

}


