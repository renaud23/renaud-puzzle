package com.puzzle.command;

import com.puzzle.model.ComponentPiece;
import com.puzzle.model.Tapis;

public class Clipser implements CommandeArgument<ComponentPiece>{
	private Tapis tapis;
	private ComponentPiece cmp;
	
	
	public Clipser(Tapis tapis) {
		this.tapis = tapis;
	}
	
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setArgument(ComponentPiece arg) {
		this.cmp = arg;
	}

}
