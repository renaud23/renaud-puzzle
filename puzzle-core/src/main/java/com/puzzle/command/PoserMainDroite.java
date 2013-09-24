package com.puzzle.command;

import com.puzzle.model.Point;
import com.puzzle.model.Tapis;

public class PoserMainDroite implements CommandeLocalisee{
	private Tapis tapis;
	private Point position;

	public PoserMainDroite(Tapis tapis) {
		super();
		this.tapis = tapis;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPosition(Point point) {
		this.position = point;
	}

	

}
