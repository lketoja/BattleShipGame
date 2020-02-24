package shipBattle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;

public class GamePreparer {

	private UserInterface UI;
	private Player player1;
	private Player player2;

	public GamePreparer(UserInterface uI) {
		super();
		UI = uI;
	}

	public GameInfo prepareGame() {
		System.out.println("Welcome to play Battle Ship!");
		GameInfo game = setTheGameReadyToBePlayed();
		return game;
	}

	private GameInfo setTheGameReadyToBePlayed() {
		if (UI.userWantsToLoadAGame())
			return loadGame();
		return createNewGame();
	}

	private GameInfo loadGame() {
		String filename = UI.askForFilename();
		GameInfo storedGame = readTheFile(filename);
		if (storedGame == null)
			setTheGameReadyToBePlayed();
		return storedGame;
	}

	private GameInfo readTheFile(String filename) {
		try {
			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			GameInfo storedGame = (GameInfo) objectIn.readObject();
			fileIn.close();
			objectIn.close();
			return storedGame;
		} catch (FileNotFoundException e) {
			System.out.println("We couldn't find a game with a name that you entered.");
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private GameInfo createNewGame() {
		getThePlayersReady();
		enterShipsToTheGameBoard();
		return new GameInfo(new GameState(), player1, player2);
	}

	private void getThePlayersReady() {
		player1 = createHumanPlayer();
		if (UI.userWantsToPlayWithAFriend())
			player2 = createHumanPlayer();
		player2 = new ComputerPlayer(UI);			
	}

	private Player createHumanPlayer() {
		Player player = new HumanPlayer(UI);
		player.setName(UI.askForPlayersName());
		return player;
	}
	
	private void enterShipsToTheGameBoard() {
		ShipMaker shipMaker = new ShipMakerForHumanPlayer(UI);
		GameBoard player1Ships = shipMaker.prepareShips();
		if(player2 instanceof ComputerPlayer)
			shipMaker = new ShipMakerForComputerPlayer();
		GameBoard player2Ships = shipMaker.prepareShips();
		player1.setGameBoard(player2Ships);
		player2.setGameBoard(player1Ships);
	}
	
	
}
