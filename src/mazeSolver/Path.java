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
	public void addPoint(int x, int y) {
		Point coordinates = maze.getPoint(x,y);
		pathArray.add(coordinates);
	}
	public void deletePoint(int location) {
		pathArray.remove(location);
	}
	public static void main(String[] args) {
		Path p = new Path(new Maze("C:\\\\Documents\\\\\\\\GitHub\\\\\\\\MazeSolver\\\\\\\\Maze.png"));
		p.addPoint(0, 2);
		for(Point point : p.getPathArray()) {
			System.out.printf("{%d,%d}\n",point.getX(),point.getY());
		}
		
	}
	
	
}
