package fiuba.mda.utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic utility class to manage event propagation. Represents a simple event
 * which can be observed.
 * 
 * @param <T>
 *            The type of the class owning the observable event
 */
public class SimpleEvent<T> {
	/**
	 * Represents an observer which can observe an observable event, being
	 * notified when the event is raised
	 * 
	 * @param <T>
	 *            the type of the class owning the event
	 * 
	 * @see SimpleEvent
	 */
	public static interface Observer<T> {
		/**
		 * Notifies this observer that the event has been raised
		 * 
		 * @param observable
		 *            the observable which raised the event
		 */
		void notify(final T observable);
	}

	private final List<Observer<T>> observers = new ArrayList<>();

	private final T observable;

	/**
	 * Creates a new {@link SimpleEvent} instance
	 * 
	 * @param observable
	 *            the instance owning the observable event
	 */
	public SimpleEvent(final T observable) {
		this.observable = observable;
	}

	/**
	 * Registers an observer to be notified when the event is raised
	 * 
	 * @param observer
	 *            the observer to register
	 */
	public void observe(final Observer<T> observer) {
		this.observers.add(observer);
	}

	/**
	 * Unregisters an observer to no longer be notified when the event is raised
	 * 
	 * @param observer
	 *            the observer to unregister
	 */
	public void unobserve(final Observer<T> observer) {
		this.observers.remove(observer);
	}

	/**
	 * Unregisters all observers so that no previously registered observer is
	 * notified when the event is raised
	 */
	public void clearObservers() {
		this.observers.clear();
	}

	/**
	 * Raises the event, notifying all registered observers
	 */
	public void raise() {
		for (Observer<T> observer : observers) {
			observer.notify(observable);
		}
	}
}
