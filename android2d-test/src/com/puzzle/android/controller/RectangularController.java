package com.puzzle.android.controller;

public abstract class RectangularController implements IController,Widget {

	protected float x;
	protected float y;
	protected float largeur;
	protected float hauteur;
	
	
	public RectangularController(){
		
	}
	
	
	public RectangularController(float x, float y, float largeur, float hauteur) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
	}





	@Override
	public boolean isIn(float x, float y) {
		boolean value = false;
		
		if(x > this.x && x < (this.x+largeur) && y > this.y && y < (this.y + this.hauteur))
			value = true;
		
		return value;
	}
}
