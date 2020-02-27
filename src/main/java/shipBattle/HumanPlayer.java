package shipBattle;

public class HumanPlayer extends Player {

	public HumanPlayer(GameState gameState) {
		super(gameState);
	}

	@Override
	protected void printGameboardBeforeTheShot() {
		enemyShips.printGameBoard(enemyShips.buildTheViewOfBoard(true));
	}

	@Override
	protected void printDirectionsForTurn() {
		System.out.println("It's " + name + "'s turn.");
	}

	@Override
	protected Coordinate shoot(UserInterface UI) {
		return UI.askForTheCoordinateOfTheMissle();
	}

}
