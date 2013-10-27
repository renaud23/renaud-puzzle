package com.puzzle.view.tool.provider;

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
import java.util.List;
import java.util.Observable;

import javax.imageio.ImageIO;

import com.puzzle.model.Piece;
import com.puzzle.view.tool.ImageLoadException;


public class PieceLoader extends Observable implements Runnable{
	

	private Thread task;
	private final List<Piece> file;
	
	
	private static PieceLoader instance;
	
	private PieceLoader(){
		this.task = new Thread(this);
		this.file = Collections.synchronizedList(new ArrayList<Piece>());
		this.start();
	}
	
	public static PieceLoader getInstance(){
		if(instance == null)instance = new PieceLoader();
		return instance;
	}

	@Override
	public void run() {
		while(true){
			if(!this.file.isEmpty()){
				
				Piece p;
				
				synchronized (this.file) {
					p = this.file.remove(0);
				}
				
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
		}
		
	}
	
	
	public void start(){
		this.task.start();
	}

	public void load(Piece piece){
		synchronized (this.file) {
			if(!this.file.contains(piece))
				this.file.add(piece);
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
