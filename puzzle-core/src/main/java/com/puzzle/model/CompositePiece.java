package com.puzzle.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.renaud.manager.IRect;


public class CompositePiece implements ComponentPiece,Iterable<Piece>{
 
	private List<Piece> pieces;
	
	private MyRect rect;
	
	private Point centre;
	
	private int zIndex;
	
	private double largeur;
	
	private double hauteur;
	
	public CompositePiece(double x,double y){
		this.pieces = new ArrayList<Piece>();
		this.rect = new RectCompositePiece(this);
		this.centre = new Point(x,y);
	}
		
	
	@Override
	public IRect getRect() {
		return this.rect;//this.rect.clone();
	}

	@Override
	public Iterator<Piece> iterator() {
		return this.pieces.iterator();
	}

	public int getzIndex() {
		return zIndex;
	}
	
	public void setzIndex(int zIndex) {
		this.zIndex = zIndex;
	}
	
	/* gestion du composite */
	
	
	public void addComponent(ComponentPiece cmp){
		if(cmp instanceof CompositePiece) this.addComposite((CompositePiece) cmp);
		else if(cmp instanceof Piece) this.addPiece((Piece) cmp);
	}
	
	private void addComposite(CompositePiece cmp){
		for(Piece p : cmp){
			this.addPiece(p);
		}
	}
	

	private void addPiece(Piece cmp){
		if(this.pieces.isEmpty()) {
			cmp.getCentre().setX(this.centre.getX());
			cmp.getCentre().setY(this.centre.getY());
		}else{
			Piece r = this.pieces.get(0);
			cmp.setZIndex(r.getZIndex());
			cmp.setAngle(r.getAngle());
			
			
			double x = r.getPuzzleX() - cmp.getPuzzleX();
			double y = r.getPuzzleY() - cmp.getPuzzleY();
			
			cmp.getCentre().setX(r.getCentre().getX() - x);
			cmp.getCentre().setY(r.getCentre().getY() + y);
			
//			cmp.getCentre().tourner(r.getAngle(), this.centre);
			
		}
		
		this.pieces.add(cmp);
		cmp.setComposite(this);
		
		((MyRect)cmp.getRect()).update();
		((RectCompositePiece)this.rect).update();
	}
	
	@Override
	public void setAngle(double angle) {
		for(Piece p : this){
			p.setAngle(angle);
		}
//		this.rect.update();
	}
	
	
	public void remove(ComponentPiece cmp){
		if(this.pieces.contains(cmp)){
			this.pieces.remove(cmp);
		}else{
			for(ComponentPiece p : this.pieces){
				if(p instanceof ComponentPiece){
					((CompositePiece)p).remove(cmp);
				}// if
			}// for
		}// else
	}
	
	
	public List<Piece> getChild(){
		return this.pieces;// attention à l'usage.
	}

	@Override
	public int getZIndex() {
		int z = 0;
		if(!this.pieces.isEmpty()){
			z = this.pieces.get(0).getZIndex();
		}
		return z;
	}

	@Override
	public void setZIndex(int index) {
		if(!this.pieces.isEmpty()){
			for(Piece p : this) p.setZIndex(index);
		}
	}

	@Override
	public Point getCentre() {
		return this.centre;
	}

	@Override
	public double getAngle() {
		double angle = 0.0;
		if(!this.pieces.isEmpty()){
			angle = this.pieces.get(0).getAngle();
		}
		return angle;
	}


	@Override
	public boolean verifierClips(Piece piece) {
		boolean state = false;
		for(Piece p : this){
			if(p.verifierClips(piece)) state=true;
		}
	
		return state;
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


	@Override
	public void poser(Tapis tapis) {
		tapis.poserComposite(this);
	}
	
	
	
}
