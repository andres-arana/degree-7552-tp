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
	private final ImageLoader imageLoader;

	/**
	 * Creates a new @{link ProjectTreeLabelProvider} instance
	 * 
	 * @param imageLoader
	 *            the image loader used to provide images for the elements of
	 *            the project tree
	 */
	@Inject
	public ProjectTreeLabelProvider(final ImageLoader imageLoader) {
		this.imageLoader = imageLoader;
	}

	@Override
	public String getText(Object element) {
		ProjectComponent model = (ProjectComponent) element;
		return model.getName();
	}

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
