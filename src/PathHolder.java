import java.util.ArrayList;


public class PathHolder {
	private int xCord;
	private int yCord;
	private ArrayList<int []> holdMe;
	
	
	public PathHolder(int x, int y) {
		this.xCord = x;
		this.yCord = y;
		holdMe = new ArrayList<int []>();
		
	}
	
	
	public int xGetter() {
		return xCord;
		
	}
	
	public int yGetter() {
		return yCord;
		
	}
	
	public ArrayList<int[]> arrayGetter() {
		return holdMe;
		
	}
	
	
}
