package fiuba.mda.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic utility class to manage event propagation. Represents an event which
 * can be observed
 * 
 * @param <T>
 *            The type of the class owning the observable event
 * @param <D>
 *            The type of the data associated to the event
 */
public class ObservableEvent<T, D> {
	/**
	 * Represents an observer which can observe an observable event, being
	 * notified when the event is raised
	 * 
	 * @see ObservableEvent
	 */
	public static interface Observer<T, D> {
		/**
		 * Notifies this observer that the event has been raised
		 * 
		 * @param observable
		 *            the observable which raised the event
		 * @param eventData
		 *            specific event data associated to the observable event
		 */
		void notify(final T observable, final D eventData);
	}

	/**
	 * The list of registered observers
	 */
	private final List<Observer<T, D>> observers = new ArrayList<>();

	/**
	 * The instance owning the observable event
	 */
	private final T observable;

	/**
	 * Creates a new {@link ObservableEvent} instance
	 * 
	 * @param observable
	 *            the instance owning the observable event
	 */
	public ObservableEvent(final T observable) {
		this.observable = observable;
	}

	/**
	 * Registers an observer to be notified when the event is raised
	 */
	public void observe(final Observer<T, D> observer) {
		this.observers.add(observer);
	}

	/**
	 * Unregisters an observer to no longer be notified when the event is raised
	 */
	public void unobserve(final Observer<T, D> observer) {
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
	 * 
	 * @param eventData
	 *            the event data to send to all observers
	 */
	public void raise(final D eventData) {
		for (Observer<T, D> observer : observers) {
			observer.notify(observable, eventData);
		}
	}
}
