package components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import exceptions.CouldNotFindXUnhitSquaresException;
import helpers.Coordinate;
import helpers.CoordinateHelper;
import helpers.Direction;
import helpers.DirectionAndTheNumberOfUnhitSquares;
import helpers.UserInterface;
import logic.GameState;

public class ComputerPlayer extends Player {

	private Random random;

	public ComputerPlayer() {
		super();
		name = "Computer";
		random = new Random();
	}

	@Override
	protected Coordinate shoot(GameState gameState, UserInterface UI) {
		if (gameState.getTheNumberOfTimesComputerHasHitAShip() > 0) {
			return continueShinkingTheShip(gameState);
		} else {
			return shootRandomly(gameState);
		}
	}

	private Coordinate continueShinkingTheShip(GameState gameState) {
		if (gameState.getTheNumberOfTimesComputerHasHitAShip() > 1) {
			return continueToShootAlongTheKnownDirection(gameState.getLastHit(), gameState.getDirection());
		} else {
			return shootToTheBestDirectionFromTheFirstHit(gameState);
		}
	}

	private Coordinate continueToShootAlongTheKnownDirection(Coordinate lastHit, Direction direction) {
		return CoordinateHelper.getTheNextCoordinate(lastHit, direction);
	}

	private Coordinate shootToTheBestDirectionFromTheFirstHit(GameState gameState) {
		Direction bestDirection = whichDirectionHasTheBiggestNumberOfUnhitSquares(gameState.getFirstHit());
		gameState.setDirection(bestDirection);
		return continueToShootAlongTheKnownDirection(gameState.getFirstHit(), bestDirection);
	}

	private Direction whichDirectionHasTheBiggestNumberOfUnhitSquares(Coordinate startPoint) {
		List<DirectionAndTheNumberOfUnhitSquares> list = new ArrayList<>();
		List<Direction> directions = Arrays.asList(Direction.values());
		for (Direction direction : directions) {
			int numberOfUnhitSquares = enemyShips.getTheNumberOfUnhitSquares(startPoint, direction);
			list.add(new DirectionAndTheNumberOfUnhitSquares(direction, numberOfUnhitSquares));
		}
		list.sort(Comparator.comparing(DirectionAndTheNumberOfUnhitSquares::getTheNumberOfUnhitSquares));
		Direction bestDirection = list.get(3).getDirection();
		return bestDirection;
	}

	// Shooting is optimized by first looking a larger area that hasn't been shot at
	// yet.
	// First we look for squares that are surrounded by 12 unhit squares (in a shape
	// of diamond).
	// If none is found, we move to look for squares that are surrounded by 4 unhit
	// squares.
	private Coordinate shootRandomly(GameState gameState) {
		Coordinate startPoint = drawRandomCoordinate(gameState);
		return lookFor12UnhitSquares(gameState, startPoint);
	}

	private Coordinate drawRandomCoordinate(GameState gameState) {
		Coordinate coordinate = new Coordinate(random.nextInt(10), random.nextInt(10));
		if ((coordinate.x + coordinate.y) % 2 == gameState.getShootingOptimizerForComputerPlayer()) {
			return coordinate;
		} else {
			return drawRandomCoordinate(gameState);
		}
	}

	private Coordinate lookFor12UnhitSquares(GameState gameState, Coordinate startPoint) {
		try {
			// If we have checked before that there is no such square left, we move on.
			if (!gameState.isLookForSquareThatIsSurroundedBy12UnhitSquares())
				throw new CouldNotFindXUnhitSquaresException();
			return enemyShips.checkEveryOtherSquareFromStartPointUntilXUnhitSquaresAreFound(startPoint, 12);
		} catch (CouldNotFindXUnhitSquaresException e) {
			gameState.setLookForSquareThatIsSurroundedBy12UnhitSquares(false);
			return lookFor4UnhitSquares(gameState, startPoint);
		}
	}

	private Coordinate lookFor4UnhitSquares(GameState gameState, Coordinate startPoint) {
		try {
			// If we have checked before that there is no such square left, we move on.
			if (gameState.isLookForSquareThatIsSurroundedBy4UnhitSquares())
				throw new CouldNotFindXUnhitSquaresException();
			return enemyShips.checkEveryOtherSquareFromStartPointUntilXUnhitSquaresAreFound(startPoint, 4);
		} catch (CouldNotFindXUnhitSquaresException e) {
			gameState.setLookForSquareThatIsSurroundedBy4UnhitSquares(false);
			return enemyShips.checkEveryOtherSquareFromStartPointUntilXUnhitSquaresAreFound(startPoint, 1);
		}
	}

	@Override
	protected void updateGameStateAfterAHit(GameState gameState, Coordinate missileCoordinate) {
		if (gameState.getTheNumberOfTimesComputerHasHitAShip() == 0)
			gameState.setFirstHit(missileCoordinate);
		gameState.incrementTheNuberOfTimesComputerHasHitAShip();
		gameState.setLastHit(missileCoordinate);
	}

	@Override
	protected void updateGameStateAfterSunkenShip(GameState gameState) {
		gameState.setTheNumberOfTimesComputerHasHitAShip(0);
		gameState.setFirstHit(null);
		gameState.setLastHit(null);
		gameState.setDirection(null);
	}

	@Override
	protected void updateGameStateAfterMissileDidntHit(GameState gameState, Coordinate missileCoordinate) {
		if (gameState.getTheNumberOfTimesComputerHasHitAShip() > 0) {
			updateGameStateToShootToTheOppositeDirectionFromTheFirstHit(gameState);
		}
	}

	private void updateGameStateToShootToTheOppositeDirectionFromTheFirstHit(GameState gameState) {
		gameState.setLastHit(gameState.getFirstHit());
		gameState.setDirection(CoordinateHelper.getOppositeDirection(gameState.getDirection()));
	}

}
