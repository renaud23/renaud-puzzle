package com.renaud.manager;

public class Rect implements Cloneable,IRect{

	private double x;
	private double y;
	private double hauteur;
	private double largeur;
	

	public Rect(){
		this.x = 0.0;
		this.y = 0.0;
		this.hauteur = 0.0;
		this.largeur = 0.0;
	}
	
	public Rect(double x, double y, double largeur, double hauteur) {
		this.x = x;
		this.y = y;
		this.hauteur = hauteur;
		this.largeur = largeur;
	}
	
	public boolean isIn(double x,double y,double largeur,double hauteur){
		return this.isIn(new Rect(x, y, largeur, hauteur));
	}
	
	
	
	
	public boolean isIn(IRect r){
		boolean state = false;
		
		double minx = Math.min(this.x , r.getX());
		double maxx = Math.max(this.x + this.largeur , r.getX() + r.getLargeur());
		double maxy = Math.max(this.y , r.getY());
		double miny = Math.min(this.y - this.hauteur , r.getY() - r.getHauteur());
		
		double ecartx = maxx;
		ecartx -= minx;
		
		double ecarty = maxy;
		ecarty -= miny;
		
		double refx = this.largeur;
		refx += r.getLargeur();
		double refy = this.hauteur;
		refy += r.getHauteur();
		
		if(ecartx < refx && ecarty < refy) state = true;
		
		return state;
	}
	
	public Rect clone(){ 
		return new Rect(x, y, largeur, hauteur);
	}
	
	public String toString(){
		return "[Rect x="+x+" y="+y+" largeur="+largeur+" hauteur="+hauteur+"]";
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getHauteur() {
		return hauteur;
	}
	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
	}
	public double getLargeur() {
		return largeur;
	}
	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}
	
	
}
