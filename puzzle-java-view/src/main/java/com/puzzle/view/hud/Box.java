package com.puzzle.view.hud;

/**
 * boite dans le plan de l'écran (même orientation) et dont
 * les cotés sont parallèle aux axes.
 * @author Administrateur
 *
 */
public class Box implements HudShape {
	
	private int x1;
	private int x2;
	private int y1;
	private int y2;
	private int largeur;
	private int hauteur;
	
	/**
	 * 
	 * @param x coin haut gauche
	 * @param y coin haut gauche
	 * @param largeur
	 * @param hauteur
	 */
	public Box(int x,int y,int largeur,int hauteur){
		this.x1 = x;
		this.y1 = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.x2 = x + this.largeur;
		this.y2 = y + this.hauteur;
	}

	public int getX() {
		return x1;
	}

	public int getY() {
		return y1;
	}

	public int getLargeur() {
		return largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	@Override
	public boolean isIn(int x, int y) {
		boolean state = false;
		
		if(x > x1 && x < x2 && y > y1 && y < y2) state = true;
	
		return state;
	}

	@Override
	public void scale(double scale) {
		// TODO Auto-generated method stub
		
	}

}
