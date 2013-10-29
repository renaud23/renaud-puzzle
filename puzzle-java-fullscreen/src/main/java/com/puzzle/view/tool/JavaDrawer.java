package com.puzzle.view.tool;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import com.puzzle.view.core.IDrawer;

public class JavaDrawer implements IDrawer{
	private Graphics2D graphics;
	private Rectangle clipArea;
	private int largeur;
	private int hauteur;
	
	
	public JavaDrawer(Graphics2D graphics) {
		this.graphics = graphics;
	
		this.clipArea = graphics.getDeviceConfiguration().getBounds();
		this.largeur = (int) this.clipArea.getWidth();
		this.hauteur = (int) this.clipArea.getHeight();
	}


	public void drawImage(Image image,double x,double y,double xRotation,double yRotation,double theta,double scaleX,double scaleY,float alpha){
		
		/** D�sactivation de l'anti-aliasing */
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		/** Demande de rendu rapide */
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		graphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);


		graphics.setComposite(AlphaComposite.getInstance(
	                AlphaComposite.SRC_OVER,alpha )) ;
	
		//
		AffineTransform t = new AffineTransform();
		t.setToIdentity();

		t.translate(x, y);
		t.scale(scaleX, scaleY);
		graphics.rotate(theta, xRotation, yRotation);

		graphics.drawImage(image,t,null);
	}
	
	public void fillRect(Color color,int x,int y,int width,int height,float alpha){
		
		graphics.setColor(color);
		graphics.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER,alpha )) ;
		graphics.fillRect(x, y, width, height);
		
		
	}
	
	
	@Override
	public void dispose() {
		this.graphics.dispose();
	}

	@Override
	public void drawRect(Color color, int x, int y, int largeur, int hauteur,float alpha) {
		graphics.setColor(color);
		graphics.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER,alpha )) ;
		graphics.drawRect(x, y, largeur, hauteur);
	}

	@Override
	public int getLargeur() {
		return this.largeur;
	}

	@Override
	public int getHauteur() {
		return this.hauteur;
	}
}
