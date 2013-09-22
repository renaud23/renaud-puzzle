package com.puzzle.model;

import com.renaud.manager.IRect;
import com.renaud.manager.Rect;

public class RectPiece implements IRect{
	private Piece piece;
	

	public RectPiece(){
		
	}
	
	public RectPiece(Piece piece) {
		this.piece = piece;
	}
	
	public boolean isIn(double x,double y,double largeur,double hauteur){
		return this.isIn(new Rect(x, y, largeur, hauteur));
	}
	
	public boolean isIn(IRect r){
		boolean state = false;
		
		double x = this.piece.getCentre().getX();
		x -= this.piece.getLargeur() / 2.0;
		
		double y = this.piece.getCentre().getY();
		y += this.piece.getHauteur() / 2.0;
		
		double minx = Math.min(x , r.getX());
		double maxx = Math.max(x + this.piece.getLargeur() , r.getX() + r.getLargeur());
		double maxy = Math.max(y , r.getY());
		double miny = Math.min(y - this.piece.getHauteur() , r.getY() - r.getHauteur());
		
		double ecartx = maxx;
		ecartx -= minx;
		
		double ecarty = maxy;
		ecarty -= miny;
		
		double refx = this.piece.getLargeur();
		refx += r.getLargeur();
		double refy = this.piece.getHauteur();
		refy += r.getHauteur();
		
		if(ecartx < refx && ecarty < refy) state = true;
		
		return state;
	}
	
	public RectPiece clone(){ 
		return new RectPiece(this.piece);
	}
	
	public String toString(){
		return "[RectPiece x="+this.getX()+" y="+this.getY()+" largeur="+this.getLargeur()+" hauteur="+this.getHauteur()+"]";
	}
	
	public double getX() {
		return this.piece.getCentre().getX()- this.piece.getLargeur() / 2.0;
	}

	public double getY() {
		return this.piece.getCentre().getY()+ this.piece.getHauteur() / 2.0;
	}
	
	public double getHauteur() {
		return this.piece.getHauteur();
	}
	
	public double getLargeur() {
		return this.piece.getLargeur();
	}
}
