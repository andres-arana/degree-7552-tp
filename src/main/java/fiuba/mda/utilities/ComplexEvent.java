package fiuba.mda.utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic utility class to manage event propagation. Represents a complex event
 * which can be observed, allowing interaction between the event raiser and the
 * event handler through an event parameter.
 * 
 * @param <T>
 *            The type of the class owning the observable event
 * @param <D>
 *            The type of the parameter associated to the event
 */
public class ComplexEvent<T, D> {
	/**
	 * Represents an observer which can observe an observable event, being
	 * notified when the event is raised
	 * 
	 * @param <T>
	 *            the type of the class owning the event
	 * @param <D>
	 *            the type of the parameter associated to the event
	 * 
	 * @see ComplexEvent
	 */
	public static interface Observer<T, D> {
		/**
		 * Notifies this observer that the event has been raised
		 * 
		 * @param observable
		 *            the observable which raised the event
		 * @param parameter
		 *            specific event data associated to the observable event
		 */
		void notify(final T observable, final D parameter);
	}

	private final List<Observer<T, D>> observers = new ArrayList<>();

	private final T observable;

	/**
	 * Creates a new @{link ComplexEvent} instance
	 * 
	 * @param observable
	 *            the observable which owns the event
	 */
	public ComplexEvent(final T observable) {
		this.observable = observable;
	}

	/**
	 * Registers an observer to be notified when the event is raised
	 * 
	 * @param observer
	 *            the observer to register
	 */
	public void observe(final Observer<T, D> observer) {
		this.observers.add(observer);
	}

	/**
	 * Unregisters an observer to no longer be notified when the event is raised
	 * 
	 * @param observer
	 *            the observer to unregister
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
