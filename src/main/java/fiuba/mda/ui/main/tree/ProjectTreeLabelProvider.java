package fiuba.mda.ui.main.tree;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import fiuba.mda.model.ProjectComponent;

/**
 * {@link LabelProvider} implementation for the project tree
 */
@Singleton
public class ProjectTreeLabelProvider extends LabelProvider {
	private final Provider<ProjectTreeImageVisitor> visitorProvider;

	/**
	 * Creates a new @{link ProjectTreeLabelProvider} instance
	 * 
	 * @param visitorProvider
	 *            a provider for {@link ProjectTreeImageVisitor} instances used
	 *            to identify the image to associate to a given
	 *            {@link ProjectComponent}
	 */
	@Inject
	public ProjectTreeLabelProvider(
			final Provider<ProjectTreeImageVisitor> visitorProvider) {
		this.visitorProvider = visitorProvider;
	}

	@Override
	public String getText(Object element) {
		ProjectComponent model = (ProjectComponent) element;
		return model.getName();
	}

	@Override
	public Image getImage(Object element) {
		ProjectComponent model = (ProjectComponent) element;
		Optional<Image> result = visitorProvider.get().imageFor(model);
		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
	}
}
