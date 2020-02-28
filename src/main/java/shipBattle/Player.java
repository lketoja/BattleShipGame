package shipBattle;

import java.io.Serializable;

public class Player implements Serializable {

	protected String name;
	protected GameBoard enemyShips;
	protected GameState gameState;

	public Player(GameState gameState) {
		this.gameState = gameState;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GameBoard getGameBoard() {
		return enemyShips;
	}

	public void setGameBoard(GameBoard gameBoard) {
		this.enemyShips = gameBoard;
	}

	public void playTurn(GameState gameState, UserInterface UI) {
		printDirectionsForTurn();
		printGameboardBeforeTheShot();
		Coordinate missileCoordinate = shoot(UI);
		enemyShips.markTheSquareThatWasHit(missileCoordinate);
		printGameboardAfterTheShot();
		if (enemyShips.isThereAShipIn(missileCoordinate)) {
			handleHit(missileCoordinate);
		} else {
			System.out.println(name + " didn't hit a ship.");
			updateGameStateAfterMissileDidntHit(missileCoordinate);
			gameState.changePlayerInTurn();
		}
		UI.waitForPlayerToHitEnter();
	}

	// for HumanPlayer to override
	protected void printGameboardBeforeTheShot() {	}

	protected void printGameboardAfterTheShot() {
		enemyShips.printGameBoard(enemyShips.buildTheViewOfBoard(true));
	}

	// for HumanPlayer to override
	protected void printDirectionsForTurn() {	}

	protected void handleHit(Coordinate missileCoordinate) {
		if (enemyShips.didTheShipSink(missileCoordinate)) {
			handleSunkenShip(missileCoordinate);
		} else {
			System.out.println(name + " hit a ship! You get to shoot again!");
			updateGameStateAfterAHit(missileCoordinate);
		}
	}

	// for ComputerPlayer to override
	protected void updateGameStateAfterAHit(Coordinate missileCoordinate) {	}

	// for ComputerPlayer to override
	protected void updateGameStateAfterMissileDidntHit(Coordinate missileCoordinate) {	}

	protected void handleSunkenShip(Coordinate missileCoordinate) {
		enemyShips.markSunkenShip(missileCoordinate);
		if (enemyShips.areAllTheShipsSunken()) {
			System.out.println("All the ships are sunken! " + name + " won!");
			System.exit(0);
		} else {
			System.out.println(name + " hit a ship and the ship sank! " + name + " get to shoot again!");
			updateGameStateAfterSunkenShip();
		}
	}

	// for ComputerPlayer to override
	protected void updateGameStateAfterSunkenShip() {
	}

	protected Coordinate shoot(UserInterface UI) {
		throw new SubclassDidNotImplementException();
	}

}
