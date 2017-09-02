import java.util.ArrayList;

public class LostFriend1 {
<<<<<<< HEAD
	private PathHolder pathTaken;
	private int[] startingPoint;
	private ArrayList<int[]> surrounding;
	private String direction;
	private boolean[] maze;
	
	
	public LostFriend1(int x, int y, boolean[][] startMaze) {
		this.startingPoint = new int[] {x,y};
		surrounding = new ArrayList<int[]>();
		
		
	}

	public ArrayList<int[]>() getSurroundings() {
=======
	private Path pathTaken;
	private Path initial;
	private ArrayList<int[]> surroudning;
	
	public LostFriend1(Path pT, Path iNi ) {
		this.pathTaken = pT;
		this.initial = iNi;
>>>>>>> 9f4fe180a926d78745344635c1440a32b04728fe
		
	}
	

public static void main(String[] args) {
	Maze x = new Maze("C:\\\\Documents\\\\GitHub\\\\MazeSolver\\\\Maze.png");
	
	LostFriend1 bob = new LostFriend1(0,3, x.getArray());
	
	
}
	
	
}
