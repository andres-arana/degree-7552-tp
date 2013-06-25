package fiuba.mda.ui.figures;

import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;

import fiuba.mda.model.BehaviorState;

/**
 * Figure which displays a state in a behavior diagram
 */
public class BehaviorStateFigure extends Ellipse {
	private final Label label;

	/**
	 * Creates a new @{link BehaviorStateFigure} instance
	 * 
	 * @param state
	 *            the {@link BehaviorState} to display
	 */
	public BehaviorStateFigure(final BehaviorState state) {
		setLayoutManager(new StackLayout());
		setLocation(new Point(state.getX(), state.getY()));
		setAntialias(SWT.ON);
		setOutline(true);
		setLineWidth(2);

		label = new Label(state.getName());
		add(label);
	}

	/**
	 * Updates the size of this figure. Should be called only after setting the
	 * font or parent of this figure to allow proper calculations to be made
	 * based on the state label preferred size.
	 */
	public void updatePreferredSize() {
		Dimension labelSize = label.getPreferredSize();
		setSize(labelSize.width + 40, labelSize.height + 20);
	}

}
