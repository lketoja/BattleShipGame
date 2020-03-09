package helpers;

import java.util.Scanner;

import components.Ship;
import exceptions.PlayerWantsToSaveTheGameException;

public class UserInterface {

	private Scanner scanner;

	public UserInterface(Scanner scanner) {
		this.scanner = scanner;
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
		System.out.println("Enter the first coordinate for ship " + ship.name + ". The length of the ship is "
				+ ship.length + "squares");
		return askForCoordinate();

	}

	public String askForDirection(Ship ship) {
		System.out.println("Enter the direction for ship " + ship.name + ". Enter 'down' or 'right'");
		String input = scanner.next();
		if (input.equals("down") || input.equals("right")) {
			return input;
		} else {
			System.out.println("Error! Enter 'down' or 'right'.");
			return askForDirection(ship);
		}
	}

	public Coordinate askForTheCoordinateOfTheMissle() {
		System.out.println("Which square do you want to shoot at? " + "Enter the coordinate for the missle (eg. '7g')");
		return askForCoordinate();
	}

	public Coordinate askForCoordinate() {
		Coordinate coordinate = new Coordinate(11, 11);
		boolean validCoordinate = false;
		do {
			String input = scanner.next();
			throwExceptionIfPlayerWantsToSaveTheGame(input);
			try {
				coordinate = CoordinateHelper.validateCoordinate(input);
				validCoordinate = true;
			} catch (IllegalArgumentException e) {
				System.out.println("Error! Enter one letter (between a-j) and one number"
						+ "(between 0-9). For example 'd2' is a valid input.");
			}
		} while (!validCoordinate);
		return coordinate;
	}

	public void throwExceptionIfPlayerWantsToSaveTheGame(String input) {
		if (input.equals("save")) {
			throw new PlayerWantsToSaveTheGameException();
		}
	}

	public String askForTheNameOfTheGameToBeSaved() {
		System.out.println("Enter a name for this game. (You need this when you want to load this game again!)");
		String name = scanner.next();
		return name;
	}

	public void closeScanner() {
		scanner.close();
	}

	public void waitForPlayerToHitEnter() {
		System.out.println("Write anything and enter for game to continue!");
		String anything = scanner.next();
	}

}
