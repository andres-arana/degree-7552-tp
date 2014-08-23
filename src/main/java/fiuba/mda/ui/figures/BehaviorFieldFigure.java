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
import fiuba.mda.model.IPositionable.Position;

public class BehaviorFieldFigure extends Figure implements MouseListener {
	private Point moveStartedLocation;
	
	private Label fieldBox;
	
	private final Label label;
	private final Representation<BehaviorField> field;
	private final GraficInterfaceDiagramFigure.Dialogs dialogs;	
	
	public BehaviorFieldFigure(final Representation<BehaviorField> field, final GraficInterfaceDiagramFigure.Dialogs dialogs) {
		this.field = field;
		this.dialogs = dialogs;
		setLayoutManager(new StackLayout());
		fieldBox = new Label();
		fieldBox.setOpaque(true);
		fieldBox.setBorder(new LineBorder());
		fieldBox.setBackgroundColor(ColorConstants.white);

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

	@Override
	public void mousePressed(MouseEvent me) {
	}
	
	@Override
	public void mouseReleased(MouseEvent me) {
	}
	
	@Override
	public void mouseDoubleClicked(MouseEvent me) {
		dialogs.showFieldDialog(field.getEntity());
	}
	
	private Rectangle buildPositionalBound(final Position position) {
		return new Rectangle(position.getX(), position.getY(), -1, -1);
	}
	
	public Representation<BehaviorField> getField() {
		return field;
	}	
}
