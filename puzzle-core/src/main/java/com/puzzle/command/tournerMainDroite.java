package com.puzzle.command;

import com.puzzle.model.ComponentPiece;
import com.puzzle.model.MainDroite;

public class tournerMainDroite implements CommandeArgument<Boolean>{
	
	
	private boolean gauche;


	@Override
	public void execute() {
		ComponentPiece cmp = MainDroite.getInstance().getContenu();
		
		if(this.gauche)
			cmp.tournerGauche();
		else
			cmp.tournerDroite();
		
		cmp.updateRect();// modif récente becarfull
	}

	@Override
	public void setArgument(Boolean gauche) {
		this.gauche = gauche;
	}

}
