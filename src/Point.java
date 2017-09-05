
public class Point {
	private int x, y;
	public Boolean traveled;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		traveled = false;
		
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
	public boolean isBlocked(boolean[][] maze) {
		return maze[x][y];
	}
}
