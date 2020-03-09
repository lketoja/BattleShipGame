package logic;

import java.util.Random;

import components.Ship;
import helpers.Coordinate;
import helpers.Direction;

public class ShipMakerForComputerPlayer extends ShipMaker {
	
	private Random random;

	public ShipMakerForComputerPlayer() {
		super();
		this.random = new Random();
	}
	
	@Override
	protected Coordinate getTheFirstCoordinate(Ship ship) {
		return new Coordinate(random.nextInt(10), random.nextInt(10));
	}
	
	@Override
	protected Direction getTheDirection(Ship ship) {
		Direction[] directions= {Direction.DOWN, Direction.UP};
		return directions[random.nextInt(2)];
	}

}
