package components;

import helpers.Coordinate;
import helpers.UserInterface;
import logic.GameState;

public class HumanPlayer extends Player {

	@Override
	protected void printGameboardBeforeTheShot() {
		enemyShips.printGameBoard(enemyShips.buildTheViewOfBoard(true));
	}

	@Override
	protected void printDirectionsForTurn() {
		System.out.println("It's " + name + "'s turn.");
	}

	@Override
	protected Coordinate shoot(GameState notNeeded, UserInterface UI) {
		return UI.askForTheCoordinateOfTheMissle();
	}

}
