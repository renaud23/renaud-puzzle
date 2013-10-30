package com.puzzle.view.core.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.SynchronousQueue;

import javax.imageio.ImageIO;

import com.puzzle.model.Piece;
import com.puzzle.view.java.ImageLoadException;


public class PieceLoader extends Observable implements Runnable{
	

	private Thread task;
	private final Queue<Piece> file;
	
	
	private static PieceLoader instance;
	
	private PieceLoader(){
		this.task = new Thread(this);
		this.file =  new ConcurrentLinkedQueue<Piece>();
		this.start();
	}
	
	public static PieceLoader getInstance(){
		if(instance == null)instance = new PieceLoader();
		return instance;
	}

	@Override
	public void run() {
		while(true){
			synchronized (this.file) {
				if(!this.file.isEmpty()){
					
					Piece p  = this.file.poll();
					
					
					String path = p.getPuzzle().getPath()+File.separator+"images"+File.separator+p.getId()+".png";
					
					try {
						VolatileImage img = this.loadFromFile(path);
						PieceBufferOperation operation = new PieceBufferOperation(p, img);
						
						this.setChanged();
						this.notifyObservers(operation);
						
					} catch (ImageLoadException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}// synch
		}
		
	}
	
	
	public void start(){
		this.task.start();
	}

	public void load(Piece piece){
		synchronized (this.file) {
			if(!this.file.contains(piece)){
				this.file.offer(piece);
			}
		}
	}
	
	private VolatileImage createVolatileImage(int width, int height, int transparency) {	
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
		VolatileImage image = null;
		
		image = gc.createCompatibleVolatileImage(width, height, transparency);
	 
		int valid = image.validate(gc);
		
		if (valid == VolatileImage.IMAGE_INCOMPATIBLE) {
			image = this.createVolatileImage(width, height, transparency);
			return image;
		}
		
		return image;
	}
	
	public VolatileImage loadFromFile(String filename) throws ImageLoadException{
		try{

			BufferedImage bimage = ImageIO.read(new File(filename) );
			VolatileImage vimage = createVolatileImage(bimage.getWidth(), bimage.getHeight(), Transparency.TRANSLUCENT);
			Graphics2D g = null;
			
			try {
				g = vimage.createGraphics();
				g.setComposite(AlphaComposite.Src);
				 
				g.setColor(new Color(0,0,0,0));
				g.fillRect(0, 0, vimage.getWidth(), vimage.getHeight());
				
				g.drawImage(bimage,null,0,0);
			} finally {	
				g.dispose();
			}
		
			return vimage;
		}catch (Exception e) {
			throw new ImageLoadException("Impossible de charger une image "+filename, e);
		}
	}

	
	
	
	
	
	
}
