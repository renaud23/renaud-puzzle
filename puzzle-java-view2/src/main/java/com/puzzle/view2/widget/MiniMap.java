package com.puzzle.view2.widget;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import com.puzzle.view2.DrawOperationAware;
import com.puzzle.view2.controller.ControllerAdaptater;
import com.puzzle.view2.image.IDrawOperation;
import com.puzzle.view2.image.IDrawable;
import com.puzzle.view2.layer.BackgroundLayer;




public class MiniMap extends ControllerAdaptater implements IDrawable,DrawOperationAware,Widget{
	
	private int x;
	private int y;
	private int largeur;
	private int hauteur;
	
	private double scale;
	private BackgroundLayer background;
	private Image backgroundImage;
	
	private IDrawOperation op;
	
	private boolean over;
	private float alphaZoom = 0.0f;
	private Color zoomColor = Color.black;
	

	public MiniMap(BackgroundLayer background, Image backgroundImage,int x, int y,double scale) {
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.background = background;
		this.backgroundImage = backgroundImage;
		
		this.largeur = (int) (background.getLargeurTapis() * scale);
		this.hauteur = (int) (background.getHauteurTapis() * scale);
		
		this.rectangle = new Rectangle(x,y,largeur,hauteur);
	}

	

	@Override
	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

	@Override
	public boolean isChange() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setChange() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		
		
		int xi = x;
		xi += (int)((background.getLargeurTapis() / 2.0 + background.getVue().getX()) * scale); 
		int yi = y;
		yi += (int)((background.getHauteurTapis() / 2.0 - background.getVue().getY()) * scale);
		int l = (int) (background.getVue().getLargeur() * scale);
		int h = (int) (background.getVue().getHauteur() * scale);
		
		
		//
		if(over){
			this.op.drawImage(this.backgroundImage, x, y, 0, 0, 0, 
					(double)largeur/this.backgroundImage.getWidth(null),
					(double)hauteur/this.backgroundImage.getHeight(null), 
					1.0f);
			
			this.op.fillRect(zoomColor, x, y, xi - x, hauteur, alphaZoom);
			this.op.fillRect(zoomColor, xi + l, y, largeur - xi - l + x, hauteur, alphaZoom);
			this.op.fillRect(zoomColor, xi, y, l, yi - y, alphaZoom);
			this.op.fillRect(zoomColor, xi, yi+h, l, hauteur - yi - h + y, alphaZoom);
		}
		//
		
		this.op.drawRect(Color.black, x, y, largeur, hauteur);
		this.op.drawRect(Color.black, xi, yi, l, h);
		
		//
		
		
		
	}

	

	

	
	
	/*
	 * 
	 */
	
	


	
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation()>0){
			background.zoomOut();
		}else{
			background.zoomIn();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		over = true;
		alphaZoom = 0.4f;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		over = false;
		alphaZoom = 0.0f;
	}



	@Override
	public void mouseDragged(MouseEvent e) {
		double x = e.getX() - this.x;
		x /= scale;
		x -= background.getLargeurTapis() /2.0;
		x -= background.getVue().getLargeur() / 2.0;
		double y = e.getY() - this.y;
		y /= scale;
		y = background.getHauteurTapis() / 2.0 - y;
		y += background.getVue().getHauteur() / 2.0;
	
		
		this.background.moveTo(x, y);
	}
	
	
	/*
	 * 
	 */
	
	public int getX() {
		return x;
	}

	



	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

	
	
	
	
	
	
	
	
	
}
