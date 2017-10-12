package mazeSolver;
import java.awt.Color;
import java.util.ArrayList;

import mazeGenerator.Maze;
import mazeGenerator.Point;

public abstract class LostFriend {

	protected Path pathTaken;
	protected Point startingPoint;
	protected Point currentPoint;
	protected String direction; //TODO: Method to find direction
	protected Point north, south, east, west, end;
	protected Maze maze;
	
	public LostFriend(int startx, int starty, int endx, int endy, Maze startMaze) {
		this.maze = startMaze;
		this.startingPoint = maze.getPoint(startx,starty);
		this.currentPoint = maze.getPoint(startx,starty);
		System.out.println(endy);
		end = maze.getPoint(endx, endy);
		System.out.println(startingPoint.getY());
		this.startingPoint.changeColor(Color.BLUE);
		end.changeColor(Color.red);
		getSurroundings();
		pathTaken = new Path(maze);
		}

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
	public void moveTo(Point point) {
		if (getAvailablePoints().contains(point)) {
			this.currentPoint = maze.getPoint(point.getX(), point.getY());
			point.setTraveled(true);
			point.changeColor(Color.BLUE);
			getSurroundings();
		}
		else
			System.out.println("Cannot move to point at: ("+point.getX()+","+point.getY()+")");
	}
	public Point getNorth() {
		return north;
	}
	public Point getSouth() {
		return south;
	}
	public Point getEast() {
		return east;
	}
	public Point getWest() {
		return west;
	}
	public Point getStartingPoint() {
		return startingPoint;
	}
	public Point getCurrentPoint() {
		return currentPoint;
	}
	public Path solveMaze() {
		while(!currentPoint.equals(end)) {
			Point next = calculateMove();
			moveTo(next);
		}
		return pathTaken;
	}
	public abstract Point calculateMove();
	public static void main(String[] args) {
		//Maze x = new Maze("C:\\\\Documents\\\\GitHub\\\\MazeSolver\\\\Maze.png");
		Maze x = new Maze("E:\\\\Computer Science\\\\GitHub\\\\MazeSolver\\\\Mazes\\\\Small\\\\maze1.png");
		//LostFriend bob = new LostFriend(1,1,x.getMazeWidth()-2, x.getMazeHeight() - 2, x);
		
		
	}
	
	
}
