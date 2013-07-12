package fiuba.mda.ui.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.UpdateManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import fiuba.mda.model.BehaviorField;
import fiuba.mda.model.Representation;
import fiuba.mda.model.Representation.Position;

public class BehaviorFieldFigure extends Figure implements MouseListener,MouseMotionListener{
	private Point moveStartedLocation;
	
	private Label fieldBox;
	
	private final Label label;
	private final Representation<BehaviorField> field;
	
	public BehaviorFieldFigure(final Representation<BehaviorField> field) {
		this.field = field;
		setLayoutManager(new StackLayout());
		fieldBox = new Label();
		fieldBox.setOpaque(true);
		fieldBox.setBorder(new LineBorder());
		fieldBox.setBackgroundColor(ColorConstants.white);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		add(fieldBox);
		label = new Label(field.getEntity().getName());
		label.setTextAlignment(SWT.CENTER);
		label.setLabelAlignment(SWT.WRAP);
		add(label);	
	}
	
	@Override
	public void setParent(IFigure p) {
		super.setParent(p);
		if (p != null) {
			Position position = field.getPosition();
			Rectangle constraint = buildPositionalBound(position);
			p.getLayoutManager().setConstraint(this, constraint);
			fieldBox.setText("    " + label.getText() + "    ");
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
		field.getPosition().translate(offset.width, offset.height);
		
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
		// TODO Auto-generated method stub
	}
	
	public Representation<BehaviorField> getField() {
		return field;
	}	
}
