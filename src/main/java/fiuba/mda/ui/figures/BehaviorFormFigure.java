package fiuba.mda.ui.figures;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Button;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.FrameBorder;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.UpdateManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import fiuba.mda.model.BehaviorForm;
import fiuba.mda.model.Representation;
import fiuba.mda.model.IPositionable.Position;

public class BehaviorFormFigure extends Figure implements MouseListener,MouseMotionListener{
	private Point moveStartedLocation;
	
	private List<Label> existingFields;
	private List<Label> fields;
	private List<Label> texts;
	private List<Button> buttons;

	private final Representation<BehaviorForm> form;
	private final GraficInterfaceDiagramFigure.Dialogs dialogs;	
	
	public BehaviorFormFigure(final Representation<BehaviorForm> form, final GraficInterfaceDiagramFigure.Dialogs dialogs) {
		this.form = form;
		this.dialogs = dialogs;
		setExistingFields();
		setNewFields();
		setTexts();
		setButtons();
		
		setLayoutManager(new StackLayout());
				
		addMouseListener(this);
		addMouseMotionListener(this);
		
		Panel formPanel = new Panel();
		formPanel.setBorder(new FrameBorder(form.getEntity().getFormName()));		
		formPanel.setLayoutManager(new FlowLayout(false));

		// Add configured components
		for (Label existingField : this.existingFields) {
			formPanel.add(existingField);
		}
		for (Label newField : this.fields) {
			formPanel.add(newField);
		}
		for (Label text : this.texts) {
			formPanel.add(text);
		}
		for (Button button : this.buttons) {
			formPanel.add(button);
		}
		
		add(formPanel, BorderLayout.CENTER);
	}
	
	@Override
	public void setParent(IFigure p) {
		super.setParent(p);
		if (p != null) {
			Position position = form.getPosition();
			Rectangle constraint = buildPositionalBound(position);
			p.getLayoutManager().setConstraint(this, constraint);
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
		form.getPosition().translate(offset.width, offset.height);
		
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
		dialogs.showFormDialog(form.getEntity());
	}
	
	public Representation<BehaviorForm> getForm() {
		return form;
	}	
	
	private void setExistingFields(){
		this.existingFields = new ArrayList<Label>();
		for (String field : this.form.getEntity().getExistingFields()) {
			Label fieldLbl = new Label(field);
			fieldLbl.setBorder(new LineBorder());
			fieldLbl.setBackgroundColor(ColorConstants.white);
			this.existingFields.add(fieldLbl);
		}
	}
	
	private void setNewFields(){
		this.fields = new ArrayList<Label>();
		for (String field : this.form.getEntity().getNewFields()) {
			Label fieldLbl = new Label(field);
			fieldLbl.setBorder(new LineBorder());
			fieldLbl.setBackgroundColor(ColorConstants.white);
			this.fields.add(fieldLbl);
		}
	}
	
	private void setTexts(){
		this.texts = new ArrayList<Label>();
		for (String text : this.form.getEntity().getTexts()) {
			this.texts.add(new Label(text));
		}
	}
	
	private void setButtons(){
		this.buttons = new ArrayList<Button>();
		for (String buttonName : this.form.getEntity().getButtons()) {
			this.buttons.add(new Button(buttonName)); 
		}
	}
	
}