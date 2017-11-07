package mazeSolver;

import java.awt.Color;
import java.util.ArrayList;

import mazeGenerator.Maze;
import mazeGenerator.Point;

public class WallFollowerFriend extends LostFriend{
	private boolean rightF = true; 
	private ArrayList<Point> junctions;
	private Point Facing;
	
	public WallFollowerFriend(int startx, int starty, int endx, int endy, Maze startMaze) { 
		super(startx, starty, endx, endy, startMaze);
		
		getAvailablePoints();
		junctions = new ArrayList<Point>();
		pathTaken.addPoint(currentPoint);
		currentPoint.setTraveled(true);
		
	}

	@Override
	public Point calculateMove() {
		ArrayList<Point> availPoints = getAvailablePoints();
		Point next = null;
		if (availPoints.size() == 1) { //One point, move to it
			next = availPoints.get(0);
		}
		else if(availPoints.size() > 1) { //At junction
			junctions.add(currentPoint);
			if (rightF == true) {
				next = rightMostPoint();
			}
		}
		else { //No Avail points, backTrack
			System.out.println("about to backtrack");
			Point lastJunction = getLastJunction();
			backtrackTo(lastJunction);
			next = lastJunction;
			moveTo(next, true); //Backtracks to last junction
			next.changeColor(Color.cyan);
			return next;
		}
		pathTaken.addPoint(next); //moves to Point
		moveTo(next);
		return next;
	}
	/**
	 * Chooses the point that if "Right-most" in points
	 * @return
	 */
	private Point rightMostPoint() {
		Point rMP = null;
		if(facing != null) 
		if (facing.equals(north)) {
			rMP = east;
			System.out.println("facing north");
		}
		if (facing.equals(east)) {
			rMP = south;
			System.out.println("facing east");
		}
		if (facing.equals(south)) {
			rMP = west;
			System.out.println("facing south");
		}
		if (facing.equals(west)) {
			rMP = north;
			System.out.println("facing west");
		}
		return rMP;
	}
	
	private void backtrackTo(Point point) {
		while(!pathTaken.getPathArray().get(pathTaken.size()-1).equals(point)) { 
			pathTaken.deletePoint(pathTaken.size()-1);
		} //removes points from path until it encounters the point
		point.setTraveled(false);
		//System.out.println("Backtracked to: "+point);
	}
	
	private Point getLastJunction() {
		if(junctions.size() > 0) {
			Point j = junctions.get(junctions.size()-1);
			junctions.remove(junctions.size()-1);
			return j;
		}
		return null;
	}
	
	public static void main(String[] args) {
		Maze maze = new Maze("Mazes\\Small\\maze1.png");

		WallFollowerFriend friend = new WallFollowerFriend(1,1,maze.getMazeWidth()-2, maze.getMazeHeight() - 2, maze);
		friend.solveMaze(100);
	}

}
