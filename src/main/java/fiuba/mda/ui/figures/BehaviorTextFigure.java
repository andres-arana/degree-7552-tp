package fiuba.mda.ui.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.UpdateManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import fiuba.mda.model.BehaviorText;
import fiuba.mda.model.Representation;
import fiuba.mda.model.IPositionable.Position;

public class BehaviorTextFigure extends Figure {
	private Point moveStartedLocation;
	private final Label label;
	private final Representation<BehaviorText> text;
	private final GraficInterfaceDiagramFigure.Dialogs dialogs;
	
	public BehaviorTextFigure(final Representation<BehaviorText> text, final GraficInterfaceDiagramFigure.Dialogs dialogs) {
		this.text = text;
		this.dialogs = dialogs;
		setLayoutManager(new StackLayout());
		label = new Label(text.getEntity().getName());
		label.setTextAlignment(SWT.CENTER);
		label.setLabelAlignment(SWT.WRAP);
		add(label);	
	}
	
	@Override
	public void setParent(IFigure p) {
		super.setParent(p);
		if (p != null) {
			Position position = text.getPosition();
			Rectangle constraint = buildPositionalBound(position);
			p.getLayoutManager().setConstraint(this, constraint);
		}
	}
	
	private Rectangle buildPositionalBound(final Position position) {
		return new Rectangle(position.getX(), position.getY(), -1, -1);
	}
	
	public Representation<BehaviorText> getText() {
		return text;
	}	
}
