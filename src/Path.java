import java.util.ArrayList;


public class Path {
	private ArrayList<Point> pathArray;
	
	public Path() {
		pathArray = new ArrayList<Point>();	
	}
	public ArrayList<Point> getPathArray() {
		return pathArray;
	}
	public void addPoint(int x, int y) {
		Point coordinates = new Point(x,y);
		pathArray.add(coordinates);
	}
	public void deletePoint(int location) {
		pathArray.remove(location);
	}
	public static void main(String[] args) {
		Path p = new Path();
		p.addPoint(0, 2);
		for(Point point : p.getPathArray()) {
			System.out.printf("{%d,%d}\n",point.getX(),point.getY());
		}
		
	}
	
	
}
