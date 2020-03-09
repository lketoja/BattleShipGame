package logic;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import components.Player;
import exceptions.PlayerWantsToSaveTheGameException;
import helpers.GameIO;
import helpers.UserInterface;

public class GameLogic {

	private GameState gameState;
	private Player[] players;
	private UserInterface UI;

	public GameLogic(GameInfo game, UserInterface UI) {
		super();
		gameState = game.gameState;
		players = new Player[2];
		players[0] = game.player1;
		players[1] = game.player2;
		this.UI = UI;
	}

	public void startGame() {
		System.out.println("Let's start the game!");
		System.out.println("You can save the game whenever you want by typing 'save' "
				+ "instead of the coordinate for the missle.");
		while (true) {
			int whoseTurn = gameState.getPlayerInTurn();
			Player theOneShooting = players[whoseTurn];
			try {
				theOneShooting.playTurn(gameState, UI);
			} catch (PlayerWantsToSaveTheGameException e) {
				GameIO.saveGame(UI, new GameInfo(players[0], players[1], gameState));
			}
		}
	}

	

}
