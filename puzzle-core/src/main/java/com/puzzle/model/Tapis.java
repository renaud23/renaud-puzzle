package com.puzzle.model;





import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import com.renaud.manager.IRect;
import com.renaud.manager.TasManager;




public class Tapis extends Observable implements Iterable<Piece>{
	private TasManager<Piece> memoire;
	
	private double largeur;
	
	private double hauteur;
	
	private List<Puzzle> puzzles;
	
	public Tapis(){
		this.puzzles = new ArrayList<Puzzle>();
	}


	public Tapis(double largeur, double hauteur) {
		this();
		this.largeur = largeur;
		this.hauteur = hauteur;
		
		this.memoire = new TasManager<Piece>(10, (largeur / 2.0) * -1.0, hauteur / 2.0, largeur, hauteur);
	}

	
	/**
	 * Pose une pièce sur le tapis.
	 * 
	 * @param piece
	 */
	public void poserPiece(Piece piece){
		Set<Piece> set = this.memoire.get(piece.getRect());
		int index = 0;
		
		for(Piece c : set){
			if(c.getZIndex() > index){
				index = c.getZIndex();
			}	
		}
		index++;
		piece.setZIndex(index);
		this.memoire.put(piece);
	}
	
	
	public void poserComposite(CompositePiece composite){
		int z = 0;
		for(Piece p : composite){
			this.memoire.remove(p);
			this.poserPiece(p);
			if(p.getZIndex() > z) z = p.getZIndex();
			p.setZIndex(0);
		}
		
		for(Piece p : composite){
			p.setZIndex(z);
		}
	}
	
	
	
	
	
	public void ajouterAComposite(CompositePiece cmp,Piece p){
		this.memoire.remove(p);
		cmp.addComponent(p);
		this.memoire.put(p);
	}
	
	
	public void retirerPiece(Piece piece){
		this.memoire.remove(piece);
	}
	
	public void retirerComposite(CompositePiece piece){
		
	}
	
	
	
	public Set<Piece> chercherPiece(IRect r){// TODO
		 return this.memoire.get(r);
	}
	
	public List<Piece> chercherPiece(double x,double y){
		Set<Piece> set = this.memoire.get(x,y);
		List<Piece> tmp = new ArrayList<Piece>();

		for(Piece cmp : set){
			if(cmp.getRect().contains(x, y)) {
				tmp.add(cmp);
			}	
		}
		
		return tmp;
	}
	
	public void poser(Puzzle puzzle){
		this.puzzles.add(puzzle);
		for(Piece p : puzzle.getPieces()){
			p.poser(this);
		}
	}
	
	
	@Override
	public Iterator<Piece> iterator() {
		Set<Piece> l =  this.memoire.getAll();
		List<Piece> o = new ArrayList<Piece>(l);
		Collections.sort(o);
		
		return o.iterator();
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

	public Collection<Puzzle> getPuzzles(){
		return this.puzzles;
	}

	public void change(){
		this.setChanged();
	}
	
	
}
