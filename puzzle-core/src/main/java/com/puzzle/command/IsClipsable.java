package com.puzzle.command;

import com.puzzle.model.Tapis;

public class IsClipsable implements CommandeArgument<ClipsParam>{
	
	private Tapis tapis;
	private ClipsParam param;
	
	

	public IsClipsable(Tapis tapis) {
		this.tapis = tapis;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setArgument(ClipsParam arg) {
		this.param = arg;
	}

}
