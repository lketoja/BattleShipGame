package helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import components.GameBoard;
import exceptions.cantMoveInThatDirectionException;

public class GameBoardHelper {

	public static List<Coordinate> getTheCoordinatesSurrounding(List<Coordinate> shipsCoordinates) {
		List<Coordinate> coordinates = new ArrayList<>();
		for (Coordinate middlePoint : shipsCoordinates) {
			coordinates.addAll(getTheCoordinatesSurroundingACoordinate(middlePoint));
		}
		return coordinates;
	}

	private static List<Coordinate> getTheCoordinatesSurroundingACoordinate(Coordinate middlePoint) {
		List<Direction> directions = Arrays.asList(Direction.values());
		List<Coordinate> coordinates = new ArrayList<>();
		for (Direction direction : directions) {
			try {
				coordinates.add(CoordinateHelper.getTheNextCoordinate(middlePoint, direction));
			} catch (cantMoveInThatDirectionException e) {
			}
		}
		return coordinates;
	}


	public static boolean areThereXUnhitSquaresSurrounding(GameBoard gameBoard, Coordinate coordinate, int x) {
		if (x == 1)
			return gameBoard.toSquare(coordinate).isShot();
		if (x == 4)
			return areThere4UnhitSquaresSurrounding(gameBoard, coordinate);
		if (x == 12)
			return areThere12UnhitSquaresSurrounding(gameBoard, coordinate);
		return false;
	}

	public static boolean areThere4UnhitSquaresSurrounding(GameBoard gameBoard, Coordinate middlePoint) {
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		coordinates.add(middlePoint);
		coordinates.addAll(getTheCoordinatesSurroundingACoordinate(middlePoint));
		return areAllTheCoordinatesUnhit(gameBoard, coordinates);
	}

	public static boolean areThere12UnhitSquaresSurrounding(GameBoard gameBoard, Coordinate middlePoint) {
		ArrayList<Coordinate> coordinatesToBeChecked = new ArrayList<Coordinate>();
		coordinatesToBeChecked.addAll(getTheCoordinatesSurroundingACoordinate(middlePoint));
		List<Coordinate> fourCoordinatesSurroundingMiddlePoint = new ArrayList<>(coordinatesToBeChecked);
		for (Coordinate coordinate : fourCoordinatesSurroundingMiddlePoint) {
			coordinatesToBeChecked.addAll(getTheCoordinatesSurroundingACoordinate(coordinate));
		}
		return areAllTheCoordinatesUnhit(gameBoard, coordinatesToBeChecked);
	}

	private static boolean areAllTheCoordinatesUnhit(GameBoard gameBoard, List<Coordinate> coordinates) {
		for (Coordinate coordinate : coordinates) {
			if (gameBoard.toSquare(coordinate).isShot()) {
				return false;
			}
		}
		return true;
	}

}
