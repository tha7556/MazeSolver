package mazeSolver;

import mazeGenerator.Maze;
import mazeGenerator.Point;

public class WallFollowerFriend extends LostFriend{

	public WallFollowerFriend(int startx, int starty, int endx, int endy, Maze startMaze) { 
		super(startx, starty, endx, endy, startMaze);
	}

	@Override
	public Point calculateMove() { //TODO: Implement
		return null;
	}

}
