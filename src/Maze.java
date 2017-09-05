import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Maze extends JComponent{

	private static final long serialVersionUID = 1L;
	private boolean[][] mazeArray;
	private BufferedImage image;
	private int width, height;
	public Maze(String fileName) {
		try {
			image = ImageIO.read(new File(fileName));
		}catch(Exception e) {
			e.printStackTrace();
		}
		height = image.getHeight();
		width = image.getWidth();
		mazeArray = new boolean[height][width];
		for(int h = 0; h < height; h++) {
			for(int w = 0; w < width; w++) {
				int pixel = image.getRGB(w, h);
				int red = (pixel >> 16) & 0xff;
 				int green = (pixel >> 8) & 0xff;
 				int blue = (pixel) & 0xff;
 				if((red == 0) && (green == 0) && (blue == 0))
 					mazeArray[h][w] = true;
 				else
 					mazeArray[h][w] = false;
			}
		}
	}
	public void paintComponent(Graphics g) {
		Image drawImage = image.getScaledInstance(930,620,BufferedImage.SCALE_SMOOTH);
		g.drawImage(drawImage, 0, 0, null);
	}
	public boolean[][] getArray() {
		return mazeArray;
	}
	public Integer getMazeHeight() {
		return height;
	}
	public Integer getMazeWidth() {
		return width;
	}
	public void printArray() {
		for(int h = 0; h < height; h++) {
			for(int w = 0; w < width; w++) {
				if(mazeArray[h][w])
					System.out.print("1  ");
				else
					System.out.print("0  ");
			}
			System.out.println();
		}
	}
	public BufferedImage getImage() {
		return image;
	}
	public static void main(String[] args) {
		Maze m = new Maze("E:\\\\Computer Science\\\\GitHub\\\\MazeSolver\\\\Maze.png");
		//Maze m = new Maze("C:\\\\Documents\\\\GitHub\\\\MazeSolver\\\\Maze.png");
		m.getArray();
		m.printArray();
		
	}
}
