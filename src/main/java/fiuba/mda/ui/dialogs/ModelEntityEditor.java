package fiuba.mda.ui.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.google.inject.Inject;

import fiuba.mda.model.ModelEntity;

public class ModelEntityEditor extends org.eclipse.jface.dialogs.Dialog {
	private ModelEntity component;
	private Label nameLabel;
	private Text nameText;

	@Inject
	public ModelEntityEditor(final Shell shell) {
		super(shell);
	}
	
	public void edit(ModelEntity component) {
		this.component = component;
		this.open();
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(800,  600);
	}

	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		
		this.getButton(IDialogConstants.OK_ID).setText("Aceptar");
		this.getButton(IDialogConstants.CANCEL_ID).setText("Cancelar");
		
		return control;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);

		Composite header = new Composite(dialogArea, SWT.NONE);
		header.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		header.setLayout(new GridLayout(2, false));
		
		this.nameLabel = new Label(header, SWT.LEFT);
		this.nameLabel.setText("Nombre");
		this.nameText = new Text(header, SWT.BORDER);
		this.nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));
		
		return dialogArea;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Editando Entidad " + this.component.getQualifiedName());
	}
	
	
	
	
}
