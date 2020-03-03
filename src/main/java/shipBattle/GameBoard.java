package shipBattle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		ships.add(new Ship("Cruiser", 4));
		ships.add(new Ship("Submarine", 4));
		ships.add(new Ship("Destroyer", 4));
	}

	public Square[][] getSquares() {
		return squares;
	}

	public List<Ship> getShips() {
		return ships;
	}

	public boolean areTheOtherShipsFarEnough(List<Coordinate> suggestedLocationForNewShip) {
		List<Coordinate> coordinatesToBeChecked = GameBoardHelper
				.getTheCoordinatesSurrounding(suggestedLocationForNewShip);
		for (Coordinate coordinate : coordinatesToBeChecked) {
			if (isThereAShipIn(coordinate)) {
				return false;
			}
		}
		return true;
	}

	public void markTheSquaresArroundASunkenShip(Coordinate hitPoint) {
		Ship sunkenShip = fromCoordinateToShip(hitPoint);
		List<Coordinate> coordinates = GameBoardHelper.getTheCoordinatesSurrounding(sunkenShip.getLocation());
		for (Coordinate coordinate : coordinates) {
			toSquare(coordinate).setShot(true);
		}
	}

	public int getTheNumberOfUnhitSquares(Coordinate startPoint, Direction direction) {
		try {
			Coordinate nextCoordinate = CoordinateHelper.getTheNextCoordinate(startPoint, direction);
			if (toSquare(nextCoordinate).isShot()) {
				return 0;
			}
			return 1 + getTheNumberOfUnhitSquares(nextCoordinate, direction);
		} catch (cantMoveInThatDirectionException e) {
			return 0;
		}
	}
	
	public Coordinate checkEveryOtherSquareFromStartPointUntilXUnhitSquaresAreFound(Coordinate startPoint,
			int x) {
		int maxNumberOfSquaresToBeChecked = 10 * 10 / 2;
		for (int i = 0; i < maxNumberOfSquaresToBeChecked; i++) {
			Coordinate coordinate = CoordinateHelper.skipOverOneCoordinateAndGetTheNext(startPoint);
			if (GameBoardHelper.areThereXUnhitSquaresSurrounding(this, coordinate, x))
				return coordinate;
		}
		throw new CouldNotFindXUnhitSquaresException();
	}

	

	public void saveTheShipsLocationOnBoard(List<Coordinate> location, Ship ship) {
		for (Coordinate coord : location) {
			squares[coord.y][coord.x].setShip(ship);
		}
	}

	public char[][] buildTheViewOfBoard(boolean gameHasStarted) {
		char[][] board = new char[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				board[i][j] = getTheRightChar(gameHasStarted, i, j);
			}
		}
		return board;
	}

	// If game has started we show (from the enemies board) only the squares that
	// have been shot at.
	// If we are entering the ships we show (from our own board) all the squares.
	private char getTheRightChar(boolean gameHasStarted, int i, int j) {
		if (gameHasStarted) {
			if (squares[i][j].isShot()) {
				return getChar(i, j);
			}
			return ' ';
		}
		return getChar(i, j);
	}

	private char getChar(int i, int j) {
		if (squares[i][j].getShip() == null) {
			return '-';
		}
		return 'X';
	}

	public void printGameBoard(char[][] board) {
		System.out.println("  A B C D E F G H I J");
		for (int i = 0; i < 10; i++) {
			System.out.print(i);
			for (int j = 0; j < 10; j++) {
				System.out.print(" " + board[i][j]);
			}
			System.out.println("");
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
			if (!toSquare(coordinate).isShot())
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

}
