package fiuba.mda.ui.actions;

import fiuba.mda.ui.main.tree.ComponentPropEditorVisitor;
import org.eclipse.jface.action.Action;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import fiuba.mda.model.Application;
import fiuba.mda.model.ProjectComponent;
import fiuba.mda.ui.launchers.Launcher;
import fiuba.mda.ui.utilities.ImageLoader;

@Singleton
public class EditSelectionPropertiesAction extends Action {
	private final Application model;
	private final Provider<ComponentPropEditorVisitor> propertyEditors;

	@Inject
	public EditSelectionPropertiesAction(final ImageLoader images,
			final Application model,
			final Provider<ComponentPropEditorVisitor> propertyEditors) {
		this.model = model;
		this.propertyEditors = propertyEditors;
		setupPresentation(images);
	}

	private void setupPresentation(final ImageLoader images) {
		setText("&Propiedades");
		setToolTipText("Editar las propiedades de este componente");
		setImageDescriptor(images.descriptorOf("pencil"));
	}

	@Override
	public void run() {
		ProjectComponent component = model.getSelectedComponent();

		ComponentPropEditorVisitor visitor = propertyEditors.get();
		Optional<Launcher> launcher = visitor.launcherFor(component);

		if (launcher.isPresent()) {
			launcher.get().launch(component);
		}
	}
}
