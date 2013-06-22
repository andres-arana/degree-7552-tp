package fiuba.mda.ui.launchers;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.ui.main.MainWindow;

/**
 * {@link EditorLauncher} implementation which allows editing a behavior
 * diagram
 */
@Singleton
public class BehaviorDiagramEditorLauncher extends
		BaseEditorLauncher<BehaviorDiagram> {
	private final MainWindow mainWindow;

	@Inject
	public BehaviorDiagramEditorLauncher(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	@Override
	protected void doLaunch(BehaviorDiagram component) {
		
	}
}
