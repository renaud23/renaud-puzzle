package com.puzzle.bigTexture.core;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import com.puzzle.bigTexture.tool.ImageLoadException;
import com.puzzle.bigTexture.tool.JImageBuffer;
import com.puzzle.bigTexture.tool.ImageFileTool;

public class BigTexture {
	
	private PrintStream out = System.out;
	private File input;
	private File output;
	private int largeurTexture;
	private int hauteurTexture;
	
	private String nameText;
	private int nbImages;
	private JImageBuffer textureBuffer;
	private int textureIndex = 1;
	
	private List<Piece> pieces = new ArrayList<>();
	
	
	public BigTexture(File input, File output, int largeurTexture,
			int hauteurTexture) {
		this.input = input;
		this.output = output;
		this.largeurTexture = largeurTexture;
		this.hauteurTexture = hauteurTexture;
	}


	public void execute(){
		out.println("--debut--");
		
		out.println("largeur texture : "+this.largeurTexture);
		out.println("hauteur texture : "+this.hauteurTexture);
		
		this.compterImage();
		
		int x = 0, y = 0;
		int yRef = 0;
		this.createTexture();
		int i = 1;
		while(i<=this.nbImages){
			Image  img = loadImage(i);
			
			if((x+img.getWidth(null)) > this.textureBuffer.getLargeur()   ){
				x = 0;
				y = yRef;
			}
			
			if( (y+img.getHeight(null)) > textureBuffer.getHauteur()){
				x=0;
				y=0;
				yRef=0;
				this.saveTexture();
				this.textureBuffer.transparentClean();
			}
			
			this.pieces.add(new Piece(i, this.nameText,x, y, img.getWidth(null), img.getHeight(null)));
			this.textureBuffer.drawImage(img, x, y, 0, 0, 0, 1.0, 1.0f);
			
			
			x += img.getWidth(null);
			yRef = Math.max(yRef, y+img.getHeight(null));
		
			img.flush();		
			i++;
		}// while fini
		
		this.saveTexture();
		
		out.println("--fin--");
	}
	
	
	
	private void createTexture(){
		this.nameText = "texture_"+this.textureIndex+".png";
		this.textureBuffer = new JImageBuffer(Color.black, this.largeurTexture, this.hauteurTexture);
		this.textureBuffer.transparentClean();
		
		this.textureIndex++;
	}
	
	private void saveTexture(){
		BufferedImage img = new BufferedImage(largeurTexture, hauteurTexture, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		
		g.setColor(new Color(0,0,0,0));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OUT));
		g.fillRect(0, 0, largeurTexture,hauteurTexture);
		g.drawImage(this.textureBuffer.getImage(), 0, 0, null);
		
		ImageFileTool il = new ImageFileTool();
		String path = output.getAbsolutePath()+File.separator;
		il.savePng(img, path, this.nameText);
		
		
		g.dispose();
		
		out.println("Sauvegarde : "+path);
	}
	
	
	
	private Image loadImage(int i){
		ImageFileTool il = new ImageFileTool();
		try {
			String path = this.input.getAbsolutePath()+File.separator+i+".png";
			return il.loadVolatileImage(path);
		} catch (ImageLoadException e) {
			return null;
		}
	}
	
	
	
	private void compterImage(){
		File[] files = this.input.listFiles();
		for(int i=0;i<files.length;i++){
			File f = files[i];
			if(f.isFile()){
				String name = f.getName();
				String[] parts = name.split("[.]");
				if("png".equals(parts[parts.length-1].toLowerCase()) &&
						parts[0].matches("[-+]?\\d*\\.?\\d+")){
					this.nbImages++;
				}
			}// if file
			
		}
	}


	public List<Piece> getPieces() {
		return pieces;
	}
	
	
}
