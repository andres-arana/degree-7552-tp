package fiuba.mda.model;

import java.util.ArrayList;
import java.util.List;

public class ObservableEvent<T, D> {
	public static interface Observer<T, D> {
		void notify(final T observable, final D eventData);
	}
	
	private final List<Observer<T, D>> observers = new ArrayList<>();
	private final T observable;
	
	public ObservableEvent(final T observable) {
		this.observable = observable;
	}
	
	public void observe(final Observer<T, D> observer) {
		this.observers.add(observer);
	}
	
	public void unobserve(final Observer<T, D> observer) {
		this.observers.remove(observer);
	}
	
	public void clearObservers() {
		this.observers.clear();
	}
	
	public void raise(final D eventData) {
		for (Observer<T, D> observer : observers) {
			observer.notify(observable, eventData);
		}
	}
}
