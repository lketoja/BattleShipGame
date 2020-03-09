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

	public ComputerPlayer(GameState gameState) {
		super(gameState);
		name = "Computer";
		random = new Random();
	}

	@Override
	protected Coordinate shoot(UserInterface UI) {
		if (getGameState().getTheNumberOfTimesComputerHasHitAShip() > 0) {
			return continueShinkingTheShip();
		} else {
			return shootRandomly();
		}
	}

	// Shooting is optimized by first looking a larger area that hasn't been shot at
	// yet.
	// First we look for squares that are surrounded by 12 unhit squares (in a shape
	// of diamond).
	// If none is found, we move to look for squares that are surrounded by 4 unhit
	// squares.
	private Coordinate shootRandomly() {
		Coordinate startPoint = drawRandomCoordinate();
		return lookFor12UnhitSquares(startPoint);
	}

	private Coordinate lookFor12UnhitSquares(Coordinate startPoint) {
		try {
			// If we have checked before that there is no such square left, we move on.
			if (!getGameState().isLookForSquareThatIsSurroundedBy12UnhitSquares())
				throw new CouldNotFindXUnhitSquaresException();
			return enemyShips.checkEveryOtherSquareFromStartPointUntilXUnhitSquaresAreFound(startPoint, 12);
		} catch (CouldNotFindXUnhitSquaresException e) {
			getGameState().setLookForSquareThatIsSurroundedBy12UnhitSquares(false);
			return lookFor4UnhitSquares(startPoint);
		}

	}

	private Coordinate lookFor4UnhitSquares(Coordinate startPoint) {
		try {
			// If we have checked before that there is no such square left, we move on.
			if (!getGameState().isLookForSquareThatIsSurroundedBy4UnhitSquares())
				throw new CouldNotFindXUnhitSquaresException();
			return enemyShips.checkEveryOtherSquareFromStartPointUntilXUnhitSquaresAreFound(startPoint, 4);
		} catch (CouldNotFindXUnhitSquaresException e) {
			getGameState().setLookForSquareThatIsSurroundedBy4UnhitSquares(false);
			return enemyShips.checkEveryOtherSquareFromStartPointUntilXUnhitSquaresAreFound(startPoint, 1);
		}
	}

	private Coordinate drawRandomCoordinate() {
		Coordinate coordinate = new Coordinate(random.nextInt(10), random.nextInt(10));
		if ((coordinate.x + coordinate.y) % 2 == getGameState().getShootingOptimizerForComputerPlayer()) {
			return coordinate;
		} else {
			return drawRandomCoordinate();
		}
	}

	private Coordinate continueShinkingTheShip() {
		if (getGameState().getTheNumberOfTimesComputerHasHitAShip() > 1) {
			return continueToShootAlongTheKnownDirection(getGameState().getLastHit(), getGameState().getDirection());
		} else {
			return shootToTheBestDirectionFromTheFirstHit();
		}
	}

	private Coordinate continueToShootAlongTheKnownDirection(Coordinate lastHit, Direction direction) {
		return CoordinateHelper.getTheNextCoordinate(lastHit, direction);
	}

	private Coordinate shootToTheBestDirectionFromTheFirstHit() {
		Direction bestDirection = whichDirectionHasTheBiggestNumberOfUnhitSquares(getGameState().getFirstHit());
		getGameState().setDirection(bestDirection);
		return continueToShootAlongTheKnownDirection(getGameState().getFirstHit(), bestDirection);
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

	@Override
	protected void updateGameStateAfterAHit(Coordinate missileCoordinate) {
		if (getGameState().getTheNumberOfTimesComputerHasHitAShip() == 0)
			getGameState().setFirstHit(missileCoordinate);
		getGameState().incrementTheNuberOfTimesComputerHasHitAShip();
		getGameState().setLastHit(missileCoordinate);
	}

	@Override
	protected void updateGameStateAfterSunkenShip() {
		getGameState().setTheNumberOfTimesComputerHasHitAShip(0);
		getGameState().setFirstHit(null);
		getGameState().setLastHit(null);
		getGameState().setDirection(null);
	}

	@Override
	protected void updateGameStateAfterMissileDidntHit(Coordinate missileCoordinate) {
		if (getGameState().getTheNumberOfTimesComputerHasHitAShip() > 0) {
			continueToShootToTheOppositeDirectionFromTheFirstHit();
		}
	}

	private void continueToShootToTheOppositeDirectionFromTheFirstHit() {
		getGameState().setLastHit(getGameState().getFirstHit());
		getGameState().setDirection(CoordinateHelper.getOppositeDirection(getGameState().getDirection()));
	}

}
