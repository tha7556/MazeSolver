package mazeGenerator;
import java.awt.Color;
/**
 * A single Point in the Maze
 */
public class Point {
	private int x, y;
	private Boolean traveled, blocked;
	private Maze maze;
	/**
	 * Creates a new Point in the Maze
	 * @param x The x coordinate of the Point
	 * @param y The y coordinate of the Point
	 * @param blocked true if it's a wall, false if it's open
	 * @param maze The Maze that the Point will be in
	 */
	public Point(int x, int y, boolean blocked, Maze maze) {
		this.x = x;
		this.y = y;
		traveled = false;
		this.blocked = blocked;
		this.maze = maze;
	}
	/**
	 * Gets the x coordinate of the Point
	 * @return The x coordinate of the Point
	 */
	public Integer getX() {
		return x;
	}
	/**
	 * Gets the y coordinate of the Point
	 * @return The y coordinate of the Point
	 */
	public Integer getY() { 
		return y;
	}
	/**
	 * Changes the x coordinate of the Point
	 * @param x The new x coordinate of the Point
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * Changes the y coordinate of the Point
	 * @param y The new y coordinate of the Point
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * Gets the value of the traveled boolean
	 * @return true if the MazeRunner has traversed this Point, false otherwise
	 */
	public Boolean hasTraveled() {
		return traveled;
	}
	/**
	 * Alters the value of the traveled boolean
	 * @param traveled true if MazeRunner has traversed this Point, false otherwise
	 */
	public void setTraveled(boolean traveled) {
		this.traveled = traveled;
	}
	/**
	 * Gets the value of the blocked boolean
	 * @return true if the Point is a wall, false otherwise
	 */
	public boolean isBlocked() {
		return blocked;
	}
	/**
	 * Changes the color on the MazeDisplay of this Point
	 * @param color The Color to change the Point to
	 */
	public void changeColor(Color color) {
		maze.changeColor(this, color);
	}
	/**
	 * Represents the Point as (x,y)
	 */
	public String toString() {
		return "("+x+","+y+")";
	}
}
