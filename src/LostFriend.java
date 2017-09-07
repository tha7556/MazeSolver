import java.util.ArrayList;

public class LostFriend {

	private Path pathTaken;
	private Point startingPoint;
	private Point currentPoint;
	private ArrayList<Point> surrounding;
	private String direction; //TODO: Method to find direction
	private Maze maze;
	
	
	public LostFriend(int x, int y, Maze startMaze) { //TODO: Make this class the generic friend (Abstract Classes), extend it into 3 others for the algorithms?
		this.maze = startMaze;
		this.startingPoint = maze.getPoint(x,y);
		this.currentPoint = maze.getPoint(x,y);
		surrounding = getSurroundings();
		}

	public ArrayList<Point> getSurroundings() {
		surrounding = new ArrayList<Point>();
		int x = currentPoint.getX();
		int y = currentPoint.getY();
		int mazeWidth = maze.getMazeWidth();
		int mazeHeight = maze.getMazeHeight();
		if(x > 0) 
			surrounding.add(maze.getPoint(x-1,y)); //Left
		if(x < mazeWidth)
			surrounding.add(maze.getPoint(x+1,y)); //Right
		if(y > 0)
			surrounding.add(maze.getPoint(x,y-1)); //Up
		if(y < mazeHeight)
			surrounding.add(maze.getPoint(x,y+1)); //Down
		return surrounding;
	}
	
	public void moveTo(Point point) {
		//TODO: Implement this to move the Friend, and handle pathTaken
		if (point.isBlocked() != true)
			this.currentPoint = maze.getPoint(point.getX(), point.getY());
		point.setTraveled(true);
	}
	public Point getNorth() {
		return null;
	}
	public Point getSouth() {
		return null;
	}
	public Point getEast() {
		return null;
	}
	public Point getWest() {
		return null;
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
