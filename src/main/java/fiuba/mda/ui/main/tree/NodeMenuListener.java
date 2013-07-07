package fiuba.mda.ui.main.tree;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import fiuba.mda.model.Application;
import fiuba.mda.model.ProjectComponent;

@Singleton
public class NodeMenuListener implements IMenuListener {
	private final Application model;
	private final Provider<ComponentContextualActionsVisitor> actionsProvider;

	@Inject
	public NodeMenuListener(final Application model,
			final Provider<ComponentContextualActionsVisitor> actionsProvider) {
		this.model = model;
		this.actionsProvider = actionsProvider;
	}

	@Override
	public void menuAboutToShow(IMenuManager manager) {
		if (model.hasCurrentProject()) {
			ProjectComponent component = model.getSelectedComponent();
			ComponentContextualActionsVisitor visitor = actionsProvider.get();
			visitor.fillActionsFor(manager, component);	
		}
	}

}
