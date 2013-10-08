package com.puzzle.command;

import com.puzzle.model.MainDroite;
import com.puzzle.model.MainGauche;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;

public class PasserDansMainDroite implements Commande {
	private Tapis tapis;
	
	
	public PasserDansMainDroite(Tapis tapis) {
		this.tapis = tapis;
	}




	@Override
	public void execute() {
		if( MainGauche.getInstance().isFocused() &&
			MainDroite.getInstance().isEmpty()){
			
			Piece p = MainGauche.getInstance().removeFocused();
			
			MainDroite.getInstance().setPiece(p);
			MainDroite.getInstance().setAncre(new Point(0,0));
			
			MainDroite.getInstance().libere();
			this.tapis.change();
			this.tapis.notifyObservers(State.gaucheToDroite);
		}
		
	}
	
}
