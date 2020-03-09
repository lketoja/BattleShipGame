package logic;

import java.util.ArrayList;
import java.util.List;

import components.GameBoard;
import components.Player;
import components.Ship;
import exceptions.ShipOutOfBoardException;
import exceptions.SubclassDidNotImplementException;
import helpers.Coordinate;
import helpers.CoordinateHelper;
import helpers.Direction;

public class ShipMaker {

	protected GameBoard gameBoard;
	

	public ShipMaker() {
		super();
		gameBoard = new GameBoard();
	}

	public GameBoard prepareShips(Player player) {
		printDirectionsForEnteringShips(player);
		gameBoard.printGameBoard(gameBoard.buildTheViewOfBoard(false));
		for (Ship ship : gameBoard.getShips()) {
			List<Coordinate> location = getAndValidateLocation(ship);
			gameBoard.saveTheShipsLocationOnBoard(location, ship);
			ship.setLocation(location);
			gameBoard.printGameBoard(gameBoard.buildTheViewOfBoard(false));
		}
		return gameBoard;
	}

	protected void printDirectionsForEnteringShips(Player player) {}

	private List<Coordinate> getAndValidateLocation(Ship ship) {
		List<Coordinate> suggestedLocationForNewShip;
		boolean validLocation;
		do {
			suggestedLocationForNewShip = getTheLocationFor(ship);
			validLocation = gameBoard.areTheOtherShipsFarEnough(suggestedLocationForNewShip);
			if (!validLocation)
				printMessageOtherShipsTooClose();
		} while (!validLocation);
		return suggestedLocationForNewShip;
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
		Direction direction = getTheDirection(ship);
		if (isOutOfBoard(coordinate, direction, ship.length))
			throw new ShipOutOfBoardException();
		List<Coordinate> location = CoordinateHelper.generateCoordinates(coordinate, direction, ship.length);
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

	protected Direction getTheDirection(Ship ship) {
		throw new SubclassDidNotImplementException();
	}

	private boolean isOutOfBoard(Coordinate coordinate, Direction direction, int length) {
		if (direction == Direction.DOWN) {
			return lastXOrYIsOutOfBoard(coordinate.y, length);
		}
		if (direction == Direction.UP) {
			return lastXOrYIsOutOfBoard(coordinate.x, length);
		}
		return false;
	}

	private boolean lastXOrYIsOutOfBoard(int firstXorY, int length) {
		int lastXorY = firstXorY + (length - 1);
		return lastXorY > 9;
	}

}
