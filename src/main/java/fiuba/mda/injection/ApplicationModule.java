package fiuba.mda.injection;

import com.google.inject.AbstractModule;

import fiuba.mda.Application;

/**
 * Injection module which configures application components
 */
public class ApplicationModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(Application.class);
	}
}
