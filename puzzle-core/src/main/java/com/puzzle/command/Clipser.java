package com.puzzle.command;

import java.util.Set;

import com.puzzle.model.ComponentPiece;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Piece;
import com.puzzle.model.Tapis;

public class Clipser implements CommandeArgument<ClipsParam>{
	private Tapis tapis;
	private ClipsParam param;
	
	
	public Clipser(Tapis tapis) {
		this.tapis = tapis;
	}
	
	
	@Override
	public void execute() {
		ComponentPiece cmp = MainDroite.getInstance().getPiece();
		
		Set<Piece> candidats = this.tapis.chercherPiece(cmp.getRect());
		
		
		for(Piece p : candidats){
			boolean nord;
			boolean sud;
			boolean est;
			boolean ouest;
			
			double xi = cmp.getCentre().getX();
			xi -= p.getCentre().getX();
			double yi = cmp.getCentre().getY();
			yi -= p.getCentre().getY();
			
			if(xi < 0) ouest = true;
			else if(xi > 0) est = true;
		}
	}

	@Override
	public void setArgument(ClipsParam arg) {
		this.param = arg;
	}

}
