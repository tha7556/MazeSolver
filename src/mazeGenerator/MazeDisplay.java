package mazeGenerator;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

import mazeSolver.LostFriend;

public class MazeDisplay extends JFrame {
	private static final long serialVersionUID = 1L;
	private Maze maze;
	public MazeDisplay(Maze maze) {
		this.maze = maze;
		for(Point[] pArr : maze.getArray())
			for(Point p : pArr)
				p.setMazeDisplay(this);
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
		LostFriend lf = new LostFriend(3,0,m);
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lf.moveTo(m.getPoint(3, 1));
	}

}
