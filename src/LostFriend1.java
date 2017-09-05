import java.util.ArrayList;

public class LostFriend1 {

	private Path pathTaken;
	private Point startingPoint;
	private Point currentPoint;
	private ArrayList<Point> surrounding;
	private String direction;
	private Maze maze;
	
	
	public LostFriend1(int x, int y, Maze startMaze) {
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
		
	}
	public Point getStartingPoint() {
		return startingPoint;
	}
	public static void main(String[] args) {
		//Maze x = new Maze("C:\\\\Documents\\\\GitHub\\\\MazeSolver\\\\Maze.png");
		Maze x = new Maze("E:\\\\Computer Science\\\\GitHub\\\\MazeSolver\\\\Maze.png");
		LostFriend1 bob = new LostFriend1(0,3,x);
		
		
	}
	
	
}
