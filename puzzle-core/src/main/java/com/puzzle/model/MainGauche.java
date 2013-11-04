package com.puzzle.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainGauche implements Iterable<Piece>{
	private static MainGauche instance;
	
	private int size;
	private List<Piece> pieces;
	private int focused;
	private Piece lastIn;
	
	
	private MainGauche(){
		this.size = 20;
		this.focused = -1;
		this.pieces = new ArrayList<Piece>(20);
	}
	
	public Piece removeFocused(){
		Piece foc = null;
		if(this.focused != -1){
			foc = this.pieces.remove(this.focused);
			this.focused = -1;
		}
		
		return foc;
	}
	
	public void focusedNext(){
		if(!this.pieces.isEmpty()){
			this.focused++;
			if(this.focused >= this.pieces.size()) this.focused = 0;
		}
	}
	
	public void focusedPrevious(){
		if(!this.pieces.isEmpty()){ 
			this.focused--;
			if(this.focused <0 ) this.focused = this.pieces.size()-1;
		}
	}
	
	public void focused(Piece p){
		if(this.pieces.contains(p)){
			int i = 0;
			for(Piece pi : this.pieces){
				if(p == pi) this.focused = i;
					
				i++;
			}
		}
	}
	
	public void addPiece(Piece piece){
		if(this.pieces.size() < this.size){
			this.pieces.add(piece);
			this.lastIn = piece;
			this.focused = -1;
		}
	}
	
	public boolean isFull(){
		return this.pieces.size() == this.size;
	}
	
	
	public int getFocused(){
		return this.focused;
	}
	
	public static MainGauche getInstance(){
		if(instance == null) instance = new MainGauche();
		return instance;
	}

	public boolean isFocused(){
		return this.focused != -1;
	}
	
	public boolean isEmpty(){
		return this.pieces.isEmpty();
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size){
		this.size = size;
	}

	public void libere(){
		this.focused = -1;
		this.pieces.clear();
	}
	
	

	public Piece getLastIn() {
		return lastIn;
	}

	@Override
	public Iterator<Piece> iterator() {
		return this.pieces.iterator();
	}
}
