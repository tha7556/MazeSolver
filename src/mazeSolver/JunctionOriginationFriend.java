package mazeSolver;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import mazeGenerator.Maze;
import mazeGenerator.Point;
/**
 * Solves the Maze by randomly selecting a Point  when at a junction, and when it encounters a dead-end it backtracks to the last junction
 */
public class JunctionOriginationFriend extends LostFriend {
	private ArrayList<Point> junctions; 
	/**
	 * Creates a new MazeRunner using the Junction Origination algorithm
	 * @param startx The starting x coordinate for the MazeRunner
	 * @param starty The starting y coordinate for the MazeRunner
	 * @param endx The x coordinate where the Maze exit is
	 * @param endy The y coordinate where the Maze exit is
	 * @param startMaze The Maze that it will try to solve
	 */
	public JunctionOriginationFriend(int startx, int starty, int endx, int endy, Maze startMaze) {
		super(startx, starty, endx, endy, startMaze);
		getAvailablePoints();
		junctions = new ArrayList<Point>();
		pathTaken.addPoint(currentPoint);
		currentPoint.setTraveled(true);
	}
	/**
	 * Calculates the next Point and moves the mazeRunner to that Point using the Junction Origination algorithm
	 */
	public Point calculateMove() {
		ArrayList<Point> points = getAvailablePoints();
		Point next = null;
		if(points.size() == 1) { //If only 1 point just move to it
			next = points.get(0);
		}
		else if(points.size() > 1) { //At a junction
			junctions.add(currentPoint);
			next = chooseRandomPoint(points);						
		}
		else { //no available points , needs to backtrack
			Point lastJunction = getLastJunction();
			backtrackTo(lastJunction);
			next = lastJunction;
			moveTo(next,true); //Backtracks to the last junction
			next.changeColor(Color.cyan);
			return next;
		}
		pathTaken.addPoint(next); //Moves to the calculated Point
		moveTo(next);
		return next;
	}
	
	/**
	 * Randomly chooses a Point out of a list of Points
	 * @param points The ArrayList<Point> of Points to choose from
	 * @return The randomly chosen Point
	 */
	private Point chooseRandomPoint(ArrayList<Point> points) {
		Random r = new Random();
		return points.get(r.nextInt(points.size()));
	}
	/**
	 * Pops the last junction off of the list of junctions
	 * @return The Point where the last junction is located
	 */
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

		JunctionOriginationFriend friend = new JunctionOriginationFriend(1,1,maze.getMazeWidth()-2, maze.getMazeHeight() - 2, maze);
		friend.solveMaze(1);
	}

}
