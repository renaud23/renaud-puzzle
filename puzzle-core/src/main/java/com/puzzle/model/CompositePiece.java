package com.puzzle.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.renaud.manager.Rect;




public class CompositePiece implements ComponentPiece,Iterable<Piece>{

	
	private List<Piece> pieces;
	
	private Rect rect;
	
	private Point centre;
	
	private int zIndex;
	
	public CompositePiece(double x,double y){
		this.pieces = new ArrayList<Piece>();
		this.rect = new Rect();
		this.centre = new Point(x,y);
	}
	

	
	
	@Override
	public Rect getRect() {
		return this.rect.clone();
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

	public void addPiece(Piece cmp){
		
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
			
			cmp.getCentre().tourner(cmp.getAngle(), this.centre);
			
		}
		
		this.pieces.add(cmp);
		cmp.setComposite(this);
		((RectPiece)cmp.getRect()).update();
		((RectPiece)cmp.getRect()).checkAngle();
		
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
		// TODO Auto-generated method stub
		return 0;
	}




	@Override
	public void setZIndex(int index) {
		// TODO Auto-generated method stub
	
	}




	@Override
	public Point getCentre() {
		return this.centre;
	}




	@Override
	public double getAngle() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
