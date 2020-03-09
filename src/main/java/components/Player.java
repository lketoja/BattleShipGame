package components;

import java.io.Serializable;

import exceptions.SubclassDidNotImplementException;
import helpers.Coordinate;
import helpers.UserInterface;
import logic.GameState;

public class Player implements Serializable {

	protected String name;
	protected GameBoard enemyShips;

	public void playTurn(GameState gameState, UserInterface UI) {
		printDirectionsForTurn();
		printGameboardBeforeTheShot();
		Coordinate missileCoordinate = shoot(gameState, UI);
		enemyShips.markTheSquareThatWasHit(missileCoordinate);
		printGameboardAfterTheShot();
		if (enemyShips.isThereAShipIn(missileCoordinate)) {
			handleHit(gameState, missileCoordinate);
		} else {
			System.out.println(name + " didn't hit a ship.");
			updateGameStateAfterMissileDidntHit(gameState, missileCoordinate);
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

	protected void handleHit(GameState gameState, Coordinate missileCoordinate) {
		if (enemyShips.didTheShipSink(missileCoordinate)) {
			handleSunkenShip(gameState, missileCoordinate);
		} else {
			System.out.println(name + " hit a ship! You get to shoot again!");
			updateGameStateAfterAHit(gameState, missileCoordinate);
		}
	}

	// for ComputerPlayer to override
	protected void updateGameStateAfterAHit(GameState gameState, Coordinate missileCoordinate) {	}

	// for ComputerPlayer to override
	protected void updateGameStateAfterMissileDidntHit(GameState gameState, Coordinate missileCoordinate) {	}

	protected void handleSunkenShip(GameState gameState, Coordinate missileCoordinate) {
		enemyShips.markSunkenShip(missileCoordinate);
		enemyShips.markTheSquaresArroundASunkenShip(missileCoordinate);
		if (enemyShips.areAllTheShipsSunken()) {
			System.out.println("All the ships are sunken! " + name + " won!");
			System.exit(0);
		} else {
			System.out.println(name + " hit a ship and the ship sank! " + name + " get to shoot again!");
			updateGameStateAfterSunkenShip(gameState);
		}
	}

	// for ComputerPlayer to override
	protected void updateGameStateAfterSunkenShip(GameState gameState) {
	}

	protected Coordinate shoot(GameState gameState, UserInterface UI) {
		throw new SubclassDidNotImplementException();
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
}
