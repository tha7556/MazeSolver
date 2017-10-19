package mazeSolver;
import java.util.ArrayList;

import mazeGenerator.Maze;
import mazeGenerator.Point;

/**
 * A collection of Points that the MazeRunner has traversed
 */
public class Path { //TODO: Somehow we ended up with this being just an ArrayList? So perhaps we gut this file and just use an ArrayList?
	private ArrayList<Point> pathArray;
	private Maze maze;
	/**
	 * Creates a new Path
	 * @param maze The maze that the Path is based on
	 */
	public Path(Maze maze) {
		this.maze = maze;
		pathArray = new ArrayList<Point>();	
	}
	/**
	 * Gets the ArrayList<Point> representation of the Path
	 * @return The ArrayList<Point> of the Points in the Path
	 */
	public ArrayList<Point> getPathArray() {
		return pathArray;
	}
	/**
	 * Adds a new Point to the Path
	 * @param point The Point being added to the Path
	 */
	public void addPoint(Point point) {
		pathArray.add(point);
	}
	/**
	 * Deletes a point from the Path
	 * @param index The index in the Path's pathArray of the Point to be removed
	 * @return  The Point that is removed
	 */
	public Point deletePoint(int index) {
		Point p = pathArray.get(index);
		pathArray.remove(index);
		return p;
	}
	/**
	 * Gets the size of the path
	 * @return The size of the path
	 */
	public int size() {
		return pathArray.size();
	}
	/**
	 * Gets the Maze object that the Path is based on
	 * @return The Maze that the Path is based on
	 */
	public Maze getMaze() {
		return maze;
	}
	public static void main(String[] args) {
		Maze maze = new Maze("E:\\Computer Science\\GitHub\\MazeSolver\\Mazes\\Small\\maze1.png");
		Path p = new Path(maze);
		p.addPoint(maze.getPoint(0, 1));
		for(Point point : p.getPathArray()) {
			System.out.println(point);
		}
		
	}
	
	
}
