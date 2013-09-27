package com.puzzle.command;

import com.puzzle.model.ComponentPiece;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Piece;


public class tournerMainDroite implements CommandeArgument<Double>{
	
	
	private double angle;


	@Override
	public void execute() {
		ComponentPiece cmp = MainDroite.getInstance().getPiece();
		
//		if(cmp instanceof Piece){
//			Piece p = (Piece) cmp;
		double newAngle = cmp.getAngle();
		newAngle += this.angle;
		cmp.setAngle(newAngle);
//		}
		
	}

	@Override
	public void setArgument(Double arg) {
		this.angle = arg;
	}

}
