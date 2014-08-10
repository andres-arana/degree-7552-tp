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
    private int heidth;
    private int width;
    private Position position;
	private Point moveStartedLocation;
	private IPositionable state;

	private Rectangle buildPositionalBound(final Position position) {
        return new Rectangle(position.getX(), position.getY(), -1,-1);
	}

	public SelectableElementFigure(final IPositionable s) {
        StackLayout manager = new StackLayout();
        setLayoutManager(manager);

        addMouseListener(this);
        addMouseMotionListener(this);

        this.state = s;
	}

	public void setHeight(int h) {
		heidth = h;
	}

	public void setWidth(int w) {
		width = w;
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
		moveStartedLocation = me.getLocation();
		me.consume();
	}

	@Override
	public void mouseReleased(MouseEvent me) {
	}

	@Override
	public void mouseDoubleClicked(MouseEvent me) {

	}


}


