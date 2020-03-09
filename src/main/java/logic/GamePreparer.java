package logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;

import components.ComputerPlayer;
import components.GameBoard;
import components.HumanPlayer;
import components.Player;
import helpers.UserInterface;

public class GamePreparer {

	private UserInterface UI;
	private Player player1;
	private Player player2;
	private GameState gameState;

	public GamePreparer(UserInterface UI) {
		super();
		this.UI = UI;
		gameState = new GameState();
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
		return new GameInfo(player1, player2);
	}

	private void getThePlayersReady() {
		player1 = createHumanPlayer();
		if (UI.userWantsToPlayWithAFriend()) {
			player2 = createHumanPlayer();
		} else {
			player2 = new ComputerPlayer(gameState);
		}
	}

	private Player createHumanPlayer() {
		Player player = new HumanPlayer(gameState);
		player.setName(UI.askForPlayersName());
		return player;
	}
	
	private void enterShipsToTheGameBoard() {
		ShipMaker shipMaker = new ShipMakerForHumanPlayer(UI);
		GameBoard player1Ships = shipMaker.prepareShips(player1);
		if(player2 instanceof ComputerPlayer) {
			shipMaker = new ShipMakerForComputerPlayer();
		} else {
			shipMaker = new ShipMakerForHumanPlayer(UI);
		}
		GameBoard player2Ships = shipMaker.prepareShips(player2);
		player1.setGameBoard(player2Ships);
		player2.setGameBoard(player1Ships);
	}

}
