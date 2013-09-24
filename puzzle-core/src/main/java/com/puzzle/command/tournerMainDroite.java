package com.puzzle.command;

import com.puzzle.model.ComponentPiece;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Piece;


public class tournerMainDroite implements CommandeArgument<Double>{
	
	
	private double angle;

	
	
	
	public tournerMainDroite() {
		
	
	}

	@Override
	public void execute() {
		ComponentPiece cmp = MainDroite.getInstance().getPiece();
		
		if(cmp instanceof Piece){
			Piece p = (Piece) cmp;
			p.addAngle(this.angle);
		}
		
	}

	@Override
	public void setArgument(Double arg) {
		this.angle = arg;
	}

}
