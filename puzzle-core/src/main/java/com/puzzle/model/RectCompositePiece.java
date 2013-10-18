package com.puzzle.model;

import com.renaud.manager.IRect;

public class RectCompositePiece implements MyRect{
	
	private CompositePiece composite;
	private double puzzX;
	private double puzzY;
	private Point[] coins = new Point[4];
	private double largeur;
	private double hauteur;
	private double x;
	private double y;
	

	public RectCompositePiece(CompositePiece composite) {
		this.composite = composite;
		this.coins[0] = new Point();
		this.coins[1] = new Point();
		this.coins[2] = new Point();
		this.coins[3] = new Point();
	}

	@Override
	public boolean isIn(double x, double y, double largeur, double hauteur) {
		boolean state = false;
		for(Piece p : this.composite){
			if(p.getRect().isIn(x,y,largeur,hauteur)) state = true;
		}
		return state;
	}

	@Override
	public boolean isIn(IRect r) {
		boolean state = false;
		for(Piece p : this.composite){
			if(p.getRect().isIn(r)) state = true;
		}
		return state;
	}

	@Override
	public boolean contains(double x, double y) {
		boolean state = false;
		for(Piece p : this.composite){
			if(p.getRect().contains(x,y)) state = true;
		}
		return state;
	}

	@Override
	public double getX() {
		return this.y;
	}

	@Override
	public double getY() {
		return this.x;
	}

	@Override
	public double getLargeur() {
		return this.largeur;
	}

	@Override
	public double getHauteur() {
		return this.hauteur;
	}

	@Override
	public void update() {
		// centre dans le puzzle.
		double maxx = -Double.MAX_VALUE;
		double minx = Double.MAX_VALUE;
		double maxy = -Double.MAX_VALUE;
		double miny = Double.MAX_VALUE;
		
		for(Piece p : this.composite){
			maxx = Math.max(maxx, p.getPuzzleX() + p.getLargeur() / 2.0);
			minx = Math.min(minx, p.getPuzzleX() - p.getLargeur() / 2.0);
			maxy = Math.max(maxy, p.getPuzzleY() + p.getHauteur() / 2.0);
			miny = Math.min(miny, p.getPuzzleY() - p.getHauteur() / 2.0);
		}
		
		double cl = maxx - minx;
		double ch = maxy - miny;
		this.composite.setLargeur(cl);
		this.composite.setHauteur(ch);
		this.puzzX = minx;
		this.puzzY = miny;
		double cx = minx + cl / 2.0;
		double cy = miny + ch / 2.0; 
		
		// mise � jour des pi�ces
		for(Piece p : this.composite){
			double x = p.getPuzzleX();
			x -= cx;
			double y = p.getPuzzleY();
			y -= cy; 
			
			y*= -1.0;// sym�trie horizontale
			x += this.composite.getCentre().getX();
			y += this.composite.getCentre().getY();
			
			p.getCentre().setX(x);
			p.getCentre().setY(y);
			p.getCentre().tourner(p.getAngle(),this.composite.getCentre());
			
			((MyRect)p.getRect()).update();
		}
		
		// calcul du rect
		this.coins[0].setX(minx);
		this.coins[0].setY(-miny);
		this.coins[1].setX(maxx);
		this.coins[1].setY(-miny);
		this.coins[2].setX(maxx);
		this.coins[2].setY(-maxy);
		this.coins[3].setX(minx);
		this.coins[3].setY(-maxy);
		
		this.coins[0].tourner(this.composite.getAngle(),this.composite.getCentre());
		this.coins[1].tourner(this.composite.getAngle(),this.composite.getCentre());
		this.coins[2].tourner(this.composite.getAngle(),this.composite.getCentre());
		this.coins[3].tourner(this.composite.getAngle(),this.composite.getCentre());
		
		double l = this.composite.getLargeur() / 2.0;
		double h = this.composite.getHauteur() / 2.0;
		
		maxx = -Double.MAX_VALUE;
		minx = Double.MAX_VALUE;
		maxy = -Double.MAX_VALUE;
		miny = Double.MAX_VALUE;
		for(int i=0;i<4;i++){
			double x = this.coins[i].getX();
			x -= l;
			double y = this.coins[i].getY();
			y += h;
			this.coins[i].setX(x);
			this.coins[i].setY(y);
			
			minx = Math.min(minx, x);
			maxx = Math.max(maxx, x);
			miny = Math.min(miny, y);
			maxy = Math.max(maxy, y);
		}
		this.x = minx;
		this.y = maxy;
		this.largeur = maxx - minx;
		this.hauteur = maxy - miny;
		
	}
	
	
	
	public void updateSize(){
		// calcul de la taille selon les coordonn�es puzz.
		double maxx = -Double.MAX_VALUE;
		double minx = Double.MAX_VALUE;
		double maxy = -Double.MAX_VALUE;
		double miny = Double.MAX_VALUE;
		
		for(Piece p : this.composite){
			maxx = Math.max(maxx, p.getPuzzleX() + p.getLargeur() / 2.0);
			minx = Math.min(minx, p.getPuzzleX() - p.getLargeur() / 2.0);
			maxy = Math.max(maxy, p.getPuzzleY() + p.getHauteur() / 2.0);
			miny = Math.min(miny, p.getPuzzleY() - p.getHauteur() / 2.0);
		}
		double cl = maxx - minx;
		double ch = maxy - miny;
		this.composite.setLargeur(cl);
		this.composite.setHauteur(ch);
		this.puzzX = minx;
		this.puzzY = miny;
	}
	
	
	public double getPuzzX() {
		return puzzX;
	}

	public double getPuzzY() {
		return puzzY;
	}
	
	
	

}
