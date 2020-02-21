package shipBattle;

import java.util.ArrayList;
import java.util.List;

public class ShipMaker {

	protected GameBoard gameBoard;

	public ShipMaker() {
		super();
		gameBoard = new GameBoard();
	}

	public GameBoard prepareShips() {
		for (Ship ship : gameBoard.getShips()) {
			gameBoard.printGameBoard(gameBoard.buildTheViewOfBoard(false));
			List<Coordinate> location = getAndValidateLocation(ship);
			gameBoard.saveTheLocationOnBoard(location, ship);
		}
		return gameBoard;
	}

	private List<Coordinate> getAndValidateLocation(Ship ship) {
		List<Coordinate> location;
		boolean validLocation;
		do {
			location = getTheLocationFor(ship);
			validLocation = gameBoard.areTheOtherShipsTooClose(location);
			if (!validLocation)
				printMessageOtherShipsTooClose();
		} while (!validLocation);
		return location;
	}

	private List<Coordinate> getTheLocationFor(Ship ship) {
		try {
			return chooseLocation(ship);
		} catch (ShipOutOfBoardException e) {
			printMessageShipOutOfBoard();
			return getTheLocationFor(ship);
		}
	}

	private List<Coordinate> chooseLocation(Ship ship) throws ShipOutOfBoardException {
		Coordinate coordinate = getTheFirstCoordinate(ship);
		String direction = getTheDirection(ship);
		if (isOutOfBoard(coordinate, direction, ship.length))
			throw new ShipOutOfBoardException();
		List<Coordinate> location = generateCoordinates(coordinate, direction, ship.length);
		System.out.println(location);
		return location;
	}

	protected void printMessageOtherShipsTooClose() {
	}

	protected void printMessageShipOutOfBoard() {
	}

	protected Coordinate getTheFirstCoordinate(Ship ship) {
		throw new SubclassDidNotImplementException();
	}

	protected String getTheDirection(Ship ship) {
		throw new SubclassDidNotImplementException();
	}

	private boolean isOutOfBoard(Coordinate coordinate, String direction, int length) {
		if (direction.equals("down")) {
			return lastXOrYIsOutOfBoard(coordinate.y, length);
		}
		if (direction.equals("right")) {
			return lastXOrYIsOutOfBoard(coordinate.x, length);
		}
		return false;
	}

	private boolean lastXOrYIsOutOfBoard(int firstXorY, int length) {
		int lastXorY = firstXorY + (length - 1);
		return lastXorY > 9;
	}

	private List<Coordinate> generateCoordinates(Coordinate coordinate, String direction, int length) {
		List<Coordinate> coordinates = new ArrayList<>();
		int x = coordinate.x;
		int y = coordinate.y;
		int addToX = 0, addToY = 0;
		if (direction.equals("down"))
			addToY = 1;
		if (direction.equals("right"))
			addToX = 1;
		for (int i = 0; i < length; i++) {
			coordinates.add(new Coordinate(x + i * addToX, y + i * addToY));
		}
		return coordinates;
	}

}
