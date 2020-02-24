package shipBattle;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

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
		ships.add(new Ship("lentotukialus", 5));
		ships.add(new Ship("taistelulaiva", 4));
		ships.add(new Ship("taistelulaiva", 4));
		ships.add(new Ship("risteilija", 3));
		ships.add(new Ship("risteilija", 3));
		ships.add(new Ship("havittaja", 2));
		ships.add(new Ship("havittaja", 2));
	}

	public Square[][] getSquares() {
		return squares;
	}

	public List<Ship> getShips() {
		return ships;
	}

	public boolean areTheOtherShipsTooClose(List<Coordinate> location) {
		for (Coordinate coordinate : location) {
			int i = coordinate.y;
			int j = coordinate.x;
			if (squares[i][j].getShip() != null || !isThereSpaceUp(i, j) || !isThereSpaceDown(i, j)
					|| !isThereSpaceLeft(i, j) || !isThereSpaceRight(i, j))
				return false;
		}
		return true;
	}

	private boolean isThereSpaceUp(int i, int j) {
		if (i == 0)
			return true;
		return squares[i - 1][j].getShip() == null;
	}

	private boolean isThereSpaceDown(int i, int j) {
		if (i == 9)
			return true;
		return squares[i + 1][j].getShip() == null;
	}

	private boolean isThereSpaceLeft(int i, int j) {
		if (j == 0)
			return true;
		return squares[i][j - 1].getShip() == null;
	}

	private boolean isThereSpaceRight(int i, int j) {
		if (j == 9)
			return true;
		return squares[i][j + 1].getShip() == null;
	}

	public void saveTheLocationOnBoard(List<Coordinate> location, Ship ship) {
		letTheSquaresKnowThatThereIsAShip(location, ship);
		letTheShipKnowWhereItIs(location, ship);
	}
	
	private void letTheShipKnowWhereItIs(List<Coordinate> location, Ship ship) {
		List<Square> locationInSquares = new ArrayList<>();
		for(Coordinate coordinate : location) {
			Square square = fromCoordinateToSquare(coordinate);
			locationInSquares.add(square);
		}
		ship.setLocation(locationInSquares);
	}

	private void letTheSquaresKnowThatThereIsAShip(List<Coordinate> location, Ship ship) {
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

	//If game has started we show (from the enemies board) only the squares that have been shot at.
	//If we are entering the ships we show (from our own board) all the squares.
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
	
	public void markThatSquareWasHit(Coordinate coordinate) {
		Square hitLocation = fromCoordinateToSquare(coordinate);
		hitLocation.setShot(true);
	}

	public boolean isThereAShipIn(Coordinate coordinate) {
		Square hitLocation = fromCoordinateToSquare(coordinate);
		if(hitLocation.getShip() == null)
			return false;
		return true;
	}
	
	public boolean didTheShipSink(Coordinate coordinate) {
		Ship ship = fromCoordinateToShip(coordinate);
		for(Square square : ship.getLocation()) {
			if(!square.isShot())
				return false;
		}
		return true;
	}

	public boolean areAllTheShipsSunken() {
		for(Ship ship : ships) {
			if(!ship.sunken)
				return false;
		}
		return true;
	}

	public void markSunkenShip(Coordinate coordinate) {
		Ship ship = fromCoordinateToShip(coordinate);
		ship.setSunken(true);
	}
	
	private Ship fromCoordinateToShip(Coordinate coordinate) {
		Square square = fromCoordinateToSquare(coordinate);
		return square.getShip();
	}
	
	private Square fromCoordinateToSquare(Coordinate coordinate) {
		return squares[coordinate.y][coordinate.x];
	}

}
