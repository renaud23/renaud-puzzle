package com.puzzle.view.basicTapis;


import com.puzzle.controller.TapisConverter;
import com.puzzle.model.Point;
import com.puzzle.model.Tapis;
import com.puzzle.view.Offscreen;

public class TapisBasicConverter implements TapisConverter {
	
	private Offscreen offscreen;
	private Tapis tapis;
	private double scaleX;
	private double scaleY;
	private double miLargeur;
	private double miHauteur;
	

	
	public TapisBasicConverter(Offscreen offscreen,Tapis tapis) {
		this.tapis = tapis;
		this.offscreen = offscreen;
	}





	@Override
	public void convertScreenToModel(Point p) {
		double x = p.getX();
		double y = p.getY();
		
		x /= this.scaleX;
		x -= this.miLargeur;
		
		y /= this.scaleY;
		y *= -1;
		y += this.miHauteur;
		
		p.setX(x);
		p.setY(y);
	}

	@Override
	public void convertModelToScreen(Point p) {
		double x = p.getX();
		double y = p.getY();
		
		x += this.miLargeur;
		x *= this.scaleX;
		
		y *= -1.0;
		y += this.miHauteur;
		y *= this.scaleY;
		
		p.setX(x);
		p.setY(y);
	}


	public double getScaleX() {
		return this.scaleX;
	}

	public double getScaleY() {
		return this.scaleY;
	}

	public void update(){
		this.scaleX = this.offscreen.getLargeur();
		this.scaleX /= this.tapis.getLargeur();
		this.scaleY = this.offscreen.getHauteur();
		this.scaleY /= this.tapis.getHauteur();
		this.miHauteur = this.tapis.getHauteur() / 2.0;
		this.miLargeur = this.tapis.getLargeur() / 2.0;
	}
	
	
	
	
	
	public void setOffscreen(Offscreen offscreen) {
		this.offscreen = offscreen;
	}


	public void setTapis(Tapis tapis) {
		this.tapis = tapis;
	}

	
}
