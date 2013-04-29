package fiuba.mda.ui.utilities;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.google.inject.Singleton;

@Singleton
public class ImageLoader {
	private final Map<String, Image> loadedImages = new HashMap<>();

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

}
