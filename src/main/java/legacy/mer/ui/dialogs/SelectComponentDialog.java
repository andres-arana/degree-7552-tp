package legacy.mer.ui.dialogs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import legacy.mer.modelo.base.Componente;
import legacy.mer.ui.MessageBoxes;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;


/**
 * Dialog which allows a user to select from a list of components
 * 
 * @param <T>
 *            the type of the elements which can be selected from
 */
public class SelectComponentDialog<T extends Componente> extends BaseDialog {
	/**
	 * Service used to display message boxes
	 */
	private final MessageBoxes messageBoxes;

	/**
	 * List of the selectable names, in sync with the components list, to be
	 * used for selection matching
	 */
	private final List<String> componentNames = new ArrayList<>();

	/**
	 * List of selectables
	 */
	private final List<T> components;

	/**
	 * The currently selected instance
	 */
	private T currentComponent = null;

	/**
	 * Combo box containing the selectables
	 */
	private Combo combo;

	/**
	 * Creates a new {@link SelectComponentDialog}
	 */
	public SelectComponentDialog(final Shell shell,
			final MessageBoxes messageBoxes, final Collection<T> components) {
		super(shell);
		this.messageBoxes = messageBoxes;
		this.components = new ArrayList<>(components);
		Collections.sort(this.components);

		for (T componente : this.components) {
			this.componentNames.add(componente.toString());
		}
	}

	@Override
	protected String getTitle() {
		return "Seleccionar";
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		Label lblEntidades = new Label(container, SWT.LEFT);
		lblEntidades.setText("Seleccionar una opción:");

		this.combo = new Combo(container, SWT.READ_ONLY);
		this.combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		for (T componente : components) {
			this.combo.add(componente.toString());
		}

		return container;
	}

	/**
	 * Devuelve la entidad seleccionada o creada.
	 * 
	 * @return
	 */
	public T getSelectedComponent() {
		return this.currentComponent;
	}

	@Override
	protected void okPressed() {
		if (this.combo.getSelectionIndex() == -1) {
			messageBoxes.error("No seleccionó ninguna opción.");
		} else {
			String nombre = combo.getText();
			int index = this.componentNames.indexOf(nombre);
			currentComponent = this.components.get(index);
			super.okPressed();
		}
	}
}
