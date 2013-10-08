package com.puzzle.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainGauche implements Iterable<Piece>{
	private static MainGauche instance;
	
	private int size;
	private List<Piece> pieces;
	private int focused;
	
	
	private MainGauche(){
		this.size = 20;
		this.focused = -1;
		this.pieces = new ArrayList<Piece>(20);
	}
	
	
	
	public void addPiece(Piece piece){
		if(this.pieces.size() < this.size){
			this.pieces.add(piece);
			this.focused = -1;
		}
	}
	
	public boolean isFull(){
		return this.pieces.size() == this.size;
	}
	
	public void removePiece(Piece piece){
		this.pieces.remove(piece);
		this.focused = -1;
	}
	
	
	public static MainGauche getInstance(){
		if(instance == null) instance = new MainGauche();
		return instance;
	}

	public int getSize() {
		return size;
	}


	@Override
	public Iterator<Piece> iterator() {
		return this.pieces.iterator();
	}
}
