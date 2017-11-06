package mazeGenerator;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.Semaphore;

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
	private Semaphore semaphore;
	private boolean done;
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
		semaphore = new Semaphore(100);
		done = false;
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
			ImageIO.write(bimg, "jpg", file);
			semaphore.release();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void start() {
		try {
			semaphore.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		thread.start();
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
			String num = "";
			for(int size = (i+"").length(); size < 8; size++)
				num += "0";
			num += i;
			threadCount = getThreadCount();
			ImageWriter iw = new ImageWriter(imgs.get(i),location+(num)+".png",(i+1));
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
	public void setDone() {
		done = true;
	}
	public int getNum() {
		return current;
	}
	public boolean isDone() {
		return done;
	}
	public static void main(String[] args) {
	}
}
