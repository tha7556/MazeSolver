package mazeSolver;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import mazeGenerator.Maze;
import mazeGenerator.Point;

public class JunctionOriginationFriend extends LostFriend {
	private Point lastJunction; 

	public JunctionOriginationFriend(int startx, int starty, int endx, int endy, Maze startMaze) {
		super(startx, starty, endx, endy, startMaze);
		getAvailablePoints();
		System.out.println("Current: "+currentPoint);
		System.out.println("North: "+north + ", "+getNorth().isBlocked());
		System.out.println("East: "+east+ ", "+getEast().isBlocked()); //TODO: It is saying that some points are blocked when they are not and vice versa
		System.out.println("South: "+south+ ", "+getSouth().isBlocked());
		System.out.println("West: "+west+ ", "+getWest().isBlocked());
	}
	@Override
	public Point calculateMove() {
		ArrayList<Point> points = getAvailablePoints();
		Point next = null;
		if(points.size() == 1) { //If only 1 point just move to it
			next = points.get(0);
		}
		else if(points.size() > 1) { //At a junction
			lastJunction = currentPoint;
			next = chooseRandomPoint(points);						
		}
		else { //no available points
			backtrackTo(lastJunction);
			next = lastJunction;
		}
			pathTaken.addPoint(next);
		return next;
	}
	private void backtrackTo(Point point) {
		while(!pathTaken.getPathArray().get(pathTaken.size()-1).equals(point)) { 
			pathTaken.deletePoint(pathTaken.size()-1);
		} //removes points from path until it encounters the point
	}
	private Point chooseRandomPoint(ArrayList<Point> points) {
		Random r = new Random();
		return points.get(r.nextInt(points.size()));
	}
	
	public static void main(String[] args) {
		Maze maze = new Maze("E:\\Computer Science\\GitHub\\MazeSolver\\Mazes\\Small\\maze1.png");
		JunctionOriginationFriend friend = new JunctionOriginationFriend(1,1,maze.getMazeWidth()-2, maze.getMazeHeight() - 2, maze);
		//friend.solveMaze();
	}

}
