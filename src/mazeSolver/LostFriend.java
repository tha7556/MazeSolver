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
	protected Point previousPoint;
	protected Point facing;
	protected String direction;
	protected Point north, south, east, west, end;
	protected Maze maze;
	protected int stepsTaken;
	protected boolean[][] knownPoints;
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
		knownPoints = new boolean[startMaze.getArray().length][startMaze.getArray()[0].length];
		for(int x = 0; x < knownPoints.length; x++) {
			for(int y = 0; y < knownPoints[0].length; y++) {
				knownPoints[x][y] = false;
			}
		ArrayList<Point> availPoints = getAvailablePoints();
		facing = availPoints.get(0);
		
		}
	}
	/**
	 * Determines whether the mazeRunner has already traveled to the given point or not
	 * @param point The Point in question
	 * @return True if the mazeRunner has already been to the Point, False otherwise
	 */
	public Boolean hasTraversed(Point point) {
		return knownPoints[point.getX()][point.getY()];
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
		if(previousPoint != null) {
			if(previousPoint.equals(north)) {
				facing = south;
				direction = "South";
			}
			else if(previousPoint.equals(east)) {
				facing = west;
				direction = "West";
			}
			else if(previousPoint.equals(south)) {
				facing = north;
				direction = "North";
			}
			else if(previousPoint.equals(west)) {
				facing = east;
				direction = "East";
			}
		}
		return new Point[]{north,east,south,west};
	}
	/**
	 * Used to determine the MazeRunner's options for movement
	 * @return ArrayList<Point> of all adjacent points that are not blocked and have not already been traversed
	 */
	public ArrayList<Point> getAvailablePoints() {
		getSurroundings();
		ArrayList<Point> points = new ArrayList<Point>();
		if(east != null && !east.isBlocked() && !east.hasTraveled())
			points.add(east);
		if(south != null && !south.isBlocked() && !south.hasTraveled())
			points.add(south);
		if(west != null && !west.isBlocked() && !west.hasTraveled())
			points.add(west);
		if(north != null && !north.isBlocked() && !north.hasTraveled())
			points.add(north);
		
		return points;
	}
	/**
	 * Moves the MazeRunner to the specified Point
	 * @param point The Point to move the MazeRunner to
	 * @param override true if MazeRunner needs to move somewhere outside of its adjacent points or to a point that it has already traversed
	 */
	public void moveTo(Point point, boolean override) {
		//System.out.println("Currently at: "+currentPoint);
		stepsTaken++;
		if (getAvailablePoints().contains(point) || (override && !point.isBlocked())) {
			this.previousPoint = currentPoint;
			this.currentPoint = point;
			point.setTraveled(true);
			point.changeColor(Color.BLUE);
			getSurroundings();
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
	public void backtrackTo(Point point) {
		while(!pathTaken.getPathArray().get(pathTaken.size()-1).equals(point)) { 
			pathTaken.deletePoint(pathTaken.size()-1);
			stepsTaken++;
		} //removes points from path until it encounters the point
		point.setTraveled(false);
		//System.out.println("Backtracked to: "+point);
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
		int j = 0;
		while(!currentPoint.equals(end)) {
			j++;
			calculateMove();
			if(fileName != null) {
				BufferedImage img = new BufferedImage(maze.getImage().getWidth(), maze.getImage().getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
			    Graphics g = img.getGraphics();
			    g.drawImage(maze.getImage(), 0, 0, null);
			    g.dispose();
			    String num = "";
				for(int size = (j+"").length(); size < 8; size++)
					num += "0";
				num += j;
				ImageWriter writer = new ImageWriter(img,fileName+num+".jpg",j);
				writer.start();
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
			j++;
			try {
				Thread.sleep((long)waitTime/2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			p.changeColor(Color.green);
			
			if(fileName != null) {
				BufferedImage img = new BufferedImage(900, 900, maze.getImage().getType());
			    Graphics g = img.getGraphics();
			    g.drawImage(maze.getImage().getScaledInstance(900, 900, Image.SCALE_SMOOTH), 0, 0, null);
			    g.dispose();
			    String num = "";
				for(int size = (j+"").length(); size < 8; size++)
					num += "0";
				num += j;
				ImageWriter writer = new ImageWriter(img,fileName+num+".jpg",j);
				writer.start();
			}
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
		int joAvgSteps = 0, rightAvgSteps = 0, leftAvgSteps = 0;
		double joAvgTime = 0.0, rightAvgTime = 0.0, leftAvgTime = 0.0;
		for(Maze m : mazes) {
			JunctionOriginationFriend jo = new JunctionOriginationFriend(1,1,m.getMazeWidth()-2, m.getMazeHeight() - 2, m);
			WallFollowerFriend rightFriend = new WallFollowerFriend(1,1,m.getMazeWidth()-2, m.getMazeHeight() - 2, m,true);
			WallFollowerFriend leftFriend = new WallFollowerFriend(1,1,m.getMazeWidth()-2, m.getMazeHeight() - 2, m,false);
			
			long startTime = System.nanoTime();
			jo.solveMaze();
			double totalTime = (System.nanoTime() - startTime)/1000000000.0;
			joAvgTime += totalTime;
			joAvgSteps += jo.getStepsTaken();
			System.out.println("Jo solved the maze in: "+jo.getStepsTaken()+" steps, in: "+(totalTime+" seconds"));
			m.reset();
			startTime = System.nanoTime();
			rightFriend.solveMaze();
			totalTime = (System.nanoTime() - startTime)/1000000000.0;
			rightAvgTime += totalTime;
			rightAvgSteps += rightFriend.getStepsTaken();
			System.out.println("RightFriend solved the maze in: "+rightFriend.getStepsTaken()+" steps, in: "+(totalTime+" seconds"));
			m.reset();
			startTime = System.nanoTime();
			leftFriend.solveMaze();
			totalTime = (System.nanoTime() - startTime)/1000000000.0;
			leftAvgTime += totalTime;
			leftAvgSteps += leftFriend.getStepsTaken();
			System.out.println("LeftFriend solved the maze in: "+leftFriend.getStepsTaken()+" steps, in: "+(totalTime+" seconds"));
		}
		System.out.println("\n\nJo: Average of: " + (joAvgSteps / mazes.size())+ " steps per maze, in an average of: "+(joAvgTime / mazes.size())+" seconds");
		System.out.println("RightFriend: Average of: " + (rightAvgSteps / mazes.size())+ " steps per maze, in an average of: "+(rightAvgTime / mazes.size())+" seconds");
		System.out.println("LeftFriend: Average of: " + (leftAvgSteps / mazes.size())+ " steps per maze, in an average of: "+(leftAvgTime / mazes.size())+" seconds");
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
			//System.out.println("Small"+file.getName());
			Maze m = new Maze(file.getPath(), false);
			smallMazes.add(m);
		}
		folder = new File("Mazes\\Medium");
		for(File file : folder.listFiles()) {
			//System.out.println("Medium"+file.getName());
			Maze m = new Maze(file.getPath(), false);
			mediumMazes.add(m);
		}
		folder = new File("Mazes\\Large");
		for(File file : folder.listFiles()) {
			//System.out.println("Large"+file.getName());
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
		
		testFriends(smallMazes);
		
		
		
	}
	
	
}
