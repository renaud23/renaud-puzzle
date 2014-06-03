package com.puzzle.view2.image.tool;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;

import com.puzzle.view2.image.IDrawOperation;

public class HwdBuffer implements IDrawOperation{
	
	private BufferStrategy strategy;
	
	
	public HwdBuffer (BufferStrategy strategy){
		this.strategy = strategy;
		
	}

	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	public void clean() {
		// TODO Auto-generated method stub
		
	}

	public void drawImage(Image image,double x,double y,double xRotation,double yRotation,double theta,double scale,float alpha){
		Graphics2D gr = (Graphics2D) this.strategy.getDrawGraphics();

		/** Desactivation de l'anti-aliasing */
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		/** Demande de rendu rapide */
		gr.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);

		gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha )) ;

		//
		AffineTransform t = new AffineTransform();
		t.setToIdentity();

		t.translate(x, y);
		t.scale(scale, scale);
		gr.rotate(theta, xRotation, yRotation);

		gr.drawImage(image,t,null);
		gr.dispose();

	}

	public void drawImage(Image image,double x,double y,double xRotation,double yRotation,double theta,double scaleX,double scaleY,float alpha){
		Graphics2D gr = (Graphics2D) this.strategy.getDrawGraphics();

		/** Desactivation de l'anti-aliasing */
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

	public void drawPart(Image image, int dx1, int dy1, int dx2, int dy2,
			int sx1, int sy1, int sx2, int sy2) {
		// TODO Auto-generated method stub
		
	}

	public void drawRect(Color color,int x,int y,int width,int height){
		Graphics2D g = (Graphics2D) this.strategy.getDrawGraphics();
		g.setColor(color);
		g.drawRect(x, y, width, height);
		g.dispose();
	}

	public void fillRect(Color color,double x,double y,double width,double height,float alpha){
		Graphics2D g = (Graphics2D) this.strategy.getDrawGraphics();
		/** Desactivation de l'anti-aliasing */
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		/** Demande de rendu rapide */
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);


		g.setComposite(AlphaComposite.getInstance(
	                AlphaComposite.SRC_OVER,alpha ));
		g.setColor(color);
		Shape s = new Rectangle2D.Double(x,y,width,height);
		
		g.fill(s);
		g.dispose();
	}

	public void drawImage(Image image,
			double x,double y,
			double xRotation,double yRotation,double theta,
			double scale,float alpha,Composite composite){
		
		Graphics2D gr = (Graphics2D) this.strategy.getDrawGraphics();

		/** D�sactivation de l'anti-aliasing */
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		/** Demande de rendu rapide */
		gr.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);

		gr.setComposite(composite) ;
		 
		//
		AffineTransform t = new AffineTransform();
		t.setToIdentity();

		t.translate(x, y);
		t.scale(scale,scale);
		gr.rotate(theta, xRotation, yRotation);

		gr.drawImage(image,t,null);
		gr.dispose();
	}

}
