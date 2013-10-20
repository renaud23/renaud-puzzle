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

import com.puzzle.model.Piece;


public class PieceImageProvider implements ImageProvider<Piece> {
	
	
	private String path;
	private Map<Integer, SoftReference<PieceBufferOperation>> images;
	

	public PieceImageProvider(String path){
		this.path = path;
		this.images = new HashMap<Integer, SoftReference<PieceBufferOperation>>();
	}


	public PieceBufferOperation getImage(Piece piece) {
		
		if(this.images.get(piece.getId())== null ||
			this.images.get(piece.getId()).get() == null){
			this.reload(piece);
			
		}
		return this.images.get(piece.getId()).get();
	}


	public void reload(Piece piece){
		String file = path+File.separator+String.valueOf(piece.getId())+".png";
		try {
			VolatileImage buff = loadFromFile(file);
			PieceBufferOperation pbo = new PieceBufferOperation(piece, buff);
			
			this.images.put(piece.getId(),new SoftReference<PieceBufferOperation>(pbo));
			
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
