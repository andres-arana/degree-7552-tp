package fiuba.mda.ui.figures;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import fiuba.mda.model.BehaviorState;
import fiuba.mda.model.Representation;
import fiuba.mda.model.Representation.Position;

/**
 * Figure which displays a state in a behavior diagram
 */
public class BehaviorStateFigure extends Figure implements MouseListener,
		MouseMotionListener {
	private Point moveStartedLocation;

    private Ellipse elipse;

	private final Label label;
	private final Representation<BehaviorState> state;

	public BehaviorStateFigure(final Representation<BehaviorState> state) {
		this.state = state;
		setLayoutManager(new StackLayout());
        elipse = new Ellipse();
		elipse.setAntialias(SWT.ON);
		elipse.setOutline(true);
		elipse.setLineWidth(2);
		addMouseListener(this);
		addMouseMotionListener(this);
        add(elipse);
        label = new Label(state.getEntity().getName());
        label.setTextAlignment(SWT.CENTER);
        label.setLabelAlignment(SWT.WRAP);
		add(label);

	}

	@Override
	public void setParent(IFigure p) {
		super.setParent(p);
		if (p != null) {
			Position position = state.getPosition();
			Rectangle constraint = buildPositionalBound(position);
			p.getLayoutManager().setConstraint(this, constraint);
			//Dimension labelSize = label.getPreferredSize();
			//setPreferredSize(labelSize.width + 40, labelSize.height + 20);
            elipse.setPreferredSize(label.getText().length() * 2 + 80, label.getText().length()  + 20 );
		    elipse.setSize(label.getText().length() * 2 + 80, label.getText().length()  + 20);
        }
	}

	private Rectangle buildPositionalBound(final Position position) {
		return new Rectangle(position.getX(), position.getY(), -1, -1);
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

    public Representation<BehaviorState> getState() {
        return state;
    }
}
