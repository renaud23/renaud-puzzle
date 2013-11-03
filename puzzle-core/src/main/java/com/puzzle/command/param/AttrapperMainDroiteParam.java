package com.puzzle.command.param;

import com.puzzle.model.ComponentPiece;
import com.puzzle.model.Point;

public class AttrapperMainDroiteParam {
	private ComponentPiece contenu;
	private Point position;
	private Point ancre;
	
	
	public ComponentPiece getContenu() {
		return contenu;
	}
	public void setContenu(ComponentPiece contenu) {
		this.contenu = contenu;
	}
	public Point getAncre() {
		return ancre;
	}
	public void setAncre(Point ancre) {
		this.ancre = ancre;
	}
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = position;
	}

	
	
	
}
