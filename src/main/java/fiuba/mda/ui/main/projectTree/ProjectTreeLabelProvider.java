package fiuba.mda.ui.main.projectTree;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.ModelAspect;
import fiuba.mda.model.ModelPackage;
import fiuba.mda.model.ProjectComponent;
import fiuba.mda.ui.utilities.ImageLoader;

/**
 * {@link LabelProvider} implementation for the project tree
 */
@Singleton
public class ProjectTreeLabelProvider extends LabelProvider {
	private final Map<String, Image> imagesByType = new HashMap<>();

	/**
	 * Creates a new @{link ProjectTreeLabelProvider} instance
	 * 
	 * @param imageLoader
	 *            the image loader used to provide images for the elements of
	 *            the project tree
	 */
	@Inject
	public ProjectTreeLabelProvider(final ImageLoader imageLoader) {
		imagesByType.put(keyFor(ModelPackage.class),
				imageLoader.loadImage("brick"));
		imagesByType.put(keyFor(ModelAspect.class),
				imageLoader.loadImage("folder"));
		imagesByType.put(keyFor(BehaviorDiagram.class),
				imageLoader.loadImage("chart_line"));
	}

	private String keyFor(final Class<?> type) {
		return type.getCanonicalName();
	}

	@Override
	public String getText(Object element) {
		ProjectComponent model = (ProjectComponent) element;
		return model.getName();
	}

	@Override
	public Image getImage(Object element) {
		return imagesByType.get(keyFor(element.getClass()));
	}
}
