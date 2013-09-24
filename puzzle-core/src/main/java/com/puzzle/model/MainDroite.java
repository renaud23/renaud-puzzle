package com.puzzle.model;

public class MainDroite {
	
	private static MainDroite instance;
	
	private ComponentPiece piece;
	
	/**
	 * abscise du point de saisie dans le plan de la pièce.
	 */
	private double x;
	
	/**
	 * ordonnée de saisie dans le plan de la pièce.
	 */
	private double y;
	
	private boolean empty;
	
	private MainDroite(){
		this.empty = true;
	}
	
	
	public static MainDroite getInstance(){
		if(instance == null) instance = new MainDroite();
		return instance;
	}

	public ComponentPiece getPiece() {
		return piece;
	}
	
	

	public boolean isEmpty() {
		return empty;
	}
	
	public void libere(){
		this.empty = true;
		this.piece = null;
	}


	public void setPiece(ComponentPiece piece) {
		this.piece = piece;
		if(this.piece != null) this.empty = false;
	}
	
	public ComponentPiece poserPiece(){
		this.empty = true;
		return this.piece;
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
	
	
}
