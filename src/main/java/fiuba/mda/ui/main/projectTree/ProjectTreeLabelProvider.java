package fiuba.mda.ui.main.projectTree;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.ProjectComponent;

/**
 * {@link LabelProvider} implementation for the project tree
 */
@Singleton
public class ProjectTreeLabelProvider extends LabelProvider {
	private final ProjectTreeImageSelector selector;

	/**
	 * Creates a new @{link ProjectTreeLabelProvider} instance
	 * 
	 * @param selector
	 *            the image selector which selects the image of each element in
	 *            the project tree
	 */
	@Inject
	public ProjectTreeLabelProvider(final ProjectTreeImageSelector selector) {
		this.selector = selector;
	}

	@Override
	public String getText(Object element) {
		ProjectComponent model = (ProjectComponent) element;
		return model.getName();
	}

	@Override
	public Image getImage(Object element) {
		Optional<Image> result = selector.fromInstance(element);
		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
	}
}
