package shipBattle;

import java.util.ArrayList;
import java.util.List;

public class ShipMakerForHumanPlayer extends ShipMaker {
	
	private UserInterface UI;

	public ShipMakerForHumanPlayer(UserInterface UI) {
		super();
		this.UI = UI;
	}

	@Override
	protected void printMessageOtherShipsTooClose() {
		System.out.println("Error! Ship is too close to other ships! Ships can touch each other only by"
				+ "corners. Ships can't be right next to each other!");
	}

	@Override
	protected void printMessageShipOutOfBoard() {
		System.out.println("Error! Ship is out of board! Choose the first coordinate and "
				+ "direction so that the ship will fit on board.");
	}
	
	@Override
	protected Coordinate getTheFirstCoordinate(Ship ship) {
		return UI.askForFirstCoordinate(ship);
	}

	@Override
	protected String getTheDirection(Ship ship) {
		return UI.askForDirection(ship);
	}
}
