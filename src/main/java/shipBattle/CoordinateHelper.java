package shipBattle;

public class CoordinateHelper {

	public static Coordinate getTheNextCoordinate(Coordinate startPoint, Direction direction) {
		switch (direction) {
		case UP:
			if (startPoint.y == 0)
				throw new cantMoveInThatDirectionException();
			return new Coordinate(startPoint.x, startPoint.y - 1);
		case DOWN:
			if (startPoint.y == 9)
				throw new cantMoveInThatDirectionException();
			return new Coordinate(startPoint.x, startPoint.y + 1);
		case LEFT:
			if (startPoint.x == 0)
				throw new cantMoveInThatDirectionException();
			return new Coordinate(startPoint.x - 1, startPoint.y);
		case RIGHT:
			if (startPoint.x == 9)
				throw new cantMoveInThatDirectionException();
			return new Coordinate(startPoint.x + 1, startPoint.y);
		default:
			throw new IllegalArgumentException();
		}
	}

	public static Direction getOppositeDirection(Direction direction) {
		switch (direction) {
		case UP:
			return Direction.DOWN;
		case DOWN:
			return Direction.UP;
		case LEFT:
			return Direction.RIGHT;
		case RIGHT:
			return Direction.LEFT;
		default:
			throw new IllegalArgumentException();
		}
	}

	public static Coordinate skipOverOneCoordinateAndGetTheNext(Coordinate startPoint) {
		//first we assume that we stay in one horizontal row
		int newX = startPoint.x + 2;
		int newY = startPoint.y;
		if(newX > 9) { //this is true if startPoint.x was 8 or 9 so we actually have to move to next line
			newX = newX % 10;
			newY = (newY + 1) % 10; //if startPoint.y was 9, we move to first row
		}
		return new Coordinate(newX, newY);
	}

}
