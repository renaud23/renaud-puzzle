package com.puzzle.model;

import java.util.HashMap;
import java.util.Map;

public class Puzzle {
	
	public enum Position{
		nord,sud,est,ouest;
	}
	
	private static int nb = 0;
	
	private int largeur;
	private int hauteur;
	private int taille;
	private boolean fini;
	private int id;
	private String nom;
	private Map<Integer, Piece> pieces;

	
	public Puzzle(String nom, int largeur, int hauteur) {
		this.pieces = new HashMap<Integer,Piece>();
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.nom = nom;
		this.taille = this.largeur * this.hauteur;
		this.id = nb++;
		this.fini = false;
	}
	
	
	
	public boolean isFini() {
		return fini;
	}
	public void setFini(boolean fini) {
		this.fini = fini;
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

	public int getTaille() {
		return taille;
	}
	
	public boolean equals(Object o){
		boolean state = false;
		if(o instanceof Puzzle){
			Puzzle pz = (Puzzle) o;
			if(this.id == pz.id) state = true;
		}
		
		return state;
	}
	
	
	public void put(int index,Piece piece){
		this.pieces.put(index, piece);
	}
	
	
	public Piece get(int index){
		Piece p = null;
		if(this.pieces.containsKey(index)){
			p = this.pieces.get(index);
		}
		return p;
	}



	public Piece get(Position position,int index){
		Piece p = null;
		int pasy = 0;
		int pasx = 0;
		
		int y = (index-1) / (largeur);
		int x = (index-1) % (largeur);
		
		if(Position.nord.equals(position)){
			pasy = -1;
		}else if(Position.sud.equals(position)){
			pasy = 1;
		}else if(Position.est.equals(position)){
			pasx = 1;
		}else if(Position.ouest.equals(position)){
			pasx = -1;
		}
		
		y += pasy;
		x += pasx;

		if(y>=0 && x>=0 && x<this.largeur && y<this.hauteur){
			int ref = index;
			ref += pasx;
			ref += pasy * this.largeur;
			
			p = this.get(ref);
		}
		
		return p;
	}
}
