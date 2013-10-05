package com.puzzle.command;



import com.puzzle.model.ComponentPiece;
import com.puzzle.model.Piece;

public class ClipserParam {
	private Piece candidat;
	private ComponentPiece component;
			
	
	public Piece getCandidat() {
		return candidat;
	}
	public void setCandidat(Piece candidat) {
		this.candidat = candidat;
	}
	public ComponentPiece getComponent() {
		return component;
	}
	public void setComponent(ComponentPiece component) {
		this.component = component;
	}	
}
