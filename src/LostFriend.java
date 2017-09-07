import java.awt.Color;
import java.util.ArrayList;

public class LostFriend {

	private Path pathTaken;
	private Point startingPoint;
	private Point currentPoint;
	private String direction; //TODO: Method to find direction
	private Point north, south, east, west;
	private Maze maze;
	//TODO: Find way to either generate or pull mazes from library Sample size of > 30
	
	public LostFriend(int x, int y, Maze startMaze) { //TODO: Make this class the generic friend (Abstract Classes), extend it into 3 others for the algorithms?
		this.maze = startMaze;
		this.startingPoint = maze.getPoint(x,y);
		this.currentPoint = maze.getPoint(x,y);
		this.startingPoint.changeColor(Color.BLUE);
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
	
	public void moveTo(Point point) {
		//TODO: Implement this to move the Friend, and handle pathTaken
		if (point.isBlocked() != true)
			this.currentPoint = maze.getPoint(point.getX(), point.getY());
		point.setTraveled(true);
		point.changeColor(Color.BLUE);
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
	public static void main(String[] args) {
		//Maze x = new Maze("C:\\\\Documents\\\\GitHub\\\\MazeSolver\\\\Maze.png");
		Maze x = new Maze("E:\\\\Computer Science\\\\GitHub\\\\MazeSolver\\\\Maze.png");
		LostFriend bob = new LostFriend(0,3,x);
		
		
	}
	
	
}
