package mazeGenerator;
import java.awt.Color;

public class Point {
	private int x, y;
	private Boolean traveled, blocked;
	private MazeDisplay mazeDisplay;
	public Point(int x, int y, boolean blocked) {
		this.x = x;
		this.y = y;
		traveled = false;
		this.blocked = blocked;
		
	}
	public Integer getX() {
		return x;
	}
	public Integer getY() { 
		return y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Boolean hasTraveled() {
		return traveled;
	}
	public void setTraveled(boolean traveled) {
		this.traveled = traveled;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setMazeDisplay(MazeDisplay mazeDisplay) {
		this.mazeDisplay = mazeDisplay;
	}
	public MazeDisplay getMazeDisplay() {
		return mazeDisplay;
	}
	public void changeColor(Color color) {
		mazeDisplay.changeColor(this, color);
	}
}
