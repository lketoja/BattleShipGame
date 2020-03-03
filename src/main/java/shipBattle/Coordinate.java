package shipBattle;

import java.io.Serializable;

public class Coordinate implements Serializable{
	
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
