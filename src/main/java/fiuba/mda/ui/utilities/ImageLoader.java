package fiuba.mda.ui.utilities;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.google.inject.Singleton;

/**
 * Utility class which loads and provides images
 */
@Singleton
public class ImageLoader {
	/**
	 * Cache of already loaded images
	 */
	private final Map<String, Image> loadedImages = new HashMap<>();

	/**
	 * Loads an image from the resources given its name, returning an
	 * {@link Image} instance of it
	 * 
	 * @param name
	 *            the name of the image
	 */
	public Image loadImage(final String name) {
		if (loadedImages.containsKey(name)) {
			return loadedImages.get(name);
		} else {
			InputStream resource = ImageLoader.class
					.getResourceAsStream("/imagenes/" + name + ".png");
			Image image = new Image(Display.getCurrent(), resource);
			loadedImages.put(name, image);
			return image;
		}
	}

	/**
	 * Loads an image from the resources given its name, returning an
	 * {@link ImageDescriptor} instance of it.
	 * 
	 * @param name
	 *            the name of the image
	 */
	public ImageDescriptor loadImageDescriptor(final String name) {
		Image image = loadImage(name);
		return ImageDescriptor.createFromImage(image);
	}
}
