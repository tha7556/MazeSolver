package mazeSolver;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
	 * @throws IOException 
	 */
	public static void testFriends(File[] files) throws IOException {
		int joAvgSteps = 0, rightAvgSteps = 0, leftAvgSteps = 0, distanceAvgSteps = 0;
		double joAvgTime = 0.0, rightAvgTime = 0.0, leftAvgTime = 0.0, distanceAvgTime = 0.0;
		File joFile = new File ("JoRunner.csv"); 
		FileWriter joFWriter = new FileWriter (joFile); 
		PrintWriter joPWriter = new PrintWriter (joFWriter);
		
		File rightFile = new File ("rightRunner.csv"); 
		FileWriter rightFWriter = new FileWriter (rightFile); 
		PrintWriter rightPWriter = new PrintWriter (rightFWriter);
		
		File leftFile = new File ("leftRunner.csv"); 
		FileWriter leftFWriter = new FileWriter (leftFile); 
		PrintWriter leftPWriter = new PrintWriter (leftFWriter);
		
		File distanceFile = new File ("distanceRunner.csv"); 
		FileWriter distanceFWriter = new FileWriter (distanceFile); 
		PrintWriter distancePWriter = new PrintWriter (distanceFWriter);
		
		joPWriter.println("MazeNumber,Size,Steps,Time");
		rightPWriter.println("MazeNumber,Size,Steps,Time");
		leftPWriter.println("MazeNumber,Size,Steps,Time");
		distancePWriter.println("MazeNumber,Size,Steps,Time");
		int num = 0;
		for(File file : files) {
			
			System.out.println("reading: "+file);
			Maze m = Maze.readFromFile(file);
			System.out.println("done reading: "+file);
			
			String size = "";
			if(m.getArea() == 2601)
				size = "Small";
			else if(m.getArea() == 10201)
				size = "Medium";
			else if(m.getArea() == 40401)
				size = "Large";
			else if(m.getArea() == 2152089)
				size = "Crazy";
			num++;
			System.out.println("Starting: "+m.getArea()+" "+num);
			JunctionOriginationFriend jo = new JunctionOriginationFriend(1,1,m.getMazeWidth()-2, m.getMazeHeight() - 2, m);
			WallFollowerFriend rightFriend = new WallFollowerFriend(1,1,m.getMazeWidth()-2, m.getMazeHeight() - 2, m,true);
			WallFollowerFriend leftFriend = new WallFollowerFriend(1,1,m.getMazeWidth()-2, m.getMazeHeight() - 2, m,false);
			ManhattenDistanceFriend robert = new ManhattenDistanceFriend(1,1,m.getMazeWidth()-2, m.getMazeHeight() - 2, m);
			
			long startTime = System.nanoTime();
			jo.solveMaze();
			double totalTime = (System.nanoTime() - startTime)/1000000000.0;
			joAvgTime += totalTime;
			joAvgSteps += jo.getStepsTaken();
			System.out.println("Jo solved the maze in: "+jo.getStepsTaken()+" steps, in: "+(totalTime+" seconds"));
			joPWriter.println(num+","+size+","+jo.getStepsTaken()+","+totalTime);
			
			m.reset();
			startTime = System.nanoTime();
			rightFriend.solveMaze();
			totalTime = (System.nanoTime() - startTime)/1000000000.0;
			rightAvgTime += totalTime;
			rightAvgSteps += rightFriend.getStepsTaken();
			System.out.println("RightFriend solved the maze in: "+rightFriend.getStepsTaken()+" steps, in: "+(totalTime+" seconds"));
			rightPWriter.println(num+","+size+","+rightFriend.getStepsTaken()+","+totalTime);
			
			m.reset();
			startTime = System.nanoTime();
			leftFriend.solveMaze();
			totalTime = (System.nanoTime() - startTime)/1000000000.0;
			leftAvgTime += totalTime;
			leftAvgSteps += leftFriend.getStepsTaken();
			System.out.println("LeftFriend solved the maze with area: "+ m.getArea()+" "+num + " in: "+leftFriend.getStepsTaken()+" steps, in: "+(totalTime+" seconds"));
			leftPWriter.println(num+","+size+","+leftFriend.getStepsTaken()+","+totalTime);
			
			m.reset();
			startTime = System.nanoTime();
			robert.solveMaze();
			totalTime = (System.nanoTime() - startTime)/1000000000.0;
			distanceAvgTime += totalTime;
			distanceAvgSteps += robert.getStepsTaken();
			System.out.println("Robert solved the maze with area: "+ m.getArea()+" "+num + " in: "+robert.getStepsTaken()+" steps, in: "+(totalTime+" seconds"));
			distancePWriter.println(num+","+size+","+robert.getStepsTaken()+","+totalTime);
			
			System.out.println("\n----------------------------\n");
		}
		System.out.println("\n\nJo: Average of: " + (joAvgSteps / files.length)+ " steps per maze, in an average of: "+(joAvgTime / files.length)+" seconds");
		System.out.println("RightFriend: Average of: " + (rightAvgSteps / files.length)+ " steps per maze, in an average of: "+(rightAvgTime / files.length)+" seconds");
		System.out.println("LeftFriend: Average of: " + (leftAvgSteps / files.length)+ " steps per maze, in an average of: "+(leftAvgTime / files.length)+" seconds");
		System.out.println("DistanceFriend: Average of: " + (distanceAvgSteps / files.length)+ " steps per maze, in an average of: "+(distanceAvgTime / files.length)+" seconds");
		
		joPWriter.close();
		leftPWriter.close();
		rightPWriter.close();
		distancePWriter.close();
		joFWriter.close();
		leftFWriter.close();
		rightFWriter.close();
		distanceFWriter.close();
	}
	/**
	 * The function that differentiates the different types of MazeRunner.</p>
	 * Calculates the next Point to move to and also calls the moveTo(Point p) function
	 * @return The next Point that the MazeRunner is moving to
	 */
	public abstract Point calculateMove();
	public static void main(String[] args) {
		File[] files = new File("Mazes\\SerializedObjects").listFiles();
		if(files.length < 200) { //write mazes
			//Maze.writeAllMazesFromFolder("Mazes\\Small", "Mazes\\SerializedObjects\\Small");
			//Maze.writeAllMazesFromFolder("Mazes\\Medium", "Mazes\\SerializedObjects\\Medium");
			//Maze.writeAllMazesFromFolder("Mazes\\Large", "Mazes\\SerializedObjects\\Large");
			Maze.writeAllMazesFromFolder("Mazes\\Crazy", "Mazes\\SerializedObjects\\Crazy"); //Takes a long time/memory
		}
		else { //test mazes
			try {
				File[] fs = new File[files.length];
				int j = 0;
				for(int i = 0; i < fs.length; i++) {
					if(files[i].getName().contains("Small")) {
						fs[j] = files[i];
						j++;
					}
				}
				for(int i = 0; i < fs.length; i++) {
					if(files[i].getName().contains("Medium")) {
						fs[j] = files[i];
						j++;
					}
				}
				for(int i = 0; i < fs.length; i++) {
					if(files[i].getName().contains("Large")) {
						fs[j] = files[i];
						j++;
					}
				}
				for(int i = 0; i < fs.length; i++) {
					if(files[i].getName().contains("Crazy")) {
						fs[j] = files[i];
						j++;
					}
				}
				System.out.println("starting tests");
				LostFriend.testFriends(fs);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	
}
