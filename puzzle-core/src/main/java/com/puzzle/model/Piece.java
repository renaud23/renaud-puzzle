package com.puzzle.model;

import com.renaud.manager.IRect;


public class Piece implements ComponentPiece,Comparable<Piece>{
	
	private int id;
	
	private double largeur;
	
	private double hauteur;
	
	private Point centre;
	
	private MyRect rect;
	
	private int zIndex;
	
	private double angle;
	
	private double puzzleX;
	
	private double puzzleY;
	
	private Puzzle puzzle;
	
	private CompositePiece composite;
	
	public Piece(){
		this.centre = new Point();
	}
	
	
	
	
	public Piece(int id,double puzzleX,double puzzleY,double largeur,double hauteur){
		this.id = id;
		this.centre = new Point();
		this.centre.setX(0.0);
		this.centre.setY(0.0);
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.rect = new RectPiece(this);
		this.puzzleX = puzzleX;
		this.puzzleY = puzzleY;
	}
	
	public Piece(int id,double x,double y,double puzzleX,double puzzleY,double largeur,double hauteur){
		this.id = id;
		this.centre = new Point();
		this.centre.setX(x);
		this.centre.setY(y);
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.rect = new RectPiece(this);
		this.puzzleX = puzzleX;
		this.puzzleY = puzzleY;
	}
	
	
	

	
	

	@Override
	public IRect getRect() {
		return this.rect;//((RectPiece)this.rect).clone();
	}
	
	public String toString(){
		return "[Piece id="+this.id+" centre="+this.centre.toString()+" largeur="+this.largeur+" hauteur="+this.hauteur+" rect="+this.rect.toString()+" zIndex="+this.zIndex+" angle="+this.angle+"]";
	}
	
	public Point getCentre() {
		return centre;
	}
	
	public void setCentre(Point centre) {
		this.centre = centre;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
		((RectPiece)this.rect).update();
	}

	@Override
	public int getZIndex() {
		return this.zIndex;
	}

	@Override
	public void setZIndex(int index) {
		this.zIndex = index;
	}

	public CompositePiece getComposite() {
		return composite;
	}

	public void setComposite(CompositePiece composite) {
		this.composite = composite;
	}

	public double getPuzzleX() {
		return puzzleX;
	}

	public void setPuzzleX(double puzzleX) {
		this.puzzleX = puzzleX;
	}

	public double getPuzzleY() {
		return puzzleY;
	}


	public void setPuzzleY(double puzzleY) {
		this.puzzleY = puzzleY;
	}

	public Puzzle getPuzzle() {
		return puzzle;
	}

	public void setPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
//		puzzle.put(this.getId(), new Point(this.getPuzzleX(), this.getPuzzleY()));
	}




	@Override
	public int compareTo(Piece p) {
		int value = 0;
		if(this.zIndex > p.zIndex){
			value = 1;
		}else if(this.zIndex < p.zIndex){
			value = -1;
		}
		
		return value;
	}

	public void setY(double y){
		this.centre.setY(y);
	}
	
	public void setX(double x){
		this.centre.setX(x);
	}




	@Override
	public boolean verifierClips(Piece piece) {
		boolean state = false;
		
		if(piece.getAngle() == this.angle){
			
			
			
			
		}
		
		return state;
	}
}
