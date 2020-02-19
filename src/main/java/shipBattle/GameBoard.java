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
		for(Coordinate coord : location) {
			squares[coord.y][coord.x].setShip(ship);
		}
		
	}

}
