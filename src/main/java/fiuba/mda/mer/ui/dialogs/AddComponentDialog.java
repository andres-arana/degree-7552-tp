package fiuba.mda.mer.ui.dialogs;

import java.util.Set;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import fiuba.mda.mer.modelo.ProyectoProxy;
import fiuba.mda.mer.modelo.base.Componente;
import fiuba.mda.mer.ui.CurrentOpenProject;
import fiuba.mda.mer.ui.editores.Editor;

/**
 * Base abstract class for all dialogs which add a component to a diagram
 * 
 * @param <T>
 *            the type of the component which this dialog adds
 */
public abstract class AddComponentDialog<T extends Componente> extends
		BaseDialog {
	/**
	 * The currently selected component
	 */
	private T component = null;

	/**
	 * The current open project
	 */
	private final CurrentOpenProject currentProject;

	/**
	 * Creates a new {@link AddComponentDialog} instance
	 * 
	 * @param shell
	 *            the shell where this dialog will be displayed in
	 * @param currentProject
	 *            the current open project provider
	 */
	public AddComponentDialog(final Shell shell,
			final CurrentOpenProject currentProject) {
		super(shell);
		this.currentProject = currentProject;
	}

	/**
	 * Opens the dialog and returns the selected or created component. Returns
	 * null if the user cancels the dialog.
	 */
	public T openForAddingComponent() {
		return open() == Window.OK ? component : null;
	}

	/**
	 * Obtains the current project query interface
	 */
	protected ProyectoProxy getCurrentProject() {
		return currentProject.getForQueries();
	}

	/**
	 * Obtains the name of the component to add, used to build the dialog title
	 */
	protected abstract String getComponentName();

	/**
	 * Loads the components which will be used to select from if the select
	 * option is used
	 */
	protected abstract Set<T> loadComponents();

	/**
	 * Obtains the editor to be used to create a new component if the new option
	 * is used
	 */
	protected abstract Editor<?> getEditor();

	@Override
	protected String getTitle() {
		return Editor.AGREGAR + " " + getComponentName();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		Button selectComponent = new Button(container, SWT.PUSH);
		selectComponent.setText("Seleccionar Existente");
		selectComponent.addSelectionListener(this.existingSelectedListener);
		selectComponent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Button newComponent = new Button(container, SWT.PUSH);
		newComponent.setText(Editor.NUEVO);
		newComponent.addSelectionListener(this.newSelectedListener);
		newComponent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		return container;
	}

	private SelectionAdapter newSelectedListener = new SelectionAdapter() {
		@SuppressWarnings("unchecked")
		public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
			Editor<?> editor = getEditor();
			int result = editor.open();
			component = (T) editor.getComponente();
			setReturnCode(result);
			close();
		};
	};

	private SelectionAdapter existingSelectedListener = new SelectionAdapter() {
		public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
			SeleccionarComponenteDialog<T> dialog = new SeleccionarComponenteDialog<T>(
					loadComponents());
			int result = dialog.open();
			component = (T) dialog.getComponente();
			setReturnCode(result);
			close();
		};
	};
}
