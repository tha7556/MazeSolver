package mazeGenerator;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

/**
 * The JFrame that displays the Maze
 */
public class MazeDisplay extends JFrame {
	private static final long serialVersionUID = 1L;
	private Maze maze;
	/**
	 * Creates and displays a Maze in a window
	 * @param maze
	 */
	public MazeDisplay(Maze maze,boolean visible) {
		this.maze = maze;
		setSize((int)maze.getBounds().getWidth(), (int)maze.getBounds().getHeight());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(maze);
		setVisible(visible);
	}
	/**
	 * Draws the Component onto the Window, The JFrame calls this method automatically
	 */
	public void paint(Graphics g) {
		super.paint(g);		
	}
	/**
	 * Gets the Maze that it is displaying
	 * @return
	 */
	public Maze getMaze() {
		return maze;
	}
	/**
	 * Changes the Color at the given Point
	 * @param point The Point being referenced
	 * @param color The Color to change the Point to
	 */
	public void changeColor(Point point, Color color) {
		maze.getImage().setRGB(point.getX(), point.getY(), color.getRGB());
		this.repaint();
	}
	public static void main(String[] args) {
		Maze m = new Maze("Mazes\\Small\\maze1.png");
	}

}
