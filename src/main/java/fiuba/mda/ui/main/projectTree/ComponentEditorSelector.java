package fiuba.mda.ui.main.projectTree;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.ModelPackage;
import fiuba.mda.model.ProjectComponent;
import fiuba.mda.ui.controllers.EditorController;
import fiuba.mda.ui.controllers.PackageEditorController;
import fiuba.mda.utilities.ClassSelector;

/**
 * {@link ClassSelector} which allows selecting the appropriate
 * {@link EditorController} for a given {@link ProjectComponent}.
 */
@Singleton
public class ComponentEditorSelector extends ClassSelector<EditorController> {
	/**
	 * Creates a new @{link ComponentEditorSelector} instance
	 * 
	 * @param packageController
	 *            the controller to use for {@link ModelPackage} instances
	 */
	@Inject
	public ComponentEditorSelector(
			final PackageEditorController packageController) {
		register(ModelPackage.class, packageController);

	}
}
