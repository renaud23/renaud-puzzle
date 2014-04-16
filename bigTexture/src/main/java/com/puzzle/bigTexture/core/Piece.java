package com.puzzle.bigTexture.core;

public class Piece {
	
	
	public Piece(int id, int x, int y, int largeur, int hauteur) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
	}
	private int id;
	private int x;
	private int y;
	private int largeur;
	private int hauteur;
	
	
	
	@Override
	public String toString() {
		return "Piece [id=" + id + ", x=" + x + ", y=" + y + ", largeur="
				+ largeur + ", hauteur=" + hauteur + "]";
	}
	
	
	
	
}
