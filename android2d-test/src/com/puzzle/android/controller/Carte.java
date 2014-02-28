package com.puzzle.android.controller;

public class Carte implements IController{
	
	private float x;
	private float y;
	private float largeur;
	private float hauteur;
	
	
	

	public Carte(float x, float y, float largeur, float hauteur) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
		
		
	}

	@Override
	public void onTouchEvent(float x, float y) {
		System.out.println(x+" "+y);
	}

	@Override
	public boolean isIn(float x, float y) {
		boolean value = false;
		
		if(x > this.x && x < (this.x+largeur) && y > this.y && y < (this.y + this.hauteur))
			value = true;
		
		return value;
	}

	@Override
	public int getZIndex() {
		return 10000;
	}
	
	
	
	public void remove(){
		
	}

}
