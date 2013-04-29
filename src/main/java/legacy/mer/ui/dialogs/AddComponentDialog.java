package legacy.mer.ui.dialogs;

import java.util.Set;

import legacy.mer.modelo.CurrentOpenProject;
import legacy.mer.modelo.ProyectoProxy;
import legacy.mer.modelo.base.Componente;
import legacy.mer.ui.editores.Editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;


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
	 * Controller used to select an existing component instead of adding a new
	 * one
	 */
	private final SelectComponentController selectComponentController;

	/**
	 * Creates a new {@link AddComponentDialog} instance
	 */
	public AddComponentDialog(final Shell shell,
			final SelectComponentController selectComponentController,
			final CurrentOpenProject currentProject) {
		super(shell);
		this.selectComponentController = selectComponentController;
		this.currentProject = currentProject;
	}

	/**
	 * Obtains the selected or created component to be added
	 */
	public T getComponent() {
		return component;
	}

	/**
	 * Obtains the current project query interface
	 */
	protected ProyectoProxy queryCurrentProject() {
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
			component = selectComponentController
					.launchSelection(loadComponents());
			setReturnCode(component == null ? CANCEL : OK);
			close();
		};
	};
}
