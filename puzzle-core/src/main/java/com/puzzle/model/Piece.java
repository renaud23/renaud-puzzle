package com.puzzle.model;

import com.renaud.manager.IRect;


public class Piece implements ComponentPiece{
	
	private int id;
	
	private double largeur;
	
	private double hauteur;
	
	private Point centre;
	
	private IRect rect;
	
	private int zIndex;
	
	
	public Piece(){
		this.centre = new Point();
	}
	
	
	public Piece(int id,double x,double y,double largeur,double hauteur){
		this.id = id;
		this.centre = new Point();
		this.centre.setX(x);
		this.centre.setY(y);
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.rect = new RectPiece(this);
		
	}
	
	
	
	public void translate(double xi,double yi){
		this.centre.translate(xi, yi);
	}
	
	

	@Override
	public IRect getRect() {
		return ((RectPiece)this.rect).clone();
	}
	
	public String toString(){
		return "[Piece id="+this.id+" centre="+this.centre.toString()+" largeur="+this.largeur+" hauteur="+this.hauteur+" rect="+this.rect.toString()+"]";
	}
	
	public Point getCentre() {
		return centre;
	}
	
	public void setCentre(Point centre) {
		this.centre = centre;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Override
	public int getZIndex() {
		return this.zIndex;
	}


	@Override
	public void setZIndex(int index) {
		this.zIndex = index;
	}


	
}
