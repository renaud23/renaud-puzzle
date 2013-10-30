package com.puzzle.view.java;

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
	
	
	public JavaDrawer(Graphics2D graphics,int largeur,int hauteur) {
		this.graphics = graphics;
	
		this.clipArea = graphics.getDeviceConfiguration().getBounds();
		
		
		
		
		this.largeur = largeur;//(int) this.clipArea.getWidth();
		this.hauteur = hauteur;//(int) this.clipArea.getHeight();
		
		
	}


	public void drawImage(Image image,double x,double y,double xRotation,double yRotation,double theta,double scaleX,double scaleY,float alpha){
		Graphics2D gr = (Graphics2D) graphics.create();
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
	
		//
		AffineTransform t = new AffineTransform();
		t.setToIdentity();

		t.translate(x, y);
		t.scale(scaleX, scaleY);
		gr.rotate(theta, xRotation, yRotation);

		gr.drawImage(image,t,null);
		gr.dispose();
	}
	
	public void fillRect(Color color,int x,int y,int width,int height,float alpha){
		Graphics2D gr = (Graphics2D) graphics.create();
		gr.setColor(color);
		gr.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER,alpha )) ;
		gr.fillRect(x, y, width, height);
		gr.dispose();
		
	}
	
	
	public void clean(){
		Graphics2D gr = (Graphics2D) graphics.create();
		gr.setColor(Color.red);
		gr.fillRect(0, 0, largeur, hauteur);
		gr.dispose();
	}
	
	@Override
	public void dispose() {
		this.graphics.dispose();
	}

	@Override
	public void drawRect(Color color, int x, int y, int largeur, int hauteur,float alpha) {
		Graphics2D gr = (Graphics2D) graphics.create();
		gr.setColor(color);
		gr.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER,alpha )) ;
		gr.drawRect(x, y, largeur, hauteur);
		gr.dispose();
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
