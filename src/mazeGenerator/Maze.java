package mazeGenerator;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Maze extends JComponent{

	private static final long serialVersionUID = 1L;
	private Point[][] mazeArray;
	private BufferedImage image;
	private int width, height;
	private MazeDisplay display;
	public Maze(String fileName) {
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
		display = new MazeDisplay(this);
	}
	public Point[][] getArray() {
		return mazeArray;
	}
	public MazeDisplay getDisplay() {
		return display;
	}
	public Integer getMazeHeight() {
		return height;
	}
	public Integer getMazeWidth() {
		return width;
	}
	public BufferedImage getImage() {
		return image;
	}
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
	public Point getPoint(int x, int y) {
		return mazeArray[x][y];
	}
	public void paintComponent(Graphics g) {
		Image drawImage = image.getScaledInstance(this.getWidth(),this.getHeight(),BufferedImage.SCALE_SMOOTH);
		g.drawImage(drawImage, 0, 0, null);
	}
	public static void main(String[] args) {
		Maze m = new Maze("E:\\Computer Science\\GitHub\\MazeSolver\\Mazes\\Small\\maze1.png");
		//Maze m = new Maze("C:\\\\Documents\\\\GitHub\\\\MazeSolver\\\\Maze.png");
		m.printArray();
		
	}
}
