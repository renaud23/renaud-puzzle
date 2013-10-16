package com.puzzle.view.tool;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;




public class SimpleImageLoader  {

	
	public Image getImage(String path) {
		Image image = null;
		try {
			image =  this.loadFromFile(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return image;
	}

	
	
	
	private VolatileImage loadFromFile(String filename) throws IOException {
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
		}catch (IIOException e) {
			System.out.println("erreur chargement "+filename);
			e.printStackTrace();
			return null;
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
}