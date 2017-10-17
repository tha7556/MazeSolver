package mazeSolver;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import mazeGenerator.Maze;
import mazeGenerator.Point;

public class JunctionOriginationFriend extends LostFriend {
	private ArrayList<Point> junctions; 

	public JunctionOriginationFriend(int startx, int starty, int endx, int endy, Maze startMaze) {
		super(startx, starty, endx, endy, startMaze);
		getAvailablePoints();
		junctions = new ArrayList<Point>();
		pathTaken.addPoint(currentPoint);
	}
	@Override
	public Point calculateMove() {
		ArrayList<Point> points = getAvailablePoints();
		Point next = null;
		if(points.size() == 1) { //If only 1 point just move to it
			//System.out.println("Next should be: "+points.get(0));
			next = points.get(0);
		}
		else if(points.size() > 1) { //At a junction
			junctions.add(currentPoint);
			next = chooseRandomPoint(points);						
		}
		else { //no available points
			Point lastJunction = getLastJunction();
			backtrackTo(lastJunction);
			next = lastJunction;
			moveTo(next,true);
			next.changeColor(Color.cyan);
			return next;
		}
			pathTaken.addPoint(next);
			moveTo(next);
		return next;
	}
	private void backtrackTo(Point point) {
		while(!pathTaken.getPathArray().get(pathTaken.size()-1).equals(point)) { 
			pathTaken.deletePoint(pathTaken.size()-1);
		} //removes points from path until it encounters the point
		point.setTraveled(false);
		System.out.println("Backtracked to: "+point);
	}
	private Point chooseRandomPoint(ArrayList<Point> points) {
		Random r = new Random();
		return points.get(r.nextInt(points.size()));
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
		Maze maze = new Maze("E:\\Computer Science\\GitHub\\MazeSolver\\Mazes\\Large\\maze1.png");
		JunctionOriginationFriend friend = new JunctionOriginationFriend(1,1,maze.getMazeWidth()-2, maze.getMazeHeight() - 2, maze);
		System.out.println(friend.getSouth());
		friend.solveMaze(20);
	}

}
