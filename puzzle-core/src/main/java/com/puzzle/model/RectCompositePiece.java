package com.puzzle.model;

import com.renaud.manager.IRect;

public class RectCompositePiece implements MyRect{
	
	private CompositePiece composite;
	private double puzzX;
	private double puzzY;
	

	public RectCompositePiece(CompositePiece composite) {
		this.composite = composite;
	}

	@Override
	public boolean isIn(double x, double y, double largeur, double hauteur) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isIn(IRect r) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getLargeur() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getHauteur() {
		// TODO Auto-generated method stub
		return 0;
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
		
		for(Piece p : this.composite){
			double x = p.getPuzzleX();
			x -= cx;
			double y = p.getPuzzleY();
			y -= cy; 
			
			y*= -1.0;// symétrie horizontale
			x += this.composite.getCentre().getX();
			y += this.composite.getCentre().getY();
			
			p.getCentre().setX(x);
			p.getCentre().setY(y);
			p.getCentre().tourner(p.getAngle(),this.composite.getCentre());
			
			((MyRect)p.getRect()).update();
		}
		
	}

	public double getPuzzX() {
		return puzzX;
	}

	public double getPuzzY() {
		return puzzY;
	}
	
	
	

}
