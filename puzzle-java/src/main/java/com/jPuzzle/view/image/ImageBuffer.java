package com.jPuzzle.view.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.VolatileImage;

public class ImageBuffer {
	
	private static GraphicsConfiguration gc = 
			GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	
	
	private VolatileImage image;
	private int largeur;
	private int hauteur;
	private Color backgroundColor;
	
	
	public ImageBuffer (Color color,int largeur,int hauteur){
		this.hauteur = hauteur;
		this.largeur = largeur;

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
		this.image = gc.createCompatibleVolatileImage(this.largeur, this.hauteur, Transparency.TRANSLUCENT);

		this.backgroundColor = color;

		
	}
	
	
	public void transparentClean(){
		Graphics2D g = image.createGraphics();
		 
		// These commands cause the Graphics2D object to clear to (0,0,0,0).
		g.setColor(new Color(0,0,0,0));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OUT));
		g.fillRect(0, 0, image.getWidth(), image.getHeight()); // Clears the image.
	}
	
	public void clean(){
		Graphics2D graphics = this.image.createGraphics();
		graphics.setColor(this.backgroundColor);
		graphics.fillRect(0, 0, largeur, hauteur);
	}
	
	public void drawImage(Image image,double x,double y,double xRotation,double yRotation,double theta,double scale,float alpha){
		
		Graphics2D gr = this.image.createGraphics();

		/** D�sactivation de l'anti-aliasing */
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		/** Demande de rendu rapide */
		gr.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);


		 gr.setComposite(AlphaComposite.getInstance(
	                AlphaComposite.SRC_OVER,alpha )) ;
		 
		 if(this.image.contentsLost())
			 this.image.validate(gc);

		//
		AffineTransform t = new AffineTransform();
		t.setToIdentity();

		t.translate(x, y);
		t.scale(scale, scale);
		gr.rotate(theta, xRotation*scale, yRotation*scale);

		gr.drawImage(image,t,null);
		gr.dispose();

	}


	public void drawImage(Image image,double x,double y,double xRotation,double yRotation,double theta,double scaleX,double scaleY,float alpha){
		
		Graphics2D gr = this.image.createGraphics();

		/** D�sactivation de l'anti-aliasing */
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		/** Demande de rendu rapide */
		gr.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);


		 gr.setComposite(AlphaComposite.getInstance(
	                AlphaComposite.SRC_OVER,alpha )) ;
		 
		 if(this.image.contentsLost())
			 this.image.validate(gc);

		//
		AffineTransform t = new AffineTransform();
		t.setToIdentity();

		t.translate(x, y);
		t.scale(scaleX, scaleY);
		gr.rotate(theta, xRotation*1.0, yRotation*1.0);

		gr.drawImage(image,t,null);
		gr.dispose();

	}
	
	public Image getImage(){
		return this.image;
	}
}