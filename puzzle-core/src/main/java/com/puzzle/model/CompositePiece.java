package com.puzzle.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.renaud.manager.Rect;




public class CompositePiece implements ComponentPiece,Iterable<ComponentPiece>{

	
	private List<ComponentPiece> pieces;
	
	private Rect rect;
	
	private Point centre;
	
	private int zIndex;
	
	public CompositePiece(){
		this.pieces = new ArrayList<ComponentPiece>();
		this.rect = new Rect();
	}
	

	
	
	@Override
	public Rect getRect() {
		return this.rect.clone();
	}

	@Override
	public Iterator<ComponentPiece> iterator() {
		return this.pieces.iterator();
	}

	public int getzIndex() {
		return zIndex;
	}

	
	public void setzIndex(int zIndex) {
		this.zIndex = zIndex;
	}

	
	
	/* gestion du composite */

	public void add(ComponentPiece cmp){
		this.pieces.add(cmp);
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
	
	
	public List<ComponentPiece> getChild(){
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
	
}
