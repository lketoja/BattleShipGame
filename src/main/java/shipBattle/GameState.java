package shipBattle;

import java.io.Serializable;
import java.util.Random;

public class GameState implements Serializable{
	
	private int playerInTurn;
	//The shortest is is of length 2 so we only shoot at every other square.
	private int shootingOptimizerForComputerPlayer = new Random().nextInt(2);
	private int theNumberOfTimesComputerHasHitAShip;
	private Coordinate firstHit;
	private Coordinate lastHit;
	private Direction direction;
	private boolean lookForSquareThatIsSurroundedBy12UnhitSquares = true;
	private boolean lookForSquareThatIsSurrounedBy4UnhitSquares = true;
	
	public boolean isLookForSquareThatIsSurroundedBy12UnhitSquares() {
		return lookForSquareThatIsSurroundedBy12UnhitSquares;
	}

	public boolean isLookForSquareThatIsSurroundedBy4UnhitSquares() {
		return lookForSquareThatIsSurrounedBy4UnhitSquares;
	}

	public void setLookForSquareThatIsSurroundedBy12UnhitSquares(boolean lookForSquareThatIsSurroundedBy12UnhitSquares) {
		this.lookForSquareThatIsSurroundedBy12UnhitSquares = lookForSquareThatIsSurroundedBy12UnhitSquares;
	}

	public void setLookForSquareThatIsSurroundedBy4UnhitSquares(boolean lookForSquareThatIsSorrounedBy4UnhitSquares) {
		this.lookForSquareThatIsSurrounedBy4UnhitSquares = lookForSquareThatIsSorrounedBy4UnhitSquares;
	}

	public int getTheNumberOfTimesComputerHasHitAShip() {
		return theNumberOfTimesComputerHasHitAShip;
	}

	public void setTheNumberOfTimesComputerHasHitAShip(int theNumberOfTimesComputerHasHitAShip) {
		this.theNumberOfTimesComputerHasHitAShip = theNumberOfTimesComputerHasHitAShip;
	}

	public Coordinate getFirstHit() {
		return firstHit;
	}

	public void setFirstHit(Coordinate firstHit) {
		this.firstHit = firstHit;
	}

	public int getShootingOptimizerForComputerPlayer() {
		return shootingOptimizerForComputerPlayer;
	}

	public Coordinate getLastHit() {
		return lastHit;
	}

	public void setLastHit(Coordinate secondHit) {
		this.lastHit = secondHit;
	}

	public void setPlayerInTurn(int playerInTurn) {
		this.playerInTurn = playerInTurn;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getPlayerInTurn() {
		return playerInTurn;
	}

	public void changePlayerInTurn() {
		this.playerInTurn = (playerInTurn + 1) % 2;
	}

	public void incrementTheNuberOfTimesComputerHasHitAShip() {
		theNumberOfTimesComputerHasHitAShip += 1;
	}

	
}
