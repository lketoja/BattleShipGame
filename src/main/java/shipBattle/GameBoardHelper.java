package shipBattle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameBoardHelper {

	private static List<Coordinate> coordinates;
	
	public static List<Coordinate> getTheCoordinatesSurrounding(List<Coordinate> coordinates) {
		coordinates = new ArrayList<>();
		for (Coordinate middlePoint : coordinates) {
			addTheCoordinatesSurroundingACoordinate(middlePoint);
		}
		return coordinates;
	}
	
	private static void addTheCoordinatesSurroundingACoordinate(Coordinate middlePoint) {
		List<Direction> directions = Arrays.asList(Direction.values());
		for (Direction direction : directions) {
			addCoordinateToList(middlePoint, direction);	
		}	
	}
	
	private static void addCoordinateToList(Coordinate middlePoint, Direction direction) {
		try {
			Coordinate coordinate = CoordinateHelper.getTheNextCoordinate(middlePoint, direction);
			coordinates.add(coordinate);
		} catch (cantMoveInThatDirectionException e) {
			return;
		}
	}
	
	public static boolean areThere12UnhitSquaresSurrounding(GameBoard gameBoard, Coordinate middlePoint) {
		coordinates = new ArrayList<Coordinate>();
		addTheCoordinatesSurroundingACoordinate(middlePoint);
		for(Coordinate coordinate : coordinates) {
			addTheCoordinatesSurroundingACoordinate(coordinate);
		}
		return areAllTheCoordinatesUnhit(gameBoard);	
	}

	public static boolean areThere4UnhitSquaresSurrounding(GameBoard gameBoard, Coordinate middlePoint) {
		coordinates = new ArrayList<Coordinate>();
		coordinates.add(middlePoint);
		addTheCoordinatesSurroundingACoordinate(middlePoint);
		return areAllTheCoordinatesUnhit(gameBoard);
	}

	private static boolean areAllTheCoordinatesUnhit(GameBoard gameBoard) {
		for(Coordinate coordinate : coordinates) {
			if(gameBoard.toSquare(coordinate).isShot()){
				return false;
			}
		}
		return true;
	}


}
