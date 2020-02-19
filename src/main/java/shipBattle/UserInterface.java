package shipBattle;

import java.util.Scanner;

public class UserInterface {

	private Scanner scanner;
	private CoordinateValidator validator;

	public UserInterface(Scanner scanner) {
		this.scanner = scanner;
		validator = new CoordinateValidator();
	}

	public boolean userWantsToLoadAGame() {
		System.out.println("If you want to load a game, enter 'load'. If you want to start a new game, enter 'new'");
		String input = scanner.next();
		if (input.equals("load"))
			return true;
		if (input.equals("new"))
			return false;
		return userWantsToLoadAGame();
	}

	public String askForFilename() {
		String name;
		do {
			System.out.println("Enter the name of the game you want to load.");
			name = scanner.next();
		} while (name == null);
		return name + ".ser";
	}

	public String askForPlayersName() {
		String name;
		do {
			System.out.println("Enter your name.");
			name = scanner.next();
		} while (name == null);
		return name;
	}

	public boolean userWantsToPlayWithAFriend() {
		System.out.println("Do you want to play with a friend or with the computer? Enter 'friend' or 'computer'");
		String input = scanner.next();
		if (input.equals("friend"))
			return true;
		if (input.equals("computer"))
			return false;
		return userWantsToPlayWithAFriend();
	}

	public Coordinate askForFirstCoordinate(Ship ship) {
		System.out.println("Enter the first coordinate for ship " + ship.name 
				+ ". The length of the ship is " + ship.length + "squares");
		String input = scanner.next();
		try {
			return validator.validateCoordinate(input);
		}catch(IllegalArgumentException e) {
			System.out.println("Error! Enter one letter (between a-j) and one number"
					+ "(between 0-9). For example 'd2' is a valid input.");
			askForFirstCoordinate(ship);
		}
		return new Coordinate(11,11);
	}

	public String askForDirection(Ship ship) {
		System.out.println("Enter the direction for ship " + ship.name + ". Enter 'down' or 'right'");
		String input = scanner.next();
		if(input.equals("down") || input.equals("right")) {
			return input;
		} else {
			System.out.println("Error! Enter 'down' or 'right'.");
			return askForDirection(ship);
		}
	}


}
