package com.puzzle.view.TapisStateContoller;

import com.puzzle.model.Point;
import com.puzzle.view.controller.IController;

public class EtatMainVide implements State{
	
	private IController controller;
	private Point param;
	

	
	
	public EtatMainVide(IController controller) {
		this.controller = controller;
	}




	@Override
	public void handle(StateEvent event) {
		// TODO Auto-generated method stub
		
	}

	




}
