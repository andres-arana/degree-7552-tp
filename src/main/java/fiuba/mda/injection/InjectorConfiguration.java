package fiuba.mda.injection;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Bootstraps an {@link Injector} by configuring the binding modules in this
 * package
 */
public class InjectorConfiguration {

	/**
	 * Creates a new {@link Injector} instance by configuring the injection
	 * modules in it
	 * 
	 * @return the new configured {@link Injector} instance
	 */
	public static Injector bootstrapInjector() {
		List<Module> modules = new ArrayList<>();
        JFaceModule e = new JFaceModule();
        modules.add(e);
		return Guice.createInjector(modules);
	}

}
