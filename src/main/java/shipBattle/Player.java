package shipBattle;

public class Player {
	
	protected String name;
	protected GameBoard gameBoard;
	
	public void enterShips() {
		for(Ship ship : gameBoard.getShips()) {
			enterShip(ship);
		}
	}
	
	public boolean enterShip(Ship ship) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	

	

}
