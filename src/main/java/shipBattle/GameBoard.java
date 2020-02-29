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

	public boolean areTheOtherShipsTooClose(List<Coordinate> suggestedLocationForNewShip) {
		for (Coordinate coordinate : suggestedLocationForNewShip) {
			if (!surroundingSquaresAreFreeOfShips(coordinate))
				return false;
		}
		return true;
	}
	
	private boolean surroundingSquaresAreFreeOfShips(Coordinate middlePoint) {
		List<Direction> directions = Arrays.asList(Direction.values());
		for (Direction direction : directions) {
			if (!nextSquareIsFreeOfShips(middlePoint, direction))
				return false;
		}
		return true;
	}

	private boolean nextSquareIsFreeOfShips(Coordinate middlePoint, Direction direction) {
		try {
			Coordinate coordinate = CoordinateHelper.getTheNextCoordinate(middlePoint, direction);
			return toSquare(coordinate).getShip() == null;
		} catch (cantMoveInThatDirectionException e) {
			return true;
		}
	}
	
	public void markTheSquaresArroundASunkenShip(Coordinate hitPoint) {
		Ship sunkenShip = fromCoordinateToShip(hitPoint);
		
		
	}
	
	public Coordinate checkEveryOtherSquareFromStartPointUntilXUnhitSquaresAreFound(Coordinate startPoint, int x) {
		int maxNumberOfSquaresToBeChecked = BOARD_SIZE * BOARD_SIZE / 2;
		for (int i = 0; i < maxNumberOfSquaresToBeChecked; i++) {
			Coordinate coordinate = CoordinateHelper.skipOverOneCoordinateAndGetTheNext(startPoint);
			if(areThereXUnhitSquaresSurrounding(coordinate, x))
				return coordinate;
		}
		throw new CouldNotFindXUnhitSquaresException();	
	}
	
	private boolean areThereXUnhitSquaresSurrounding(Coordinate coordinate, int x) {
		if(x==12)
			return areThere12UnhitSquaresSurrounding(coordinate);
		if(x==4)
			return areThere4UnhitSquaresSurrounding(coordinate);
		if(x==1)
			return toSquare(coordinate).isShot();
		return false;
	}
	
	private boolean areThere12UnhitSquaresSurrounding(Coordinate middlePoint) {
		if(!areThere4UnhitSquaresSurrounding(middlePoint))
			return false;
		List<Direction> directions = Arrays.asList(Direction.values());
		for(Direction direction : directions) {
			if(!areTheSquaresUnhitInDirection(direction, middlePoint))
				return false;
		}
		return true;
	}
	
	private boolean areTheSquaresUnhitInDirection(Direction direction, Coordinate middlePoint) {
		try {
			Coordinate nextCoordinate = CoordinateHelper.getTheNextCoordinate(middlePoint, direction);
			return areThere4UnhitSquaresSurrounding(nextCoordinate);
		} catch (cantMoveInThatDirectionException e) {
			return true;
		}
	}

	private boolean areThere4UnhitSquaresSurrounding(Coordinate middlePoint) {
		List<Direction> directions = Arrays.asList(Direction.values());
		for(Direction direction : directions) {
			if(nextSquareIsShotAt(middlePoint, direction))
				return false;
		}
		return true;
	}
	
	private boolean nextSquareIsShotAt(Coordinate middlePoint, Direction direction) {
		try {
			Coordinate coordinate = CoordinateHelper.getTheNextCoordinate(middlePoint, direction);
			return toSquare(coordinate).isShot();
		} catch (cantMoveInThatDirectionException e) {
			return false;
		}
	}

	public int getTheNumberOfUnhitSquares(Coordinate startPoint, Direction direction) {
		try {
			Coordinate nextCoordinate = CoordinateHelper.getTheNextCoordinate(startPoint, direction);
			return 1 + getTheNumberOfUnhitSquares(nextCoordinate, direction);
		} catch (cantMoveInThatDirectionException e) {
			return 0;
		}
	}

//	public void saveTheLocationOnBoard(List<Coordinate> location, Ship ship) {
//		letTheSquaresKnowThatThereIsAShip(location, ship);
//		letTheShipKnowWhereItIs(location, ship);
//	}

//	private void letTheShipKnowWhereItIs(List<Coordinate> location, Ship ship) {
//		List<Square> locationInSquares = new ArrayList<>();
//		for (Coordinate coordinate : location) {
//			Square square = toSquare(coordinate);
//			locationInSquares.add(square);
//		}
//		ship.setLocation(locationInSquares);
//	}

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

	private Square toSquare(Coordinate coordinate) {
		return squares[coordinate.y][coordinate.x];
	}

	

	

}
