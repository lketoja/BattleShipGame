package shipBattle;

public class CoordinateValidator {

	public Coordinate validateCoordinate(String input) {
		if (input.length() < 2)
			throw new IllegalArgumentException();
		int x = input.charAt(0) - 'a';
		int y = input.charAt(1) - '0';
		if (between0And9(x) && between0And9(y)) {
			return new Coordinate(x, y);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public boolean between0And9(int number) {
		return number >= 0 && number <= 9;
	}

}
