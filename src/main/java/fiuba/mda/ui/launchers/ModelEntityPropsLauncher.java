package fiuba.mda.ui.launchers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import fiuba.mda.model.ModelEntity;
import fiuba.mda.ui.dialogs.ModelEntityEditor;

@Singleton
public class ModelEntityPropsLauncher extends BaseLauncher<ModelEntity> {
	private final Provider<ModelEntityEditor> entityEditorProvider;

	@Inject
	public ModelEntityPropsLauncher(
			Provider<ModelEntityEditor> entityEditorProvider) {
		this.entityEditorProvider = entityEditorProvider;
	}

	@Override
	protected void doLaunch(final ModelEntity component) {
		ModelEntityEditor editor = entityEditorProvider.get();
		editor.edit(component);
	}

}
