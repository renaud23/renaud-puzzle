package com.puzzle.model;

import java.util.ArrayList;
import java.util.List;

import com.renaud.manager.IRect;
import com.renaud.manager.Rect;

public class RectPiece implements MyRect{
	private Piece piece;
	private double x;
	private double y;
	private double largeur;
	private double hauteur;
	private List<Point> coins = new ArrayList<Point>();
	

	public RectPiece(){
		
	}
	
	public RectPiece(Piece piece) {
		this.piece = piece;
		
		this.update();
		this.checkAngle();
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
	
	public void checkAngle(){
		double[] x = new double[4];
		double[] y = new double[4];
		double refl = this.piece.getLargeur() / 2.0;
		double refh = this.piece.getHauteur() / 2.0;
	
		x[0] = refl * Math.cos(this.piece.getAngle()) - refh * Math.sin(this.piece.getAngle());
		y[0] = refl * Math.sin(this.piece.getAngle()) + refh * Math.cos(this.piece.getAngle());
		
		x[1] = -refl * Math.cos(this.piece.getAngle()) - refh * Math.sin(this.piece.getAngle());
		y[1] =- refl * Math.sin(this.piece.getAngle()) + refh * Math.cos(this.piece.getAngle());
		
		x[2] = -refl * Math.cos(this.piece.getAngle()) + refh * Math.sin(this.piece.getAngle());
		y[2] = -refl * Math.sin(this.piece.getAngle()) - refh * Math.cos(this.piece.getAngle());
		
		x[3] = refl * Math.cos(this.piece.getAngle()) + refh * Math.sin(this.piece.getAngle());
		y[3] = refl * Math.sin(this.piece.getAngle()) - refh * Math.cos(this.piece.getAngle());
		
		double ymin = Double.MAX_VALUE, ymax = Double.MIN_VALUE, xmin = Double.MAX_VALUE, xmax = Double.MIN_VALUE;
		
		
		for(int i=0;i<4;i++){
			x[i] += this.piece.getCentre().getX();
			y[i] += this.piece.getCentre().getY();
			
			if(x[i] < xmin) xmin = x[i];
			if(x[i] > xmax) xmax = x[i];
			if(y[i] < ymin) ymin = y[i];
			if(y[i] > ymax) ymax = y[i];
			
			this.coins.add(i, new Point(x[i],y[i]));
		}
		
		this.x = xmin;
		this.y = ymax;
		this.largeur = xmax - xmin;
		this.hauteur = ymax - ymin;
		
		
		
		
	}

	
	@Override
	public boolean contains(double x, double y) {
		boolean state = false;
		if(x >= this.x && x <= (this.x+this.largeur) && y <= this.y && y >= (this.y-this.hauteur))
			state = true;
		return state;
	}
	
	public RectPiece clone(){ 
		RectPiece p = new RectPiece(this.piece);
		p.update();
		p.checkAngle();
		return p;
	}
	
	public String toString(){
		return "[RectPiece x="+this.getX()+" y="+this.getY()+" largeur="+this.getLargeur()+" hauteur="+this.getHauteur()+"]";
	}
	
	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}
	
	public double getHauteur() {
		return this.hauteur;
	}
	
	public double getLargeur() {
		return this.largeur;
	}

	@Override
	public void update() {
		this.x = this.piece.getCentre().getX() - this.piece.getLargeur() / 2.0;
		this.y = this.piece.getCentre().getY() + this.piece.getHauteur() / 2.0;
		this.largeur = this.piece.getLargeur();
		this.hauteur = this.piece.getHauteur();
	}

	
	
	public List<Point> getCoins() {
		return coins;
	}


}
