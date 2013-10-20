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
import java.util.Observable;
import javax.imageio.ImageIO;

import com.puzzle.model.Piece;

public class LoadImageTask extends Observable implements Runnable {
	
	private Piece piece;
	private Thread task;
	private String path;
	private PieceBufferOperation operation;
	
	

	public LoadImageTask(Piece piece,String path) {
		this.piece = piece;
		this.path = path;
		this.task = new Thread(this);
	}

	public void start(){
		this.task.start();
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
	
	public VolatileImage loadFromFile(String filename){
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
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void run() {
		String file = path+File.separator+String.valueOf(piece.getId())+".png";
		try {
			VolatileImage buff = loadFromFile(file);
			this.operation = new PieceBufferOperation(this.piece, buff);	
		} catch (Exception e) {
			e.printStackTrace();
		}


		this.setChanged();
		this.notifyObservers(operation);
	}

	public PieceBufferOperation getOperation() {
		return operation;
	}
	
	
	
	

}
