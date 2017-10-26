package mazeGenerator;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import javax.imageio.ImageIO;
/**
 * Used to quickly write multiple Maze images to files 
 *
 */
public class ImageWriter implements Runnable{
	private Image img;
	private String location;
	private Thread thread;
	private int current;
	/**
	 * Creates a new ImageWriter thread to write a single Maze Image
	 * @param img The Maze image to write
	 * @param location The fileLocation
	 * @param current The number of this Image
	 */
	public ImageWriter(Image img, String location,int current) {
		this.img = img;
		this.location = location;
		this.current = current;
		thread = new Thread(this,location+" thread"+current);
	}
	/**
	 * Writes the Maze Image to a File
	 */
	public void writeImageToFile() {
		BufferedImage bimg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		if(img instanceof BufferedImage) {
			bimg = (BufferedImage)img;
		}
		else {
		    Graphics2D bGr = bimg.createGraphics();
		    bGr.drawImage(img, 0, 0, null);
		    bGr.dispose();
		}
		File file = new File(location);
		try {
			ImageIO.write(bimg, "png", file);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Gets the Thread that this is running on
	 * @return The Thread of the ImageWriter
	 */
	public Thread getThread() {
		return thread;
	}
	@Override
	public void run() {
		writeImageToFile();
		System.out.println("Finished: "+current);
	}
	/**
	 * Automatically makes multiple ImageWriters to write many images at once
	 * @param imgs The ArrayList of Images to write
	 * @param location The File location for the Images
	 */
	public static void writeImagesToFile(ArrayList<Image> imgs, String location) {
		int threadCount = 0;
		for(int i = 0; i < imgs.size(); i++) {
			threadCount = getThreadCount();
			ImageWriter iw = new ImageWriter(imgs.get(i),location+(i+1)+".png",(i+1));
			while(threadCount > 100) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				threadCount = getThreadCount();
			}
			System.out.println("Starting: " +(i+1) + " of: " + imgs.size());
			iw.getThread().start();
			threadCount++;
		}
	}
	/**
	 * Gets the current number of running Threads
	 * @return The current number of running Threads
	 */
	public static int getThreadCount() {
		int count = 0;
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		for(Thread t : threadSet) {
			if(t.getName().contains("thread"))
				count++;
		}
		return count;
	}
	public static void main(String[] args) {
	}
}
