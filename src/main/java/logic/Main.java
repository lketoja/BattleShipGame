package logic;

import java.util.Scanner;

import helpers.UserInterface;

public class Main {
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		UserInterface UI = new UserInterface(scanner);
		GamePreparer gamePreparer = new GamePreparer(UI);
				
		GameInfo game = gamePreparer.prepareGame();
		GameLogic logic = new GameLogic(game.player1, game.player2, UI);
		logic.startGame();
		
		
		
	}
}
