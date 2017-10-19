package mazeSolver;
import java.awt.Color;
import java.util.ArrayList;

import mazeGenerator.Maze;
import mazeGenerator.Point;
/**
 * Abstract MazeRunner Class, used to implement generic functions that will be used by the different versions of it
 *
 */
public abstract class LostFriend {

	protected Path pathTaken;
	protected Point startingPoint;
	protected Point currentPoint;
	//protected String direction; //TODO: Do we still need this?
	protected Point north, south, east, west, end;
	protected Maze maze;
	/**
	 * Constructor that should be extended for each instance of the MazeRunner
	 * @param startx The starting x coordinate for the MazeRunner
	 * @param starty The starting y coordinate for the MazeRunner
	 * @param endx The x coordinate where the Maze exit is
	 * @param endy The y coordinate where the Maze exit is
	 * @param startMaze The Maze that it will try to solve
	 */
	public LostFriend(int startx, int starty, int endx, int endy, Maze startMaze) {
		this.maze = startMaze;
		this.startingPoint = maze.getPoint(startx,starty);
		this.currentPoint = maze.getPoint(startx,starty);
		end = maze.getPoint(endx, endy);
		this.startingPoint.changeColor(Color.BLUE);
		end.changeColor(Color.red);
		getSurroundings();
		pathTaken = new Path(maze);
		}
	/**
	 * Used to get pointers to all of the adjacent points to the MazeRunner
	 * @return Point[] containing all points that are adjacent to the MazeRunner
	 */
	public Point[] getSurroundings() {
		int x = currentPoint.getX();
		int y = currentPoint.getY();
		int mazeWidth = maze.getMazeWidth();
		int mazeHeight = maze.getMazeHeight();
		if(x > 0) 
			west = maze.getPoint(x-1,y);
		else
			west = null;
		if(x < mazeWidth)
			east = maze.getPoint(x+1,y);
		else
			east = null;
		if(y > 0)
			north = maze.getPoint(x,y-1);
		else
			north = null;
		if(y < mazeHeight)
			south = maze.getPoint(x,y+1);
		else
			south = null;
		return new Point[]{north,east,south,west};
	}
	/**
	 * Used to determine the MazeRunner's options for movement
	 * @return ArrayList<Point> of all adjacent points that are not blocked and have not already been traversed
	 */
	public ArrayList<Point> getAvailablePoints() {
		getSurroundings();
		ArrayList<Point> points = new ArrayList<Point>();
		if(north != null && !north.isBlocked() && !north.hasTraveled())
			points.add(north);
		if(south != null && !south.isBlocked() && !south.hasTraveled())
			points.add(south);
		if(east != null && !east.isBlocked() && !east.hasTraveled())
			points.add(east);
		if(west != null && !west.isBlocked() && !west.hasTraveled())
			points.add(west);
		
		return points;
	}
	/**
	 * Moves the MazeRunner to the specified Point
	 * @param point The Point to move the MazeRunner to
	 * @param override true if MazeRunner needs to move somewhere outside of its adjacent points or to a point that it has already traversed
	 */
	public void moveTo(Point point, boolean override) {
		System.out.println("Currently at: "+currentPoint);
		if (getAvailablePoints().contains(point) || (override && !point.isBlocked())) {
			this.currentPoint = point;
			point.setTraveled(true);
			point.changeColor(Color.BLUE);
			getSurroundings();
		}
		else if(point.isBlocked())
			throw new RuntimeException("Point: "+point+" is blocked");
		else if(point.hasTraveled())
			throw new RuntimeException("Point: "+point+" has already been traversed");
		else
			throw new RuntimeException("Cannot move to point: "+point);
	}
	/**
	 * Moves the MazeRunner to the specified Point </p>
	 * The moveTo function that does not need the override variable
	 * @param point The Point to move the MazeRunner to
	 */
	public void moveTo(Point point) {
		moveTo(point,false);
	}
	/**
	 * Gets the Point directly above the MazeRunner
	 * @return The Point directly above the MazeRunner
	 */
	public Point getNorth() {
		return north;
	}
	/**
	 * Gets the Point directly below the MazeRunner
	 * @return The Point directly below the MazeRunner
	 */
	public Point getSouth() {
		return south;
	}
	/**
	 * Gets the Point directly to the right the MazeRunner
	 * @return The Point directly to the right the MazeRunner
	 */
	public Point getEast() {
		return east;
	}
	/**
	 * Gets the Point directly to the left the MazeRunner
	 * @return The Point directly to the left the MazeRunner
	 */
	public Point getWest() {
		return west;
	}
	/**
	 * Gets the Point that the MazeRunner started at
	 * @return The Point that the MazeRunner started at
	 */
	public Point getStartingPoint() {
		return startingPoint;
	}
	/**
	 * Gets the Point where the MazeRunner currently is
	 * @return The Point where the MazeRunner currently is
	 */
	public Point getCurrentPoint() {
		return currentPoint;
	}
	/**
	 * Calls calculate move repeatedly until currentPoint and end are the same Points, and then highlights the correct Path in green
	 * @param waitTime Number of milliseconds to wait between movements
	 * @return The final Path that it took to reach the end Point
	 */
	public Path solveMaze(int waitTime) {
		while(!currentPoint.equals(end)) {
			calculateMove();
			try {
				Thread.sleep((long)waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(Point p : pathTaken.getPathArray()) {
			try {
				Thread.sleep((long)waitTime/2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			p.changeColor(Color.green);
		}
		return pathTaken;
	}
	/**
	 * Calls calculate move repeatedly until currentPoint and end are the same Points, and then highlights the correct Path in green
	 * @return The final Path that it took to reach the end Point
	 */
	public Path solveMaze() {
		return solveMaze(0);
	}
	/**
	 * The function that differentiates the different types of MazeRunner.</p>
	 * Calculates the next Point to move to and also calls the moveTo(Point p) function
	 * @return The next Point that the MazeRunner is moving to
	 */
	public abstract Point calculateMove();
	public static void main(String[] args) {
		Maze x = new Maze("Mazes\\\\Small\\\\maze1.png");
		//LostFriend bob = new LostFriend(1,1,x.getMazeWidth()-2, x.getMazeHeight() - 2, x);
		
		
	}
	
	
}
