package logic;

import java.util.Random;

import components.Ship;
import helpers.Coordinate;

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
	protected String getTheDirection(Ship ship) {
		String[] directions= {"down", "right"};
		return directions[random.nextInt(2)];
	}

}
