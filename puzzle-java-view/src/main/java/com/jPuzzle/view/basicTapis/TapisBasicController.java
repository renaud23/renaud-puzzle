package com.jPuzzle.view.basicTapis;

import java.util.Observable;
import java.util.Observer;

import com.puzzle.controller.IController;
import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.drawer.IDrawer;

public class TapisBasicController implements IController,Observer{
	
	private Fenetre fenetre;
	private IDrawer tapisDrawer;
	private Tapis tapis;
	
	
	public TapisBasicController(Fenetre fenetre,Tapis tapis){
		this.fenetre = fenetre;
		this.tapis = tapis;
		this.tapisDrawer = new TapisBasicDrawer(tapis, fenetre.getBuffer(0));
		
		this.tapis.addObserver(this);
		
		TapisBasicConverter.getInstance().setOffscreen(this.fenetre.getOffscreen());
		TapisBasicConverter.getInstance().setTapis(this.tapis);
		TapisBasicConverter.getInstance().update();
		
		this.tapisDrawer.draw();
		this.fenetre.repaint();
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
