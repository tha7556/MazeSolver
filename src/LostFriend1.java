import java.util.ArrayList;

public class LostFriend1 {

	private Path pathTaken;
	private Point startingPoint;
	private Point currentPoint;
	private ArrayList<Point> surrounding;
	private String direction;
	private boolean[][] maze;
	
	
	public LostFriend1(int x, int y, boolean[][] startMaze) {
		this.startingPoint = new Point(x,y);
		surrounding = new ArrayList<Point>();
		this.maze = startMaze;
		
		
	}

	public ArrayList<int[]> getSurroundings() {
		return null;
	}

	

	public static void main(String[] args) {
		Maze x = new Maze("C:\\\\Documents\\\\GitHub\\\\MazeSolver\\\\Maze.png");
		//Maze x = new Maze("E:\\\\Computer Science\\\\GitHub\\\\MazeSolver\\\\Maze.png");
		
		LostFriend1 bob = new LostFriend1(0,3, x.getArray());
		
		
	}
	
	
}
