package shipBattle;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GameLogic {

	private GameState gameState;
	private Player[] players;
	private UserInterface UI;

	public GameLogic(GameState state, Player player1, Player player2, UserInterface UI) {
		super();
		this.gameState = state;
		players = new Player[2];
		players[0] = player1;
		players[1] = player2;
		this.UI = UI;
	}

	public void startGame() {
		System.out.println("Let's start the game!");
		System.out.println("You can save the game whenever you want by typing 'save' "
				+ "instead of the coordinate for the missle.");
		int counter = 0;
		while (true) {
			Player theOneShooting = players[counter % 2];
			try {
				theOneShooting.playTurn(gameState);
			} catch (PlayerWantsToSaveTheGameException e) {
				saveGame();
			}

		}

	}

	private void saveGame() {
		String name = UI.askForTheNameOfTheGameToBeSaved();
		String filename = name + ".ser";
		GameInfo gameInfo = new GameInfo(gameState, players[0], players[1]);
		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(gameInfo);
			objectOut.close();
			fileOut.close();
			System.out.println("The game has been saved!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("The game has ended. Thank you for playing!");
		UI.closeScanner();
		System.exit(0);
	}

}
