package helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import components.GameInfo;

public class GameIO {
	
	public static GameInfo loadGame(UserInterface UI) throws FileNotFoundException, Exception {
		String filename = UI.askForFilename();
		FileInputStream fileIn = new FileInputStream(filename);
		ObjectInputStream objectIn = new ObjectInputStream(fileIn);
		GameInfo storedGame = (GameInfo) objectIn.readObject();
		fileIn.close();
		objectIn.close();
		return storedGame;	
	}
	
	public static void saveGame(UserInterface UI, GameInfo game) {
		String name = UI.askForTheNameOfTheGameToBeSaved();
		String filename = name + ".ser";
		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(game);
			objectOut.close();
			fileOut.close();
			System.out.println("The game has been saved!");
		} catch (IOException e) {
			System.out.println("Something went wrong while saving the game");
		}
		System.out.println("The game has ended. Thank you for playing!");
		UI.closeScanner();
		System.exit(0);
	}

}
