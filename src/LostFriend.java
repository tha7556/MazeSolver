import java.util.ArrayList;

public class LostFriend {

	private Path pathTaken;
	private Point startingPoint;
	private Point currentPoint;
	private String direction; //TODO: Method to find direction
	private Point north, south, east, west;
	private Maze maze;
	
	
	public LostFriend(int x, int y, Maze startMaze) { //TODO: Make this class the generic friend (Abstract Classes), extend it into 3 others for the algorithms?
		this.maze = startMaze;
		this.startingPoint = maze.getPoint(x,y);
		this.currentPoint = maze.getPoint(x,y);
		getSurroundings();
		}

	public void getSurroundings() {
		int x = currentPoint.getX();
		int y = currentPoint.getY();
		int mazeWidth = maze.getMazeWidth();
		int mazeHeight = maze.getMazeHeight();
		if(x > 0) 
			west = maze.getPoint(x-1,y);
		if(x < mazeWidth)
			east = maze.getPoint(x+1,y);
		if(y > 0)
			north = maze.getPoint(x,y-1);
		if(y < mazeHeight)
			east = maze.getPoint(x,y+1);
	}
	
	public void moveTo(Point point) {
		//TODO: Implement this to move the Friend, and handle pathTaken
		if (point.isBlocked() != true)
			this.currentPoint = maze.getPoint(point.getX(), point.getY());
		point.setTraveled(true);
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
