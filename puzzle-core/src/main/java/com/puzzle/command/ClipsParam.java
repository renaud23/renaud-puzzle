package com.puzzle.command;

import java.util.List;

import com.puzzle.model.ComponentPiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;

public class ClipsParam {
	
	private Point centre;
	private List<Piece> candidats;
	
	
	
	
	
	
	public List<Piece> getCandidats() {
		return candidats;
	}
	public void setCandidats(List<Piece> candidats) {
		this.candidats = candidats;
	}
	
	public Point getCentre() {
		return centre;
	}
	public void setCentre(Point centre) {
		this.centre = centre;
	}
	
	
}



