package com.puzzle.model;

public class MainDroite {
	
	private static MainDroite instance;
	
	private ComponentPiece contenu;
	private Point ancre;
	private boolean empty;
	
	
	private MainDroite(){
		this.empty = true;
	}
	
	
	public static MainDroite getInstance(){
		if(instance == null) instance = new MainDroite();
		return instance;
	}

	public ComponentPiece getContenu() {
		return contenu;
	}
	
	

	public boolean isEmpty() {
		return empty;
	}
	
	public void libere(){
		this.empty = true;
		this.contenu = null;
	}


	public void setPiece(ComponentPiece piece) {
		this.contenu = piece;
		if(this.contenu != null) this.empty = false;
	}
	
	public ComponentPiece poserPiece(){
		this.empty = true;
		return this.contenu;
	}

	public Point getAncre() {
		return ancre;
	}

	public void setAncre(Point ancre) {
		this.ancre = ancre;
	}


	
}
