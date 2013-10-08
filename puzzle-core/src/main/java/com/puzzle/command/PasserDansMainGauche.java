package com.puzzle.command;

import com.puzzle.command.param.ChangerDeMainParam;
import com.puzzle.model.MainDroite;
import com.puzzle.model.MainGauche;
import com.puzzle.model.Piece;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;



public class PasserDansMainGauche implements CommandeArgument<ChangerDeMainParam>{
	
	private Tapis tapis;
	private ChangerDeMainParam param;
	
	

	public PasserDansMainGauche(Tapis tapis) {
		this.tapis = tapis;
	}


	@Override
	public void execute() {
		boolean reussi = false;
		if(!MainDroite.getInstance().isEmpty() &&
			MainDroite.getInstance().getPiece() instanceof Piece &&
			!MainGauche.getInstance().isFull()){
			
			reussi = true;
			this.param.setPiece((Piece) MainDroite.getInstance().getPiece());
			MainGauche.getInstance().addPiece((Piece) MainDroite.getInstance().getPiece());
			
			MainDroite.getInstance().libere();
			this.tapis.change();
			this.tapis.notifyObservers(State.droiteToGauche);
		}
		
		this.param.setReussi(reussi);
		
	}


	@Override
	public void setArgument(ChangerDeMainParam param) {
		this.param = param;
	}

}
