package mazeSolver;
import java.awt.Color;
import java.util.ArrayList;

import mazeGenerator.Maze;
import mazeGenerator.Point;

public class LostFriend {

	private Path pathTaken;
	private Point startingPoint;
	private Point currentPoint;
	private String direction; //TODO: Method to find direction
	private Point north, south, east, west, end;
	private Maze maze;
	
	public LostFriend(int startx, int starty, int endx, int endy, Maze startMaze) { //TODO: Make this class the generic friend (Abstract Classes), extend it into 3 others for the algorithms?
		this.maze = startMaze;
		this.startingPoint = maze.getPoint(startx,starty);
		this.currentPoint = maze.getPoint(startx,starty);
		end = maze.getPoint(endx, endy);
		System.out.println(startingPoint.getY());
		this.startingPoint.changeColor(Color.BLUE);
		end.changeColor(Color.red);
		getSurroundings();
		}

	public void getSurroundings() {
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
	}
	public ArrayList<Point> getAvailablePoints() {
		ArrayList<Point> points = new ArrayList<Point>();
		if(getNorth() != null && !getNorth().isBlocked())
			points.add(getNorth());
		if(getSouth() != null && !getSouth().isBlocked())
			points.add(getSouth());
		if(getEast() != null && !getEast().isBlocked())
			points.add(getEast());
		if(getWest() != null && !getWest().isBlocked())
			points.add(getWest());
		return points;
	}
	public void moveTo(Point point) {
		//TODO: Implement this to move the Friend, and handle pathTaken
		if (getAvailablePoints().contains(point)) {
			this.currentPoint = maze.getPoint(point.getX(), point.getY());
			point.setTraveled(true);
			point.changeColor(Color.BLUE);
			pathTaken.addPoint(point.getX(), point.getY());
			maze.getDisplay().repaint();
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
	public static void main(String[] args) {
		//Maze x = new Maze("C:\\\\Documents\\\\GitHub\\\\MazeSolver\\\\Maze.png");
		Maze x = new Maze("E:\\\\Computer Science\\\\GitHub\\\\MazeSolver\\\\Mazes\\\\Small\\\\maze1.png");
		LostFriend bob = new LostFriend(1,1,x.getMazeWidth()-2, x.getMazeHeight() - 2, x);
		
		
	}
	
	
}
