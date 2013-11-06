package com.puzzle.view.hud;

import java.util.Observable;
import java.util.Observer;

import com.puzzle.model.State;
import com.puzzle.model.Tapis;
import com.puzzle.view.drawer.IDrawer;

public class Selection implements IDrawer,Observer{
	
	private int mouseX;
	private int mouseY;
	private boolean selection;
	private Tapis tapis;
	
	

	public Selection(Tapis tapis) {
		this.tapis = tapis;
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Observable o, Object param) {
		if(param == State.MainDroitePleine) this.selection = true;
		else if(param == State.gaucheToDroite) this.selection = true;
		else if(param == State.MainDroiteVide) this.selection = false;
		else if(param == State.droiteToGauche) this.selection = false;
		
	}

}
