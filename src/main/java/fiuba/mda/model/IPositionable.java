package fiuba.mda.model;


public interface IPositionable {
	public static class Position implements java.io.Serializable {
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

	Position getPosition();
}
