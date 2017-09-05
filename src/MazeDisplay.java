import java.awt.Color;

import javax.swing.JFrame;

public class MazeDisplay extends JFrame {
	private static final long serialVersionUID = 1L;
	private Maze maze;
	public MazeDisplay(Maze maze) {
		this.maze = maze;
		setSize(945, 665);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(maze);
		setVisible(true);
	}
	public Maze getMaze() {
		return maze;
	}
	public void changeColor(Point point, Color color) {
		maze.getImage().setRGB(point.getX(), point.getY(), color.getRGB());
		this.repaint();
	}
	public static void main(String[] args) {
		new MazeDisplay(new Maze("E:\\\\Computer Science\\\\GitHub\\\\MazeSolver\\\\Maze.png"));
		//new MazeDisplay(new Maze("C:\\\\Documents\\\\\\\\GitHub\\\\\\\\MazeSolver\\\\\\\\Maze.png"));
	}

}
