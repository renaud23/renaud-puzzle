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
		return this.rect;
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

	private void addPiece(Piece p){
		if(this.pieces.isEmpty()) {
			p.getCentre().setX(this.centre.getX());
			p.getCentre().setY(this.centre.getY());
		}else{
			Piece r = this.pieces.get(0);
			p.setZIndex(r.getZIndex());
			p.setAngle(r.getAngle());
			
			double x = r.getPuzzleX() - p.getPuzzleX();
			double y = r.getPuzzleY() - p.getPuzzleY();
			
			p.getCentre().setX(r.getCentre().getX() - x);
			p.getCentre().setY(r.getCentre().getY() + y);
			
			p.getCentre().tourner(r.getAngle(), r.getCentre());
			
		}
		
		this.pieces.add(p);
		p.setComposite(this);
		
		((MyRect)p.getRect()).update();
		((RectCompositePiece)this.rect).updateSize();
		
		Piece r = this.pieces.get(0);
		double x = r.getPuzzleX() - ((RectCompositePiece)this.rect).getPuzzX();
		double y = r.getPuzzleY() - ((RectCompositePiece)this.rect).getPuzzY();
		Point nc = new Point(r.getCentre().getX() - x,r.getCentre().getY() - y);
		nc.tourner(this.getAngle(), r.getCentre());
		this.centre.setX(nc.getX());
		this.centre.setY(nc.getY());
	}	
	

	public int getTaille(){
		return this.pieces.size();
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
		return this.pieces;// attention � l'usage.
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
//		this.rect.update();
		tapis.poser(this);
	}


	@Override
	public void tournerGauche() {
		for(Piece p : this) p.tournerGauche();
	}


	@Override
	public void tournerDroite() {
		for(Piece p : this) p.tournerDroite();
	}
	
	@Override
	public double getAngle() {
		double angle = 0.0;
		if(!this.pieces.isEmpty()){
			angle = this.pieces.get(0).getAngle();
		}
		return angle;
	}
	
	public Puzzle getPuzzle(){
		Puzzle p = null;
		if(!this.pieces.isEmpty()) p = this.pieces.get(0).getPuzzle();
		
		return p;
	}
	
	public void updateRect(){
		this.rect.update();
	}


	@Override
	public int getAngleIndex() {
		int angle = -1;
		if(!this.pieces.isEmpty()) angle = this.pieces.get(0).getAngleIndex();
		
		return angle;
	}


	@Override
	public void setAngleIndex(int index) {
		for(Piece p : this.pieces) p.setAngleIndex(index);
	}
}
