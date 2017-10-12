package mazeSolver;
import java.util.ArrayList;

import mazeGenerator.Maze;
import mazeGenerator.Point;


public class Path {
	private ArrayList<Point> pathArray;
	private Maze maze;
	
	public Path(Maze maze) {
		this.maze = maze;
		pathArray = new ArrayList<Point>();	
	}
	public ArrayList<Point> getPathArray() {
		return pathArray;
	}
	public void addPoint(Point point) {
		pathArray.add(point);
	}
	public Point deletePoint(int location) {
		Point p = pathArray.get(location);
		pathArray.remove(location);
		return p;
	}
	public int size() {
		return pathArray.size();
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
