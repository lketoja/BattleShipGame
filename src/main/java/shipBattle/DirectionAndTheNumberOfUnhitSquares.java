package shipBattle;

public class DirectionAndTheNumberOfUnhitSquares {
	
	private Direction direction;
	private int theNumberOfUnhitSquares;
	
	public DirectionAndTheNumberOfUnhitSquares(Direction direction, int theNumberOfUnhitSquares) {
		super();
		this.direction = direction;
		this.theNumberOfUnhitSquares = theNumberOfUnhitSquares;
	}

	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public int getTheNumberOfUnhitSquares() {
		return theNumberOfUnhitSquares;
	}
	public void setTheNumberOfUnhitSquares(int theNumberOfUnhitSquares) {
		this.theNumberOfUnhitSquares = theNumberOfUnhitSquares;
	}
	
}
