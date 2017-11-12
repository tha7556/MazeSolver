package mazeSolver;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import mazeGenerator.Maze;
import mazeGenerator.Point;

public class ManhattenDistanceFriend extends LostFriend{
	private ArrayList<Point> junctions;

	public ManhattenDistanceFriend(int startx, int starty, int endx, int endy, Maze startMaze) {
		super(startx, starty, endx, endy, startMaze);
		getAvailablePoints();
		junctions = new ArrayList<Point>();
		pathTaken.addPoint(currentPoint);
		currentPoint.setTraveled(true);
	}

	@Override
	public Point calculateMove() {
		/**
		 * Calculates move based on which point in in availPoints has the shortest
		 * distance to the end point
		 */
		ArrayList<Point> availPoints = getAvailablePoints();
		ArrayList<Double> availPointsDistance = new ArrayList<>();
		
		Point next = null;
		
		if(availPoints.size() == 1) { //If only 1 point just move to it
			next = availPoints.get(0);
		}
		else if(availPoints.size() > 1) { //At a junction
			junctions.add(currentPoint);
			double sDist = 0; //Initializes double
			
			for (Point i: availPoints) {
				double currentDistance = distance(i, end);
				availPointsDistance.add(currentDistance);
				if(currentDistance < sDist || sDist == 0) {
					sDist = currentDistance;
				}
				//next = availPoints.get(availPointsDistance.indexOf(sDist)); //Next available point matches intdices between the two ArrayLists of availPoints and availPointsDistance
				}
			
			next = availPoints.get(availPointsDistance.indexOf(sDist)); //Next available point matches intdices between the two ArrayLists of availPoints and availPointsDistance
		
		}
				else { //no available points , needs to backtrack
					Point lastJunction = getLastJunction();
					backtrackTo(lastJunction);
					next = lastJunction;
					moveTo(next, true); //Backtracks to the last junction
					next.changeColor(Color.cyan);
					return next;
			}
		
		pathTaken.addPoint(next); //Moves to the calculated Point
		moveTo(next);
		return next;
	}

	private double distance(Point r, Point q) {
		int xSQR = (r.getX() - q.getX())*(r.getX() - q.getX());
		int ySQR = (r.getY()-q.getY())*(r.getY()-q.getY());
		return Math.sqrt(xSQR + ySQR);
	}
	
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
		Maze maze = new Maze("Mazes\\Crazy\\maze1.png",true);

		ManhattenDistanceFriend robert = new ManhattenDistanceFriend(1,1,maze.getMazeWidth()-2, maze.getMazeHeight() - 2, maze);
		robert.solveMaze("E:\\Pictures\\Solutions\\test");
	}

}
