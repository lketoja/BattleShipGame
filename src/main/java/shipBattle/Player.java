package shipBattle;

public class Player {
	
	protected String name;
	protected GameBoard enemyShips;
	protected UserInterface UI;
	
	public Player(UserInterface uI) {
		super();
		UI = uI;
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

	public void playTurn(GameState gameState) {
		enemyShips.printGameBoard(enemyShips.buildTheViewOfBoard(true));
		Coordinate missileCoordinate = shoot();
		enemyShips.markThatSquareWasHit(missileCoordinate);
		if(enemyShips.isThereAShipIn(missileCoordinate))
			handleHit(missileCoordinate);
	}

	private void handleHit(Coordinate missileCoordinate) {
		if(enemyShips.didTheShipSink(missileCoordinate)) {
			enemyShips.markSunkenShip(missileCoordinate);
			if(enemyShips.areAllTheShipsSunken()) {
				System.out.println("All the ships are sunken! You won!");
				System.exit(0);
			}	
		}
		System.out.println("The ship sank! You get to shoot again!");
	}

	private Coordinate shoot() {
		return UI.askForTheCoordinateOfTheMissle();
	}
	
	

	

}
