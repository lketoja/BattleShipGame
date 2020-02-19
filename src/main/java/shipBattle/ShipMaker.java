package shipBattle;

public class ShipMaker {
	
	protected UserInterface UI;
	protected GameBoard gameBoard;
	
	public ShipMaker(UserInterface uI) {
		super();
		UI = uI;
		gameBoard = new GameBoard();
	}

	public GameBoard prepareShips() {
		return new GameBoard();
	}

}
