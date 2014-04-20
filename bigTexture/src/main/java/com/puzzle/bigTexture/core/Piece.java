package com.puzzle.bigTexture.core;

public class Piece {
	
	private int id;
	private String nameTex;
	private int xTex;
	private int yTex;
	private int largeur;
	private int hauteur;
	private int xPuzz;
	private int yPuzz;
	
	public Piece(int id, String nameTex,int xTex, int yTex, int largeur, int hauteur) {
		this.id = id;
		this.nameTex = nameTex;
		this.xTex = xTex;
		this.yTex = yTex;
		this.largeur = largeur;
		this.hauteur = hauteur;
	}
	
	
	
	
	@Override
	public String toString() {
		return "Piece [id=" + id + ", x=" + xTex + ", y=" + yTex + ", largeur="
				+ largeur + ", hauteur=" + hauteur + "]";
	}




	public int getxPuzz() {
		return xPuzz;
	}

	public void setxPuzz(int xPuzz) {
		this.xPuzz = xPuzz;
	}

	public int getyPuzz() {
		return yPuzz;
	}

	public void setyPuzz(int yPuzz) {
		this.yPuzz = yPuzz;
	}

	public int getxTex() {
		return xTex;
	}

	public void setxTex(int xTex) {
		this.xTex = xTex;
	}

	public int getyTex() {
		return yTex;
	}

	public void setyTex(int yTex) {
		this.yTex = yTex;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

	public String getNameTex() {
		return nameTex;
	}

	public void setNameTex(String nameTex) {
		this.nameTex = nameTex;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
