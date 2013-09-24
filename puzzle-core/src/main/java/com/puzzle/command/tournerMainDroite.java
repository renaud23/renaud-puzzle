package com.puzzle.command;

import com.puzzle.model.Tapis;

public class tournerMainDroite implements CommandeArgument<Double>{
	
	private Tapis tapis;
	private double angle;

	
	
	
	public tournerMainDroite(Tapis tapis) {
		super();
		this.tapis = tapis;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setArgument(Double arg) {
		this.angle = arg;
	}

}
