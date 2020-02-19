package shipBattle;

public class Coordinate {
	
	public final int x;
	public final int y;
	
	public Coordinate(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
