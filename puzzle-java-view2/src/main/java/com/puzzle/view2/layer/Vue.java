package com.puzzle.view2.layer;

public class Vue implements Cloneable{
	
	
	private double x;
	private double y;
	private double largeur;
	private double hauteur;
	private double scale;
	
	
	
	public Vue clone(){
		Vue n = new Vue();
		n.x = x;
		n.y = y;
		n.largeur = largeur;
		n.hauteur = hauteur;
		
		return n;
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
	public double getLargeur() {
		return largeur;
	}
	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}
	public double getHauteur() {
		return hauteur;
	}
	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
	}
	
	public double getScale() {
		return scale;
	}




	public void setScale(double scale) {
		this.scale = scale;
	}




	public void addX(double x){
		this.x += x;
	}
	public void addY(double y){
		this.y += y;
	}
	
	

}
