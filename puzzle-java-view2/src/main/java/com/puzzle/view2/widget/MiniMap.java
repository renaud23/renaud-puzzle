package com.puzzle.view2.widget;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import com.puzzle.view2.DrawOperationAware;
import com.puzzle.view2.controller.ControllerAdaptater;
import com.puzzle.view2.draw.IDrawOperation;
import com.puzzle.view2.draw.IDrawable;
import com.puzzle.view2.layer.BackgroundLayer;




public class MiniMap extends ControllerAdaptater implements IDrawable,DrawOperationAware,Widget{
	
	private int x;
	private int y;
	private int largeur;
	private int hauteur;
	
	private double scale;
	private BackgroundLayer background;
	
	private IDrawOperation op;
	
	

	public MiniMap(BackgroundLayer background,int x, int y,double scale) {
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.background = background;
		
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
		this.op.drawRect(Color.black, x, y, largeur, hauteur);
		
		int xi = x;
		xi += (int)((background.getLargeurTapis() / 2.0 + background.getxVue()) * scale); 
		int yi = y;
		yi += (int)((background.getHauteurTapis() / 2.0 - background.getyVue()) * scale);
		int l = (int) (background.getLargeurVue() * scale);
		int h = (int) (background.getHauteurVue() * scale);
		
		this.op.drawRect(Color.black, xi, yi, l, h);
	}

	

	

	
	
	/*
	 * 
	 */
	
	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("entered");
	}


	
	
	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("exit");
	}



	@Override
	public void mouseDragged(MouseEvent e) {
		double x = e.getX() - this.x;
		x /= scale;
		x -= background.getLargeurTapis() /2.0;
		x -= background.getLargeurVue() / 2.0;
		double y = e.getY() - this.y;
		y /= scale;
		y = background.getHauteurTapis() / 2.0 - y;
		y += background.getHauteurVue() / 2.0;
		
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
