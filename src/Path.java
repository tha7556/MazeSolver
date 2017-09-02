import java.util.ArrayList;


public class Path {
	private ArrayList<int []> pathArray;
	
	public Path() {
		pathArray = new ArrayList<int []>();	
	}
	public ArrayList<int[]> getPathArray() {
		return pathArray;
	}
	public void addPoint(int x, int y, int location) {
		int[] coordinates = {x,y};
		pathArray.add(location,coordinates);
	}
	public void addPoint(int x, int y) {
		addPoint(x,y,pathArray.size());
	}
	public void deletePoint(int location) {
		pathArray.remove(location);
	}
	public static void main(String[] args) {
		Path p = new Path();
		p.addPoint(0, 2);
		for(int[] arr : p.getPathArray()) {
			System.out.printf("{%d,%d}\n",arr[0],arr[1]);
		}
		
	}
	
	
}
