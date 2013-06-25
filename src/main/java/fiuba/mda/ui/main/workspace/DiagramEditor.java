package fiuba.mda.ui.main.workspace;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayeredPane;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.ToolBar;

/**
 * SWT control which represents a diagram editor, allowing a diagram figure to
 * be shown. It also exposes an API to associate actions to the diagram, and
 * shows those actions on a toolbar on the control.
 */
public class DiagramEditor extends Composite {
	private final ToolBarManager toolBarManager;
	private final FreeformLayer primaryLayer;

	/**
	 * Creates a new @{link DiagramEditor} instance
	 * 
	 * @param parent
	 *            the parent control on which this editor will be shown.
	 * @param style
	 *            the SWT style of the control.
	 */
	public DiagramEditor(Composite parent, int style) {
		super(parent, style);

		Layout layout = new GridLayout(1, false);
		setLayout(layout);

		ToolBar toolBar = buildToolBar();
		toolBarManager = buildToolBarManager(toolBar);

		buildSeparator();

		primaryLayer = buildPrimaryLayer(parent);
		FreeformLayeredPane root = buildLayeredPane(primaryLayer, parent);
		buildCanvas(root);
	}

	/**
	 * Adds a figure to the primary diagram layer
	 * 
	 * @param figure
	 *            the figure to add
	 */
	public void addFigure(final IFigure figure) {
		figure.setFont(primaryLayer.getFont());
		primaryLayer.add(figure);
	}

	/**
	 * Registers an action to be shown on the editor toolbar
	 * 
	 * @param action
	 *            the action to register
	 */
	public void addAction(final IAction action) {
		toolBarManager.add(action);
		toolBarManager.update(false);
	}

	private FreeformLayer buildPrimaryLayer(final Composite parent) {
		FreeformLayer result = new FreeformLayer();
		result.setLayoutManager(new FreeformLayout());
		result.setFont(parent.getFont());
		return result;
	}

	private FreeformLayeredPane buildLayeredPane(final FreeformLayer primaryLayer, final Composite parent) {
		FreeformLayeredPane result = new FreeformLayeredPane();
		result.add(primaryLayer);
		result.setFont(parent.getFont());
		return result;
	}

	private FigureCanvas buildCanvas(final IFigure root) {
		FigureCanvas result = new FigureCanvas(this);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		result.setLayoutData(gridData);
		result.setScrollBarVisibility(FigureCanvas.ALWAYS);
		result.setViewport(new FreeformViewport());
		result.setContents(root);
		return result;
	}

	private Label buildSeparator() {
		Label result = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		result.setLayoutData(gridData);
		return result;
	}

	private ToolBarManager buildToolBarManager(final ToolBar toolBar) {
		return new ToolBarManager(toolBar);
	}

	private ToolBar buildToolBar() {
		ToolBar result = new ToolBar(this, SWT.BORDER);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		result.setLayoutData(gridData);
		return result;
	}

}
