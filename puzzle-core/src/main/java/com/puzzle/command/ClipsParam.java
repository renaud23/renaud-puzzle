package com.puzzle.command;

import java.util.List;

import com.puzzle.model.ComponentPiece;
import com.puzzle.model.Piece;

public class ClipsParam {
	private ComponentPiece component;
	private boolean clipsable;
	private List<Piece> candidats;
	
	
	
	
	
	
	public ComponentPiece getComponent() {
		return component;
	}
	public void setComponent(ComponentPiece component) {
		this.component = component;
	}
	public boolean isClipsable() {
		return clipsable;
	}
	public void setClipsable(boolean clipsable) {
		this.clipsable = clipsable;
	}
	public List<Piece> getCandidats() {
		return candidats;
	}
	public void setCandidats(List<Piece> candidats) {
		this.candidats = candidats;
	}
}



