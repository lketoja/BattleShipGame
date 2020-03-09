package logic;

import components.GameState;
import helpers.Coordinate;
import helpers.GameBoardHelper;
import helpers.UserInterface;

public class HumanPlayer extends Player {

	@Override
	protected void printGameboardBeforeTheShot() {
		GameBoardHelper.renderGameBoard(false, enemyShips);
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
