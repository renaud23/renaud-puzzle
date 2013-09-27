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
	private int id;
	private String nom;
	private Map<Integer, Point> pieces;

	
	public Puzzle(String nom, int largeur, int hauteur) {
		this.pieces = new HashMap<Integer,Point>();
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.nom = nom;
		this.taille = this.largeur * this.hauteur;
		this.id = nb++;
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
	
	
	public void put(int index,Point centre){
		this.pieces.put(index, centre);
	}
	
	
	public Point get(int index){
		Point p = null;
		if(this.pieces.containsKey(index)){
			p = this.pieces.get(index);
		}
		return p;
	}
	
	public Point get(Position position,int index){
		Point p = null;
		int pasy = 0;
		int pasx = 0;
		
		int y = index / (largeur+1);
		int x = index % (largeur+1);
		
		if(Position.nord.equals(position)){
			pasy = -this.largeur;
		}else if(Position.sud.equals(position)){
			pasy = this.largeur;
		}else if(Position.est.equals(position)){
			pasy = 1;
		}else if(Position.ouest.equals(position)){
			pasy = -1;
		}
		
		y += pasy;
		x += pasx;
		
		if(y>=0 && x>=0 && x<this.largeur && y<this.hauteur){
			int ref = index;
			ref += x;
			ref += y;
			
			p = this.get(ref);
		}
		
		
		return p;
	}
}
