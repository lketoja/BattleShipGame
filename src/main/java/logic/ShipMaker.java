package logic;

import java.util.ArrayList;
import java.util.List;

import components.GameBoard;
import components.Ship;
import exceptions.ShipOutOfBoardException;
import exceptions.SubclassDidNotImplementException;
import helpers.Coordinate;
import helpers.CoordinateHelper;
import helpers.Direction;
import helpers.GameBoardHelper;

public class ShipMaker {

	protected GameBoard gameBoard;
	

	public ShipMaker() {
		super();
		gameBoard = new GameBoard();
	}

	public GameBoard prepareShips(Player player) {
		printDirectionsForEnteringShips(player);
		GameBoardHelper.renderGameBoard(false, gameBoard);
		for (Ship ship : gameBoard.getShips()) {
			List<Coordinate> location = getAndValidateLocation(ship);
			gameBoard.saveTheShipsLocationOnBoard(location, ship);
			ship.setLocation(location);
			GameBoardHelper.renderGameBoard(false, gameBoard);
		}
		return gameBoard;
	}

	protected void printDirectionsForEnteringShips(Player player) {}

	private List<Coordinate> getAndValidateLocation(Ship ship) {
		List<Coordinate> suggestedLocationForNewShip;
		boolean validLocation;
		do {
			suggestedLocationForNewShip = getTheLocationFor(ship);
			validLocation = GameBoardHelper.areTheOtherShipsFarEnough(gameBoard, suggestedLocationForNewShip);
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
		List<Coordinate> location = CoordinateHelper.generateCoordinates(coordinate, direction, ship.length);
		if (CoordinateHelper.isOutOfBoard(location))
			throw new ShipOutOfBoardException();
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

}
