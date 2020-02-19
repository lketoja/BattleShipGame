package shipBattle;

public class CoordinateValidator {

	public Coordinate validateCoordinate(String input) {
		int x = input.charAt(1) - '0';
		int y = input.charAt(0) - 'a';
		if(between0And9(x) && between0And9(y)) {
			return new Coordinate(x, y);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public boolean isOnBoard(Coordinate coord) {
		return false;
	}
	
	public boolean between0And9(int number) {
		return number>=0 && number <= 9;
	}

}
