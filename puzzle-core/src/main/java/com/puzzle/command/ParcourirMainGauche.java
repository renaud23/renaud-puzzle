package com.puzzle.command;

import com.puzzle.command.param.ParcourirMainGaucheParam;
import com.puzzle.model.MainGauche;

public class ParcourirMainGauche implements CommandeArgument<ParcourirMainGaucheParam>{
	
	private ParcourirMainGaucheParam param;

	@Override
	public void execute() {
		if(this.param.isUp())
			MainGauche.getInstance().focusedNext();
		else
			MainGauche.getInstance().focusedPrevious();
		this.param.setFocused(MainGauche.getInstance().getFocused());
	}

	@Override
	public void setArgument(ParcourirMainGaucheParam param) {
		this.param = param;
	}

}
