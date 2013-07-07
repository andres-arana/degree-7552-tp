package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.Application;
import fiuba.mda.model.ProjectComponent;
import fiuba.mda.ui.launchers.SimpleDialogLauncher;
import fiuba.mda.ui.utilities.ImageLoader;

@Singleton
public class DeleteSelectionAction extends Action {
	private final Application model;
	private final SimpleDialogLauncher dialogs;

	@Inject
	public DeleteSelectionAction(final ImageLoader images,
			final Application model, final SimpleDialogLauncher dialogs) {
		this.model = model;
		this.dialogs = dialogs;
		setupPresentation(images);
	}

	private void setupPresentation(final ImageLoader images) {
		setText("&Eliminar");
		setToolTipText("Eliminar este componente");
		setImageDescriptor(images.descriptorOf("delete"));
	}

	@Override
	public void run() {
		ProjectComponent selectedComponent = model.getSelectedComponent();
		if (!selectedComponent.isRoot()) {
			if (showConfirmation(selectedComponent)) {
				ProjectComponent parent = model.getSelectedComponent()
						.getParent();
				parent.removeChild(model.getSelectedComponent());
			}
		}
	}

	private boolean showConfirmation(ProjectComponent selectedComponent) {
		return dialogs.showConfirm(dialogTitle(selectedComponent),
				dialogsMessage(selectedComponent));
	}

	private String dialogsMessage(ProjectComponent selectedComponent) {
		return "Esta seguro de querer eliminar "
				+ selectedComponent.getQualifiedName() + "?";
	}

	private String dialogTitle(ProjectComponent selectedComponent) {
		return "Confirmar eliminacion";
	}
}
