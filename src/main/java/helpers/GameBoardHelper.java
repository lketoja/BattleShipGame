package helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import components.GameBoard;
import exceptions.CantMoveInThatDirectionException;
import exceptions.CouldNotFindXUnhitSquaresException;

public class GameBoardHelper {

	public static boolean areTheOtherShipsFarEnough(GameBoard gameBoard, List<Coordinate> suggestedLocationForNewShip) {
		List<Coordinate> coordinatesToBeChecked = CoordinateHelper
				.getTheCoordinatesSurrounding(suggestedLocationForNewShip);
		for (Coordinate coordinate : coordinatesToBeChecked) {
			if (gameBoard.isThereAShipIn(coordinate)) {
				return false;
			}
		}
		return true;
	}
	
	public static Coordinate checkEveryOtherSquareFromStartPointUntilXUnhitSquaresAreFound(Coordinate startPoint, int x, GameBoard gameBoard) {
		int maxNumberOfSquaresToBeChecked = 10 * 10 / 2;
		Coordinate coordinate = startPoint;
		for (int i = 0; i < maxNumberOfSquaresToBeChecked; i++) {
			List<Coordinate> coordinatesToBeChecked = CoordinateHelper.getTheCoordinatesToBeChecked(startPoint, x); 
			if (gameBoard.areAllTheCoordinatesUnhit(coordinatesToBeChecked))
				return coordinate;
			coordinate = CoordinateHelper.skipOverOneCoordinateAndGetTheNext(coordinate);
		}
		throw new CouldNotFindXUnhitSquaresException();
	}
	
	public static Direction whichDirectionHasTheBiggestNumberOfUnhitSquares(Coordinate startPoint, GameBoard gameBoard) {
		List<DirectionAndTheNumberOfUnhitSquares> list = prepareListOfDirectionsAndUnhitSquares(startPoint, gameBoard);
		return getTheBestDirectionFromTheList(list);
	}
	
	private static List<DirectionAndTheNumberOfUnhitSquares> prepareListOfDirectionsAndUnhitSquares(Coordinate startPoint, GameBoard gameBoard){
		List<DirectionAndTheNumberOfUnhitSquares> list = new ArrayList<>();
		List<Direction> directions = Arrays.asList(Direction.values());
		for (Direction direction : directions) {
			int numberOfUnhitSquares = GameBoardHelper.getTheNumberOfUnhitSquares(startPoint, direction, gameBoard);
			list.add(new DirectionAndTheNumberOfUnhitSquares(direction, numberOfUnhitSquares));
		}
		return list;
	}
	
	private static Direction getTheBestDirectionFromTheList(List<DirectionAndTheNumberOfUnhitSquares> list) {
		list.sort(Comparator.comparing(DirectionAndTheNumberOfUnhitSquares::getTheNumberOfUnhitSquares));
		Direction bestDirection = list.get(3).getDirection();
		return bestDirection;
	}
	
	public static int getTheNumberOfUnhitSquares(Coordinate startPoint, Direction direction, GameBoard gameBoard) {
		try {
			Coordinate nextCoordinate = CoordinateHelper.getTheNextCoordinate(startPoint, direction);
			if (gameBoard.isShot(nextCoordinate)) {
				return 0;
			}
			return 1 + getTheNumberOfUnhitSquares(nextCoordinate, direction, gameBoard);
		} catch (CantMoveInThatDirectionException e) {
			return 0;
		}
	}

	public static void renderGameBoard(boolean gameHasStarted, GameBoard gameBoard) {
		char[][] board = buildTheViewOfTheBoard(gameHasStarted, gameBoard);
		printTheGameBoard(board);
	}

	private static char[][] buildTheViewOfTheBoard(boolean gameHasStarted, GameBoard gameBoard) {
		char[][] board = new char[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				Coordinate coordinate = new Coordinate(i, j);
				board[i][j] = getTheRightChar(gameHasStarted, coordinate, gameBoard);
			}
		}
		return board;
	}

	// If game has started we show (from the enemies board) only the squares that
	// have been shot at.
	// If we are entering the ships we show (from our own board) all the squares.
	private static char getTheRightChar(boolean gameHasStarted, Coordinate coordinate, GameBoard gameBoard) {
		if (gameHasStarted) {
			if (gameBoard.isShot(coordinate)) {
				return getChar(coordinate, gameBoard);
			}
			return ' ';
		}
		return getChar(coordinate, gameBoard);
	}

	private static char getChar(Coordinate coordinate, GameBoard gameBoard) {
		if (gameBoard.isThereAShipIn(coordinate)) {
			return 'X';
		}
		return '-';
	}

	private static void printTheGameBoard(char[][] board) {
		System.out.println("  A B C D E F G H I J");
		for (int i = 0; i < 10; i++) {
			System.out.print(i);
			for (int j = 0; j < 10; j++) {
				System.out.print(" " + board[i][j]);
			}
			System.out.println("");
		}
	}

}
