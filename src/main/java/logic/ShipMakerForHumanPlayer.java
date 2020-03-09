package logic;

import java.util.ArrayList;
import java.util.List;

import components.Player;
import components.Ship;
import helpers.Coordinate;
import helpers.UserInterface;

public class ShipMakerForHumanPlayer extends ShipMaker {
	
	private UserInterface UI;

	public ShipMakerForHumanPlayer(UserInterface UI) {
		super();
		this.UI = UI;
	}
	
	@Override
	protected void printDirectionsForEnteringShips(Player player) {
		System.out.println("Now it's time for " + player.getName() + " to enter locations for the ships");
		System.out.println("The ships can touch each other only by corners. (They can't be right next to each other.)");
		System.out.println("For each ship you will enter the coordinate of the first square (e.g. 'd6') and then the direction");
		System.out.println("The direction is ether 'down' or 'right'");
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
