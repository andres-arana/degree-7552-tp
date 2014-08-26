package fiuba.mda.ui.launchers;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.ModelEntity;
import fiuba.mda.ui.dialogs.ModelEntityEditor;

@Singleton
public class ModelEntityPropsLauncher extends BaseLauncher<ModelEntity> {
	private final ModelEntityEditor entityEditor;

	@Inject
	public ModelEntityPropsLauncher(final ModelEntityEditor entityEditor) {
		this.entityEditor = entityEditor;
	}

	@Override
	protected void doLaunch(final ModelEntity component) {
		this.entityEditor.edit(component);
	}

}
