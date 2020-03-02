package shipBattle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ComputerPlayer extends Player {

	private Random random;

	public ComputerPlayer(GameState gameState) {
		super(gameState);
		name = "Computer";
		random = new Random();
	}

	@Override
	protected Coordinate shoot(UserInterface UI) {
		if (gameState.getTheNumberOfTimesComputerHasHitAShip() > 0) {
			return continueShinkingTheShip();
		} else {
			return shootRandomly();
		}
	}

	//Shooting is optimized by first looking a larger area that hasn't been shot at yet.
	//First we look for squares that are surrounded by 12 unhit squares (in a shape of diamond).
	//If none is found, we move to look for squares that are surrounded by 4 unhit squares.
	private Coordinate shootRandomly() {
		Coordinate startPoint = drawRandomCoordinate();
		return lookFor12UnhitSquares(startPoint);
	}

	private Coordinate lookFor12UnhitSquares(Coordinate startPoint) {
		try {
			//If we have checked before that there is no such square left, we move on.
			if (!gameState.isLookForSquareThatIsSurroundedBy12UnhitSquares())
				throw new CouldNotFindXUnhitSquaresException();
			return enemyShips.checkEveryOtherSquareFromStartPointUntilXUnhitSquaresAreFound(startPoint, 12);
		}catch (CouldNotFindXUnhitSquaresException e) {
			gameState.setLookForSquareThatIsSurroundedBy12UnhitSquares(false);
			return lookFor4UnhitSquares(startPoint);
		}
	
	}

	private Coordinate lookFor4UnhitSquares(Coordinate startPoint) {
		try {
			//If we have checked before that there is no such square left, we move on.
			if (!gameState.isLookForSquareThatIsSurroundedBy4UnhitSquares())
				throw new CouldNotFindXUnhitSquaresException();
			return enemyShips.checkEveryOtherSquareFromStartPointUntilXUnhitSquaresAreFound(startPoint, 4);
		} catch (CouldNotFindXUnhitSquaresException e) {
			gameState.setLookForSquareThatIsSurroundedBy4UnhitSquares(false);
			return enemyShips.checkEveryOtherSquareFromStartPointUntilXUnhitSquaresAreFound(startPoint, 1);
		}
	}

	private Coordinate drawRandomCoordinate() {
		Coordinate coordinate = new Coordinate(random.nextInt(10), random.nextInt(10));
		if ((coordinate.x + coordinate.y) % 2 == gameState.getShootingOptimizerForComputerPlayer()) {
			return coordinate;
		} else {
			return drawRandomCoordinate();
		}
	}
	
	private Coordinate continueShinkingTheShip() {
		if (gameState.getTheNumberOfTimesComputerHasHitAShip() > 1) {
			return continueToShootAlongTheKnownDirection(gameState.getLastHit(), gameState.getDirection());
		} else {
			return shootToTheBestDirectionFromTheFirstHit();
		}
	}

	private Coordinate continueToShootAlongTheKnownDirection(Coordinate lastHit, Direction direction) {
		return CoordinateHelper.getTheNextCoordinate(lastHit, direction);
	}

	private Coordinate shootToTheBestDirectionFromTheFirstHit() {
		Direction bestDirection = whichDirectionHasTheBiggestNumberOfEmptySquares(gameState.getFirstHit());
		gameState.setDirection(bestDirection);
		return continueToShootAlongTheKnownDirection(gameState.getFirstHit(), bestDirection);
	}

	private Direction whichDirectionHasTheBiggestNumberOfEmptySquares(Coordinate startPoint) {
		Map<Direction, Integer> emptySquaresInEachDirection = new HashMap<>();
		List<Direction> directions = Arrays.asList(Direction.values());
		for (Direction direction : directions) {
			int numberOfUnhitSquares = enemyShips.getTheNumberOfUnhitSquares(startPoint, direction);
			emptySquaresInEachDirection.put(direction, numberOfUnhitSquares);
		}
		// sort the hashmap
		return Direction.UP;
	}

	@Override
	protected void updateGameStateAfterAHit(Coordinate missileCoordinate) {
		if (gameState.getTheNumberOfTimesComputerHasHitAShip() == 0)
			gameState.setFirstHit(missileCoordinate);
		gameState.incrementTheNuberOfTimesComputerHasHitAShip();
		gameState.setFirstHit(missileCoordinate);
	}

	@Override
	protected void updateGameStateAfterSunkenShip() {
		gameState.setTheNumberOfTimesComputerHasHitAShip(0);
		gameState.setFirstHit(null);
		gameState.setLastHit(null);
		gameState.setDirection(null);
	}

	@Override
	protected void updateGameStateAfterMissileDidntHit(Coordinate missileCoordinate) {
		if (gameState.getTheNumberOfTimesComputerHasHitAShip() > 0) {
			continueToShootToTheOppositeDirectionFromTheFirstHit();
		}
	}

	private void continueToShootToTheOppositeDirectionFromTheFirstHit() {
		gameState.setLastHit(gameState.getFirstHit());
		gameState.setDirection(CoordinateHelper.getOppositeDirection(gameState.getDirection()));
	}

}
