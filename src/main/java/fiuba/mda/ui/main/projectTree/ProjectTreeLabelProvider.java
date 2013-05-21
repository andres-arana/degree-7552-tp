package fiuba.mda.ui.main.projectTree;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.ModelEntity;
import fiuba.mda.model.ModelPackage;
import fiuba.mda.model.ProjectComponent;
import fiuba.mda.ui.utilities.ImageLoader;

/**
 * {@link LabelProvider} implementation for the project tree
 */
@Singleton
public class ProjectTreeLabelProvider extends LabelProvider {
	/**
	 * The {@link ImageLoader} to use to obtain the images for the components
	 */
	private final ImageLoader imageLoader;

	/**
	 * Creates a new {@link ProjectTreeLabelProvider} instane
	 */
	@Inject
	public ProjectTreeLabelProvider(final ImageLoader imageLoader) {
		this.imageLoader = imageLoader;
	}

	/**
	 * Override {@link LabelProvider#getText(Object)} to return the associated
	 * text of a given element.
	 */
	@Override
	public String getText(Object element) {
		ProjectComponent model = (ProjectComponent) element;
		return model.getName();
	}

	/**
	 * Override the {@link LabelProvider#getImage(Object)} to return the
	 * associated image of a given element.
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof ModelPackage) {
			return imageLoader.loadImage("package");
		} else if (element instanceof ModelEntity) {
			return imageLoader.loadImage("entidad");
		} else {
			return null;
		}
	}
}
