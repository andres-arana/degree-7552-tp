package fiuba.mda.ui.main.tree;

import org.eclipse.swt.graphics.Image;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.ModelAspect;
import fiuba.mda.model.ModelEntity;
import fiuba.mda.model.ModelPackage;
import fiuba.mda.model.ProjectComponent;
import fiuba.mda.model.ProjectComponentVisitor;
import fiuba.mda.ui.utilities.ImageLoader;

/**
 * {@link ProjectComponentVisitor} which provides image instances for a given
 * {@link ProjectComponent}.
 */
public class ComponentImageVisitor implements ProjectComponentVisitor {
	private final ImageLoader images;

	private Optional<Image> image = Optional.absent();

	/**
	 * Creates a new {@link ComponentImageVisitor} instance
	 * 
	 * @param images
	 *            the image loader to use to load the images to provide
	 */
	@Inject
	public ComponentImageVisitor(final ImageLoader images) {
		this.images = images;
	}

	@Override
	public void visit(ModelPackage modelPackage) {
		if (modelPackage.isRoot()) {
			image = Optional.of(images.of("folder_page"));
		} else {
			image = Optional.of(images.of("package"));
		}
	}

	@Override
	public void visit(ModelEntity modelEntity) {
		// TODO: Define image for entities
	}

	@Override
	public void visit(ModelAspect modelAspect) {
		image = Optional.of(images.of("folder"));
	}

	@Override
	public void visit(BehaviorDiagram behaviorDiagram,boolean isEditing) {

	}

    @Override
    public void visit(BehaviorDiagram behaviorDiagram) {
        image = Optional.of(images.of("chart_line"));
    }

    /**
	 * Obtains the image for a given {@link ProjectComponent} by visiting it and
	 * obtaining the configured image through double dispatching
	 * 
	 * @param model
	 *            the model to obtain the image from
	 * @return the image, if any, or absent if none was configured for the
	 *         {@link ProjectComponent}
	 */
	public Optional<Image> imageFor(ProjectComponent model) {
		model.accept(this);
		return image;
	}
}
