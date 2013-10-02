package com.puzzle.command;

import com.puzzle.model.ComponentPiece;
import com.puzzle.model.MainDroite;

public class tournerMainDroite implements CommandeArgument<Boolean>{
	
	
	private boolean gauche;


	@Override
	public void execute() {
		ComponentPiece cmp = MainDroite.getInstance().getPiece();
		
		if(this.gauche)
			cmp.tournerGauche();
		else
			cmp.tournerDroite();
	}

	@Override
	public void setArgument(Boolean gauche) {
		this.gauche = gauche;
	}

}
