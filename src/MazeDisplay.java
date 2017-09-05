import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

public class MazeDisplay extends JFrame {
	private static final long serialVersionUID = 1L;
	private Maze maze;
	public MazeDisplay(Maze maze) {
		this.maze = maze;
		setSize((int)maze.getBounds().getWidth(), (int)maze.getBounds().getHeight());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(maze);
		setVisible(true);
	}
	public void paint(Graphics g) {
		super.paint(g);		
	}
	public Maze getMaze() {
		return maze;
	}
	public void changeColor(Point point, Color color) {
		maze.getImage().setRGB(point.getX(), point.getY(), color.getRGB());
		this.repaint();
	}
	public static void main(String[] args) {
		Maze m = new Maze("E:\\\\Computer Science\\\\GitHub\\\\MazeSolver\\\\Maze.png");
		//Maze m = new Maze("C:\\\\Documents\\\\\\\\GitHub\\\\\\\\MazeSolver\\\\\\\\Maze.png");
		MazeDisplay d = new MazeDisplay(m);
		d.changeColor(m.getPoint(0,3), Color.BLUE);
	}

}
