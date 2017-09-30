package mazeGenerator;

import java.util.Collections;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
 
/*
 * recursive backtracking algorithm
 * http://weblog.jamisbuck.org/2010/12/27/maze-generation-recursive-backtracking
 */
public class MazeGenerator {
	private int x;
	private int y;
	private int[][] maze;
 
	public MazeGenerator(int x, int y) {
		this.x = x;
		this.y = y;
		maze = new int[this.x][this.y];
	}
 
	private ArrayList<String> format() {
		ArrayList<String> result = new ArrayList<String>(2*y);
		for (int i = 0; i < y; i++) {
			String temp = "";
			// draw the north edge
			for (int j = 0; j < x; j++) {
				System.out.print((maze[j][i] & 1) == 0 ? "+---" : "+   ");
				temp += (maze[j][i] & 1) == 0 ? "+-" : "+ ";
			}
			System.out.println("+");
			temp += "+";
			result.add(temp);
			// draw the west edge
			temp = "";
			for (int j = 0; j < x; j++) {
				System.out.print((maze[j][i] & 8) == 0 ? "|   " : "    ");
				temp += (maze[j][i] & 8) == 0 ? "| " : "  ";
			}
			System.out.println("|");
			temp += "|";
			result.add(temp);
		}
		// draw the bottom line
		String temp = "";
		for (int j = 0; j < x; j++) {
			System.out.print("+---");
			temp += "+-";
		}
		System.out.println("+");
		temp += "+";
		result.add(temp);
		return result;
	}
 
	private void generateMaze(int cx, int cy) {
		DIR[] dirs = DIR.values();
		Collections.shuffle(Arrays.asList(dirs));
		for (DIR dir : dirs) {
			int nx = cx + dir.dx;
			int ny = cy + dir.dy;
			if (between(nx, x) && between(ny, y) && (maze[nx][ny] == 0)) {
				maze[cx][cy] |= dir.bit;
				maze[nx][ny] |= dir.opposite.bit;
				generateMaze(nx, ny);
			}
		}
	}
	public int[][] getMaze() {
		return maze;
	}
	private static boolean between(int v, int upper) {
		return (v >= 0) && (v < upper);
	}
	public BufferedImage createMaze() {
		maze = new int[this.x][this.y];
		generateMaze(0,0);
		ArrayList<String> sList = format();
		BufferedImage image = new BufferedImage(sList.get(0).length(), sList.size(), BufferedImage.TYPE_INT_ARGB);
		boolean[][] result = new boolean[sList.size()][sList.get(0).length()];
		for(int z = 0; z < sList.size(); z++) {
			//System.out.println(sList.get(z));
			for(int i = 0; i < sList.get(z).length(); i++) {
				result[z][i] = !sList.get(z).substring(i,i+1).equals(" ");
				if(result[z][i])
					image.setRGB(z, i, Color.BLACK.getRGB());
				else
					image.setRGB(z, i, Color.WHITE.getRGB());
			}
		}
		
		return image;
	}
	public BufferedImage createMaze(String fileName) {
		BufferedImage image = createMaze();
		File file = new File(fileName);
		try {
			ImageIO.write(image, "png", file);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return image;
	}
 
	private enum DIR {
		N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
		private final int bit;
		private final int dx;
		private final int dy;
		private DIR opposite;
 
		// use the static initializer to resolve forward references
		static {
			N.opposite = S;
			S.opposite = N;
			E.opposite = W;
			W.opposite = E;
		}
 
		private DIR(int bit, int dx, int dy) {
			this.bit = bit;
			this.dx = dx;
			this.dy = dy;
		}
	};
 
	public static void main(String[] args) {
		int x = 100;
		int y = 100; //x,y is width/height of maze
		MazeGenerator mazeGenerator = new MazeGenerator(x, y);
		BufferedImage image = mazeGenerator.createMaze("E:\\test.png");
	}
 
}