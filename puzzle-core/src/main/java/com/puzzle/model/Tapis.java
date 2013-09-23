package com.puzzle.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.renaud.manager.IRect;
import com.renaud.manager.TasManager;




public class Tapis implements Iterable<ComponentPiece>{
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
	 * Ajoute une nouvelle pièce au tapis.
	 * 
	 * @param piece
	 */
	public void poserPiece(ComponentPiece piece){
		Set<ComponentPiece> set = this.memoire.get(piece.getRect());
		int index = 0;
		for(ComponentPiece c : set){
			if(c.getZIndex() > index)
				index = c.getZIndex();
		}
		index++;
		piece.setZIndex(index);
		
		this.memoire.put(piece);
	}
	
	
	
	public void retirerPiece(ComponentPiece piece){
		this.memoire.remove(piece);
	}
	
	public Set<ComponentPiece> chercherPiece(IRect r){// TODO
		 return this.memoire.get(r);
	}
	
	public List<ComponentPiece> chercherPiece(double x,double y){
		
		Set<ComponentPiece> set = this.memoire.get(x,y);
		List<ComponentPiece> tmp = new ArrayList<ComponentPiece>();
		
		for(ComponentPiece cmp : set){
			
			if(cmp.getRect().contains(x, y)) {
				tmp.add(cmp);
			}
			
		}
		
		return tmp;
	}
	
	
	
	@Override
	public Iterator<ComponentPiece> iterator() {
		return this.memoire.getAll().iterator();
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
