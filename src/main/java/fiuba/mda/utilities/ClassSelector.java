package fiuba.mda.utilities;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Optional;

/**
 * Base class for services which provide instances of other services based on
 * the class of a given object.
 * 
 * @param <T>
 *            The type of the services to return based on the given object class
 */
public abstract class ClassSelector<T> {
	private final Map<String, T> instances = new HashMap<>();

	protected void register(final Class<?> type, final T instance) {
		instances.put(keyFor(type), instance);
	}

	private String keyFor(final Class<?> type) {
		return type.getCanonicalName();
	}

	/**
	 * Obtains the service instance for a given object
	 * 
	 * @param instance
	 *            the instance to get the service for
	 * @return the appropriate service for the given instance, if any has been
	 *         registered, or absent if none was registered.
	 */
	public Optional<T> fromInstance(final Object instance) {
		return fromClass(instance.getClass());
	}

	/**
	 * Obtains the service instance for a given type
	 * 
	 * @param type
	 *            the type to get the service for
	 * @return the appropriate service for the given type, if any has been
	 *         registered, or absent if none was registered.
	 */
	public Optional<T> fromClass(final Class<?> type) {
		T result = instances.get(keyFor(type));
		if (result != null) {
			return Optional.of(result);
		} else {
			return Optional.absent();
		}
	}
}
