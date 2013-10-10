package com.puzzle.view.tool;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;




public class ImageMemoryManager extends Thread implements ImageProvider{
	
//	private static String PATH_IMAGES = "data/images/";
	
	
	private String path;
	
	private static ImageMemoryManager instance;
	
	private Map<Integer, SoftReference<VolatileImage>> images;
	
	


	
	public static ImageMemoryManager getInstance(){
		if(ImageMemoryManager.instance == null){
			ImageMemoryManager.instance = new ImageMemoryManager();
		}
		return ImageMemoryManager.instance;
	}

	private ImageMemoryManager(){
		this.images = new HashMap<Integer, SoftReference<VolatileImage>>();
	}


	public VolatileImage getImage(int code) {
		if(this.images.get(code)== null ||
			this.images.get(code).get() == null)
			this.reload(code);
		return this.images.get(code).get();
	}


	public void reload(int code){
		String file = path+File.separator+String.valueOf(code)+".png";
		try {
			VolatileImage buff = loadFromFile(file);
			this.images.put(code,new SoftReference<VolatileImage>(buff));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public VolatileImage loadFromFile(String filename) throws IOException {
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
	
	
	public void clear(){
		this.images.clear();
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
