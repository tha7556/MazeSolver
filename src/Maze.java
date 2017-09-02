import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Maze {
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
	public boolean[][] getArray() {
		return mazeArray;
	}
	public Integer getHeight() {
		return height;
	}
	public Integer getWidth() {
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
		m.getArray();
		m.printArray();
		
	}
}
