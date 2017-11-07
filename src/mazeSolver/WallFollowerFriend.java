package mazeSolver;

import java.awt.Color;
import java.util.ArrayList;

import mazeGenerator.Maze;
import mazeGenerator.Point;

public class WallFollowerFriend extends LostFriend{
	private boolean rightF; 
	private ArrayList<Point> junctions;
	
	public WallFollowerFriend(int startx, int starty, int endx, int endy, Maze startMaze,boolean goRight) { 
		super(startx, starty, endx, endy, startMaze);
		
		getAvailablePoints();
		junctions = new ArrayList<Point>();
		pathTaken.addPoint(currentPoint);
		currentPoint.setTraveled(true);
		rightF = goRight;
		
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
			else {
				next = leftMostPoint();
			}
		}
		else { //No Avail points, backTrack
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
	 * Chooses the point that is "Right-most" in points
	 * @return
	 */
	private Point rightMostPoint() {
		Point rMP = null; 
		if (direction.equals("North")) {
			if(east != null && !east.isBlocked() && !east.hasTraveled()) {
			rMP = east;
			}
			else {
				direction = "East";
				return rightMostPoint();
			}
		}
		if (direction.equals("East")) {
			if(south != null && !south.isBlocked() && !south.hasTraveled()) {
				rMP = south;
			}
			else {
				direction = "South";
				return rightMostPoint();
			}
		}
		if (direction.equals("South")) {
			if(west != null && !west.isBlocked() && !west.hasTraveled()) {
				rMP = west;
			}
			else {
				direction = "West";
				return rightMostPoint();
			}
			
		}
		if (direction.equals("West")) {
			if(north != null && !north.isBlocked() && !north.hasTraveled()) {
				rMP = north;
			}
			else {
				direction = "North";
				return rightMostPoint();
			}			
		}
		return rMP;
	}
	/**
	 * Chooses the point that is "Left-most" in points
	 * @return
	 */
	private Point leftMostPoint() {
		Point lMP = null; 
		if (direction.equals("North")) {
			if(west != null && !west.isBlocked() && !west.hasTraveled()) {
			lMP = west;
			}
			else {
				direction = "South";
				return leftMostPoint();
			}
		}
		if (direction.equals("East")) {
			if(north != null && !north.isBlocked() && !north.hasTraveled()) {
				lMP = north;
			}
			else {
				direction = "North";
				return leftMostPoint();
			}
		}
		if (direction.equals("South")) {
			if(east != null && !east.isBlocked() && !east.hasTraveled()) {
				lMP = east;
			}
			else {
				direction = "East";
				return rightMostPoint();
			}
			
		}
		if (direction.equals("West")) {
			if(south != null && !south.isBlocked() && !south.hasTraveled()) {
				lMP = south;
			}
			else {
				direction = "South";
				return rightMostPoint();
			}			
		}
		return lMP;
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

		WallFollowerFriend friend = new WallFollowerFriend(1,1,maze.getMazeWidth()-2, maze.getMazeHeight() - 2, maze,false);
		friend.solveMaze();
	}

}
