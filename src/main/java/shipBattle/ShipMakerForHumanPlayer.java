package shipBattle;

import java.util.ArrayList;
import java.util.List;

public class ShipMakerForHumanPlayer extends ShipMaker{

	public ShipMakerForHumanPlayer(UserInterface uI) {
		super(uI);
	}
	
	@Override
	public GameBoard prepareShips() {
		for(Ship ship : gameBoard.getShips()) {
			List<Coordinate> location = getTheLocationFor(ship);
			boolean validLocation = gameBoard.areTheOtherShipsTooClose(location);
			if(!validLocation)
				printMessageOtherShipsTooClose();
				getTheLocationFor(ship);
			gameBoard.saveTheLocationOnBoard(location, ship);
		}
		return gameBoard;
	}

	private List<Coordinate> getTheLocationFor(Ship ship) {
		try {
			return chooseLocation(ship);
		} catch (ShipOutOfBoardException e) {
			System.out.println("Error! Ship is out of board! Choose the first coordinate and "
					+ "direction so that the ship will fit on board.");
			return getTheLocationFor(ship);
		}
	}

	private List<Coordinate> chooseLocation(Ship ship) throws ShipOutOfBoardException {
		Coordinate coordinate = UI.askForFirstCoordinate(ship);
		String direction = UI.askForDirection(ship);
		if(isOutOfBoard(coordinate, direction, ship.length)) 
			throw new ShipOutOfBoardException();
		List<Coordinate> location = generateCoordinates(coordinate, direction, ship.length);
		System.out.println(location);
		return location;
	}

	private boolean isOutOfBoard(Coordinate coordinate, String direction, int length) {
		if(direction.equals("down")) {
			return lastXOrYIsOutOfBoard(coordinate.y, length);
		}
		if(direction.equals("right")) {
			return lastXOrYIsOutOfBoard(coordinate.x, length);
		}
		return false;
	}
	
	private boolean lastXOrYIsOutOfBoard(int firstXorY, int length) {
		int lastXorY = firstXorY + (length - 1);
		return lastXorY > 9;
	}
	
	private void printMessageOtherShipsTooClose() {
		System.out.println("Error! Ship is too close to other ships! Ships can touch each other only by"
				+ "corners. Ships can't be right next to each other!");
	}
	
	private List<Coordinate> generateCoordinates(Coordinate coordinate, String direction, int length) {
		List<Coordinate> coordinates = new ArrayList<>();
		int x = coordinate.x;
		int y = coordinate.y;
		int addToX = 0, addToY = 0;
		if(direction.equals("down"))
			addToY = 1;
		if(direction.equals("right"))
			addToX = 1;
		for(int i = 0; i<length; i++) {
			coordinates.add(new Coordinate(x + i*addToX, y + i*addToY));
		}
		return coordinates;
	}


}
