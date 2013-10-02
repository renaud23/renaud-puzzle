package com.puzzle.view.drawer;

import java.util.ArrayList;
import java.util.List;

import com.puzzle.model.Piece;
import com.puzzle.model.Point;

public class DrawSelectionParam {
	private Point position;
	private List<Piece> candidats = new ArrayList<Piece>();
	
	
	
	
	
	
	
	public void addCandidats(List<Piece> l){
		this.candidats.addAll(l);
	}
	
	public void clearCandidats(){
		this.candidats.clear();
	}
	
	public List<Piece> getCandidats() {
		return candidats;
	}

	public void setCandidats(List<Piece> candidats) {
		this.candidats = candidats;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
	
	
	
	
	
	
	
	
	
}
