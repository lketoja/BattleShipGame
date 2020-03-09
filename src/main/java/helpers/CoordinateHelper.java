package helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import exceptions.CantMoveInThatDirectionException;

public class CoordinateHelper {

	public static Coordinate validateCoordinate(String input) {
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

	public static boolean between0And9(int number) {
		return number >= 0 && number <= 9;
	}

	public static List<Coordinate> generateCoordinates(Coordinate coordinate, Direction direction, int length) {
		List<Coordinate> coordinates = new ArrayList<>();
		Coordinate coordinateWeWantToAddToTheList = coordinate;
		for (int i = 0; i < length; i++) {
			coordinates.add(coordinateWeWantToAddToTheList);
			coordinateWeWantToAddToTheList = CoordinateHelper.getTheNextCoordinate(coordinateWeWantToAddToTheList,
					direction);
		}
		return coordinates;
	}

	public static Coordinate getTheNextCoordinate(Coordinate startPoint, Direction direction) {
		switch (direction) {
		case UP:
			if (startPoint.y == 0)
				throw new CantMoveInThatDirectionException();
			return new Coordinate(startPoint.x, startPoint.y - 1);
		case DOWN:
			if (startPoint.y == 9)
				throw new CantMoveInThatDirectionException();
			return new Coordinate(startPoint.x, startPoint.y + 1);
		case LEFT:
			if (startPoint.x == 0)
				throw new CantMoveInThatDirectionException();
			return new Coordinate(startPoint.x - 1, startPoint.y);
		case RIGHT:
			if (startPoint.x == 9)
				throw new CantMoveInThatDirectionException();
			return new Coordinate(startPoint.x + 1, startPoint.y);
		default:
			throw new IllegalArgumentException();
		}
	}

	public static boolean isOutOfBoard(List<Coordinate> location) {
		for (Coordinate coordinate : location) {
			if (!between0And9(coordinate.x) || !between0And9(coordinate.y))
				return false;
		}
		return true;
	}

	public static List<Coordinate> getTheCoordinatesSurrounding(List<Coordinate> shipsCoordinates) {
		List<Coordinate> coordinates = new ArrayList<>();
		for (Coordinate middlePoint : shipsCoordinates) {
			coordinates.addAll(getTheCoordinatesSurroundingACoordinate(middlePoint));
		}
		return coordinates;
	}

	public static List<Coordinate> getTheCoordinatesSurroundingACoordinate(Coordinate middlePoint) {
		List<Direction> directions = Arrays.asList(Direction.values());
		List<Coordinate> coordinates = new ArrayList<>();
		for (Direction direction : directions) {
			try {
				coordinates.add(CoordinateHelper.getTheNextCoordinate(middlePoint, direction));
			} catch (CantMoveInThatDirectionException e) {
			}
		}
		return coordinates;
	}

	public static List<Coordinate> getTheCoordinatesToBeChecked(Coordinate startPoint, int x) {
		if(x==1) 
			return Arrays.asList(startPoint);
		if (x == 4)
			return getTheCoordinatesSurroundingACoordinate(startPoint);
		if (x == 12) {
			List<Coordinate> coordinatesToBeChecked = getTheCoordinatesSurroundingACoordinate(startPoint);
			coordinatesToBeChecked.addAll(getTheCoordinatesSurrounding(coordinatesToBeChecked));
			return coordinatesToBeChecked;
		}
		return null;
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
		// first we assume that we stay in one horizontal row
		int newX = startPoint.x + 2;
		int newY = startPoint.y;
		if (newX > 9) { // this is true if startPoint.x was 8 or 9 so we actually have to move to next
						// line
			newX = newX % 10;
			newY = (newY + 1) % 10; // if startPoint.y was 9, we move to first row
		}
		return new Coordinate(newX, newY);
	}

}
