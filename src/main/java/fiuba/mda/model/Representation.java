package fiuba.mda.model;


/**
 * Representation in a diagram for a given entity.
 *
 * @param <T> The type of the entity being represented 
 */
public class Representation<T> {
	public static class Position {
		int x = 0;
		int y = 0;
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public void translate(int u, int v) {
			this.x += u;
			this.y += v;
		}
	}
	
	private final Position position;
	private final T entity;
	
	public Representation(final T entity) {
		this.entity = entity;
		this.position = new Position();
	}

	public Position getPosition() {
		return position;
	}

	public T getEntity() {
		return entity;
	}
}
