package com.puzzle.model;

import java.util.Set;
import com.renaud.manager.IRect;
import com.renaud.manager.TasManager;




public class Tapis {
	private TasManager<ComponentPiece> memoire;
	
	private double largeur;
	
	private double hauteur;
	
	
	
	public Tapis(){
		
	}


	public Tapis(double largeur, double hauteur) {
		super();
		this.largeur = largeur;
		this.hauteur = hauteur;
		
		this.memoire = new TasManager<ComponentPiece>(7, (largeur / 2.0) * -1.0, hauteur / 2.0, largeur, hauteur);
	}


	
	/**
	 * Ajoute une nouvelle pi�ce au tapis.
	 * 
	 * @param piece
	 */
	public void poserPiece(ComponentPiece piece){
		this.memoire.put(piece);
	}
	
	public void retirerPiece(ComponentPiece piece){
		this.memoire.remove(piece);
	}
	
	public Set<ComponentPiece> chercherPiece(IRect r){
		 return this.memoire.get(r);
	}
	
	public Set<ComponentPiece> chercherPiece(double x,double y){
		 return this.memoire.get(x,y);
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
	
	
	
}
