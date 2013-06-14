package fiuba.mda.ui.main.projectTree;

import org.eclipse.swt.graphics.Image;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.ModelAspect;
import fiuba.mda.model.ModelPackage;
import fiuba.mda.model.ProjectComponent;
import fiuba.mda.ui.utilities.ImageLoader;
import fiuba.mda.utilities.ClassSelector;

/**
 * Class selector which provide image instances for a given
 * {@link ProjectComponent}.
 */
@Singleton
public class ProjectTreeImageSelector extends ClassSelector<Image> {

	/**
	 * Creates a new @{link ProjectTreeImageSelector} instance
	 * 
	 * @param images
	 *            the image loader to use to load the images to provide
	 */
	@Inject
	public ProjectTreeImageSelector(final ImageLoader images) {
		register(ModelPackage.class, images.of("brick"));
		register(ModelAspect.class, images.of("folder"));
		register(BehaviorDiagram.class, images.of("chart_line"));
	}
}
