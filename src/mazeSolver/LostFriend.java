package mazeSolver;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;


import mazeGenerator.Maze;
import mazeGenerator.Point;
import mazeGenerator.ImageWriter;
/**
 * Abstract MazeRunner Class, used to implement generic functions that will be used by the different versions of it
 *
 */
public abstract class LostFriend {

	protected Path pathTaken;
	protected Point startingPoint;
	protected Point currentPoint;
	protected Point facing;
	protected Point north, south, east, west, end;
	protected Maze maze;
	protected int stepsTaken;
	/**
	 * Constructor that should be extended for each instance of the MazeRunner
	 * @param startx The starting x coordinate for the MazeRunner
	 * @param starty The starting y coordinate for the MazeRunner
	 * @param endx The x coordinate where the Maze exit is
	 * @param endy The y coordinate where the Maze exit is
	 * @param startMaze The Maze that it will try to solve
	 */
	public LostFriend(int startx, int starty, int endx, int endy, Maze startMaze) {
		this.maze = startMaze;
		this.startingPoint = maze.getPoint(startx,starty);
		this.currentPoint = maze.getPoint(startx,starty);
		end = maze.getPoint(endx, endy);
		this.startingPoint.changeColor(Color.BLUE);
		end.changeColor(Color.red);
		getSurroundings();
		pathTaken = new Path(maze);
		stepsTaken = 0;
		ArrayList<Point> availPoints = getAvailablePoints();
		facing = availPoints.get(0);
		
		}
	/**
	 * Used to get pointers to all of the adjacent points to the MazeRunner
	 * @return Point[] containing all points that are adjacent to the MazeRunner
	 */
	public Point[] getSurroundings() {
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
		return new Point[]{north,east,south,west};
	}
	/**
	 * Used to determine the MazeRunner's options for movement
	 * @return ArrayList<Point> of all adjacent points that are not blocked and have not already been traversed
	 */
	public ArrayList<Point> getAvailablePoints() {
		getSurroundings();
		ArrayList<Point> points = new ArrayList<Point>();
		if(north != null && !north.isBlocked() && !north.hasTraveled())
			points.add(north);
		if(east != null && !east.isBlocked() && !east.hasTraveled())
			points.add(east);
		if(south != null && !south.isBlocked() && !south.hasTraveled())
			points.add(south);
		if(west != null && !west.isBlocked() && !west.hasTraveled())
			points.add(west);
		
		return points;
	}
	/**
	 * Moves the MazeRunner to the specified Point
	 * @param point The Point to move the MazeRunner to
	 * @param override true if MazeRunner needs to move somewhere outside of its adjacent points or to a point that it has already traversed
	 */
	public void moveTo(Point point, boolean override) {
		System.out.println("Currently at: "+currentPoint);
		stepsTaken++;
		if (getAvailablePoints().contains(point) || (override && !point.isBlocked())) {
			this.currentPoint = point;
			point.setTraveled(true);
			point.changeColor(Color.BLUE);
			getSurroundings();
			ArrayList<Point> availPoints = getAvailablePoints();
			if(availPoints.size() > 0)
				facing = availPoints.get(0);
		}
		else if(point.isBlocked())
			throw new RuntimeException("Point: "+point+" is blocked");
		else if(point.hasTraveled())
			throw new RuntimeException("Point: "+point+" has already been traversed");
		else
			throw new RuntimeException("Cannot move to point: "+point);
	}
	/**
	 * Moves the MazeRunner to the specified Point </p>
	 * The moveTo function that does not need the override variable
	 * @param point The Point to move the MazeRunner to
	 */
	public void moveTo(Point point) {
		moveTo(point,false);
	}
	/**
	 * Gets the number of points that it has traversed
	 * @return The number of Points that the MazeRunner has traversed
	 */
	public int getStepsTaken() {
		return stepsTaken;
	}
	/**
	 * Gets the Point directly above the MazeRunner
	 * @return The Point directly above the MazeRunner
	 */
	public Point getNorth() {
		return north;
	}
	/**
	 * Gets the Point directly below the MazeRunner
	 * @return The Point directly below the MazeRunner
	 */
	public Point getSouth() {
		return south;
	}
	/**
	 * Gets the Point directly to the right the MazeRunner
	 * @return The Point directly to the right the MazeRunner
	 */
	public Point getEast() {
		return east;
	}
	/**
	 * Gets the Point directly to the left the MazeRunner
	 * @return The Point directly to the left the MazeRunner
	 */
	public Point getWest() {
		return west;
	}
	/**
	 * Gets the Point that the MazeRunner started at
	 * @return The Point that the MazeRunner started at
	 */
	public Point getStartingPoint() {
		return startingPoint;
	}
	/**
	 * Gets the Point where the MazeRunner currently is
	 * @return The Point where the MazeRunner currently is
	 */
	public Point getCurrentPoint() {
		return currentPoint;
	}
	
	public Point getFace() {
		return facing;
	}
	/**
	 * Calls calculate move repeatedly until currentPoint and end are the same Points, and then highlights the correct Path in green
	 * @param waitTime Number of milliseconds to wait between movements
	 * @param fileName The name of the file to save the progress images to
	 * @return The final Path that it took to reach the end Point
	 */
	public Path solveMaze(int waitTime,String fileName) {
		ArrayList<Image> imgs = new ArrayList<Image>();
		while(!currentPoint.equals(end)) {
			calculateMove();
			if(fileName != null) {
				BufferedImage img = new BufferedImage(maze.getImage().getWidth(), maze.getImage().getHeight(), maze.getImage().getType());
			    Graphics g = img.getGraphics();
			    g.drawImage(maze.getImage(), 0, 0, null);
			    g.dispose();
				imgs.add(img.getScaledInstance(200, 200, Image.SCALE_SMOOTH));
			}
			if(waitTime > 0) {
				try {
					Thread.sleep((long)waitTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		for(Point p : pathTaken.getPathArray()) {
			try {
				Thread.sleep((long)waitTime/2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			p.changeColor(Color.green);
			
			if(fileName != null) {
				BufferedImage img = new BufferedImage(maze.getImage().getWidth(), maze.getImage().getHeight(), maze.getImage().getType());
			    Graphics g = img.getGraphics();
			    g.drawImage(maze.getImage(), 0, 0, null);
			    g.dispose();
				imgs.add(img.getScaledInstance(200, 200, Image.SCALE_SMOOTH));
			}
		}
		if(fileName != null) {
			//MazeGenerator.writeImagesToFile(imgs, fileName); 
			ImageWriter.writeImagesToFile(imgs,fileName);			
		}
		return pathTaken;
	}
	/**
	 * Calls calculate move repeatedly until currentPoint and end are the same Points, and then highlights the correct Path in green
	 * @return The final Path that it took to reach the end Point
	 */
	public Path solveMaze() {
		return solveMaze(0,null);
	}
	/**
	 * Calls calculate move repeatedly until currentPoint and end are the same Points, and then highlights the correct Path in green
	 * @param waitTime Number of milliseconds to wait between movements
	 * @return The final Path that it took to reach the end Point
	 */
	public Path solveMaze(int waitTime) {
		return solveMaze(waitTime,null);
	} 
	/**
	 * Calls calculate move repeatedly until currentPoint and end are the same Points, and then highlights the correct Path in green
	 * @param fileName The name of the file to save the progress images to
	 * @return The final Path that it took to reach the end Point
	 */
	public Path solveMaze(String fileName) {
		return solveMaze(0,fileName);
	}
	/**
	 * Tests the MazeRunner by putting it through multiple set mazes and comparing results
	 * @param mazes The ArrayList of Mazes to test it with
	 */
	public static void testFriends(ArrayList<Maze> mazes) {
		int avgSteps = 0;
		double avgTime = 0.0;
		for(Maze m : mazes) {
			JunctionOriginationFriend jo = new JunctionOriginationFriend(1,1,m.getMazeWidth()-2, m.getMazeHeight() - 2, m);
			long startTime = System.nanoTime();
			jo.solveMaze();
			double totalTime = (System.nanoTime() - startTime)/1000000000.0;
			avgTime += totalTime;
			
			avgSteps += jo.getStepsTaken();
			System.out.println("Jo solved the maze in: "+jo.getStepsTaken()+" steps, in: "+(totalTime+" seconds"));
		}
		System.out.println("Average of: " + (avgSteps / mazes.size())+ " steps per maze, in an average of: "+(avgTime / mazes.size())+" seconds");
	}
	/**
	 * The function that differentiates the different types of MazeRunner.</p>
	 * Calculates the next Point to move to and also calls the moveTo(Point p) function
	 * @return The next Point that the MazeRunner is moving to
	 */
	public abstract Point calculateMove();
	public static void main(String[] args) {
		ArrayList<Maze> smallMazes = new ArrayList<Maze>();
		ArrayList<Maze> mediumMazes = new ArrayList<Maze>();
		ArrayList<Maze> largeMazes = new ArrayList<Maze>();
		ArrayList<Maze> crazyMazes = new ArrayList<Maze>();
		File folder = new File("Mazes\\Small");
		for(File file : folder.listFiles()) {
			System.out.println("Small"+file.getName());
			Maze m = new Maze(file.getPath(), false);
			smallMazes.add(m);
		}
		folder = new File("Mazes\\Medium");
		for(File file : folder.listFiles()) {
			System.out.println("Medium"+file.getName());
			Maze m = new Maze(file.getPath(), false);
			mediumMazes.add(m);
		}
		folder = new File("Mazes\\Large");
		for(File file : folder.listFiles()) {
			System.out.println("Large"+file.getName());
			Maze m = new Maze(file.getPath(), false);
			largeMazes.add(m);
		}
		/*folder = new File("Mazes\\Crazy");
		File[] files = folder.listFiles();
		for(int i = 0; i < 10; i++) {
			File file = files[i];
			System.out.println("Crazy"+file.getName());
			Maze m = new Maze(file.getPath(), false);
			crazyMazes.add(m);
		}*/
		System.out.println("done loading the mazes\n");
		
		testFriends(largeMazes);
		
		
		
	}
	
	
}
