package fiuba.mda.model;


/**
 * Representation in a diagram for a given entity.
 *
 * @param <T> The type of the entity being represented 
 */
public class Representation<T> implements IPositionable {

	private final IPositionable.Position position;
	private final T entity;
	
	public Representation(final T entity) {
		this.entity = entity;
		this.position = new Position();
	}

	@Override
	public IPositionable.Position getPosition() {
		return position;
	}

	public T getEntity() {
		return entity;
	}
}
