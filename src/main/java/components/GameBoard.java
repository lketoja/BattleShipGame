package components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.CouldNotFindXUnhitSquaresException;
import exceptions.CantMoveInThatDirectionException;
import helpers.Coordinate;
import helpers.CoordinateHelper;
import helpers.Direction;
import helpers.GameBoardHelper;

public class GameBoard implements Serializable {

	public final int BOARD_SIZE = 10;
	private Square[][] squares;
	private List<Ship> ships;

	public GameBoard() {
		super();
		squares = new Square[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				squares[i][j] = new Square();
			}
		}
		ships = new ArrayList<Ship>();
		ships.add(new Ship("Carrier", 5));
		ships.add(new Ship("Battleship", 4));
//		ships.add(new Ship("Cruiser", 4));
//		ships.add(new Ship("Submarine", 4));
//		ships.add(new Ship("Destroyer", 4));
	}

	public Square[][] getSquares() {
		return squares;
	}

	public List<Ship> getShips() {
		return ships;
	}

	public void markTheSquaresArroundASunkenShip(Coordinate hitPoint) {
		Ship sunkenShip = fromCoordinateToShip(hitPoint);
		List<Coordinate> coordinates = CoordinateHelper.getTheCoordinatesSurrounding(sunkenShip.getLocation());
		for (Coordinate coordinate : coordinates) {
			toSquare(coordinate).setShot(true);
		}
	}

	public boolean areAllTheCoordinatesUnhit(List<Coordinate> coordinatesToBeChecked) {
		for (Coordinate coordinate : coordinatesToBeChecked) {
			if (isShot(coordinate))
				return false;
		}
		return true;
	}

	public void saveTheShipsLocationOnBoard(List<Coordinate> location, Ship ship) {
		for (Coordinate coord : location) {
			squares[coord.y][coord.x].setShip(ship);
		}
	}

	public void markTheSquareThatWasHit(Coordinate coordinate) {
		Square hitLocation = toSquare(coordinate);
		hitLocation.setShot(true);
	}

	public boolean isThereAShipIn(Coordinate coordinate) {
		Square hitLocation = toSquare(coordinate);
		if (hitLocation.getShip() == null)
			return false;
		return true;
	}

	public boolean didTheShipSink(Coordinate missileCoordinate) {
		Ship ship = fromCoordinateToShip(missileCoordinate);
		for (Coordinate coordinate : ship.getLocation()) {
			if (!isShot(coordinate))
				return false;
		}
		return true;
	}

	public boolean areAllTheShipsSunken() {
		for (Ship ship : ships) {
			if (!ship.sunken)
				return false;
		}
		return true;
	}

	public void markSunkenShip(Coordinate coordinate) {
		Ship ship = fromCoordinateToShip(coordinate);
		ship.setSunken(true);
	}

	private Ship fromCoordinateToShip(Coordinate coordinate) {
		Square square = toSquare(coordinate);
		return square.getShip();
	}

	public Square toSquare(Coordinate coordinate) {
		return squares[coordinate.y][coordinate.x];
	}

	public boolean isShot(Coordinate coordinate) {
		return toSquare(coordinate).isShot();
	}

}
