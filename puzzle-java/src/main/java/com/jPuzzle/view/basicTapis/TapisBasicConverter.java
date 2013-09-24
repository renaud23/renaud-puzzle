package com.jPuzzle.view.basicTapis;

import com.jPuzzle.view.controler.TapisConverter;
import com.jPuzzle.view.image.Offscreen;
import com.puzzle.model.Point;
import com.puzzle.model.Tapis;

public class TapisBasicConverter implements TapisConverter {
	
	private Offscreen offscreen;
	private Tapis tapis;
	
	
	private double scaleX;
	private double scaleY;
	private double miLargeur;
	private double miHauteur;
	
	private static TapisBasicConverter instance;
	public static TapisBasicConverter getInstance(){
		if(instance == null) instance = new TapisBasicConverter();
		return instance;
	}
	
	private TapisBasicConverter() {
		
	}





	@Override
	public void convertScreenToModel(Point p) {
		double x = p.getX();
		double y = p.getY();
		
		x /= this.scaleX;
		x -= this.miLargeur;
		
		y *= -1;
		y /= this.scaleY;
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
		
		y *= -1;
		y += this.miHauteur;
		y *= this.scaleY;
		
		p.setX(x);
		p.setY(y);
	}


	public double getScaleX() {
		return scaleX;
	}

	public double getScaleY() {
		return scaleY;
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