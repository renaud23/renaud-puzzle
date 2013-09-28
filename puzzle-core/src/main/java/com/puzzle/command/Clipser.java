package com.puzzle.command;

import com.puzzle.model.ComponentPiece;
import com.puzzle.model.Tapis;

public class Clipser implements CommandeArgument<ClipsParam>{
	private Tapis tapis;
	private ClipsParam param;
	
	
	public Clipser(Tapis tapis) {
		this.tapis = tapis;
	}
	
	
	@Override
	public void execute() {
		
		System.out.println("p");
	}

	@Override
	public void setArgument(ClipsParam arg) {
		this.param = arg;
	}

}
