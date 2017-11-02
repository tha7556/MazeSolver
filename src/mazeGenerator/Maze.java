package mazeGenerator;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * The Maze that the MazeRunner will attempt to solve
 *
 */
public class Maze extends JComponent{

	private static final long serialVersionUID = 1L;
	private Point[][] mazeArray;
	private BufferedImage image;
	private int width, height;
	private MazeDisplay display;
	/**
	 * Creates and Displays a Maze read in from an image file where Black = a wall and White = empty space
	 * @param fileName The String to the image file to be read in
	 */
	public Maze(String fileName, boolean visible) {
		try {
			image = ImageIO.read(new File(fileName));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		height = image.getHeight();
		width = image.getWidth();
		setSize(930,930);
		mazeArray = new Point[height][width];
		for(int w = 0; w < width; w++) {
			for(int h = 0; h < height; h++) {
				int pixel = image.getRGB(w, h);
				int red = (pixel >> 16) & 0xff;
 				int green = (pixel >> 8) & 0xff;
 				int blue = (pixel) & 0xff;
 				if((red == 0) && (green == 0) && (blue == 0))
 					mazeArray[w][h] = new Point(w,h,true,this);
 				else
 					mazeArray[w][h] = new Point(w,h,false,this);
			}
		}
		if(visible)
			display = new MazeDisplay(this,visible);
	}
	public Maze(String fileName) {
		this(fileName,true);
	}
	/**
	 * Gets the matrix of Points that represents the Maze
	 * @return Point[][] representing the Maze
	 */
	public Point[][] getArray() {
		return mazeArray;
	}
	/**
	 * Gets the MazeDisplay
	 * @return The MazeDisplay of the Maze
	 */
	public MazeDisplay getDisplay() {
		return display;
	}
	/**
	 * Gets the height of the Maze (different from getHeight() which returns the height of this JComponent)
	 * @return The height of the Maze
	 */
	public Integer getMazeHeight() {
		return height;
	}
	/**
	 * Gets the Width of the Maze (different from getWidth() which returns the width of this JComponent)
	 * @return The width of the Maze
	 */
	public Integer getMazeWidth() {
		return width;
	}
	/**
	 * Gets the image that is read in to make the Maze
	 * @return The image used to make the Maze
	 */
	public BufferedImage getImage() {
		return image;
	}
	/**
	 * Prints the Maze to the console where 1 = wall and 0 = empty space
	 */
	public void printArray() {
		for(int h = 0; h < height; h++) {
			for(int w = 0; w < width; w++) {
				if(mazeArray[h][w].isBlocked())
					System.out.print("1  ");
				else
					System.out.print("0  ");
			}
			System.out.println();
		}
	}
	/**
	 * Gets the Point at the specified coordinates
	 * @param x The x coordinate of the Point
	 * @param y The y coordinate of the Point
	 * @return The desired Point
	 */
	public Point getPoint(int x, int y) {
		if(x < width && x > -1 && y < height && y > -1)
			return mazeArray[x][y];
		return null;
	}
	public int getArea() {
		return width * height;
	}
	public void changeColor(Point point, Color color) {
		image.setRGB(point.getX(), point.getY(), color.getRGB());
		this.repaint();
	}
	/**
	 * Used to draw the Maze in the MazeDisplay
	 */
	public void paintComponent(Graphics g) {
		Image drawImage = image.getScaledInstance(this.getWidth(),this.getHeight(),BufferedImage.SCALE_SMOOTH);
		g.drawImage(drawImage, 0, 0, null);
	}
	public static int[] analyzeMaze(Maze maze) {
		int[] degrees = new int[5];
		for(int i = 0; i < degrees.length; i++)
			degrees[i] = 0;
		for(Point points[] : maze.getArray()) {
			for(Point point : points) {
				int degree = point.getDegree();
				if(!point.isBlocked())
					degrees[degree] += 1;
			}
		}
		return degrees;
	}
	public static void main(String[] args) {
		String[] names = new String[] {"Small","Medium","Large","Crazy"};
		Maze maze = null;
		for(String size : names) {
			double[] degrees = new double[5];
			for(int i = 0; i < degrees.length; i++)
				degrees[i] = 0.0;
			for(int i = 1; i < 51; i++) {
				maze = new Maze("Mazes\\"+size+"\\maze"+i+".png",false);
				int[] dgs = Maze.analyzeMaze(maze);
				for(int j = 1; j < degrees.length; j++) {
					degrees[j] += dgs[j];
				}
						
			}
			System.out.println(size+" with area: "+maze.getArea());
			for(int i = 1; i < degrees.length; i++) {
				degrees[i] /= 50.0;
				System.out.println("\tDegree "+i+": "+degrees[i]);
			}
			System.out.println();
		}
		
	}
}
