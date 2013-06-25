package fiuba.mda.model;

public class BehaviorState {
	private int x = 0;
	private int y = 0;
	private final String name;
	
	public BehaviorState(final String name) {
		this.name = name;
	}

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

	public String getName() {
		return name;
	}
}
