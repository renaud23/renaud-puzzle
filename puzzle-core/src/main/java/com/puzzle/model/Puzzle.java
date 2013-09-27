package com.puzzle.model;

public class Puzzle {
	private int largeur;
	private int hauteur;
	private int id;
	private String nom;
	
	
	
	
	
	public Puzzle(String nom, int largeur, int hauteur) {
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.nom = nom;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
	
	
}
