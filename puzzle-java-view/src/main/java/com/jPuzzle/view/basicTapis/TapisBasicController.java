package com.jPuzzle.view.basicTapis;

import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.drawer.IDrawer;

public class TapisBasicController {
	
	private Fenetre fenetre;
	private IDrawer tapisDrawer;
	private Tapis tapis;
	
	
	public TapisBasicController(Fenetre fenetre,Tapis tapis){
		this.fenetre = fenetre;
		this.tapis = tapis;
		this.tapisDrawer = new TapisBasicDrawer(tapis, fenetre.getBuffer(0));
		
		TapisBasicConverter.getInstance().setOffscreen(this.fenetre.getOffscreen());
		TapisBasicConverter.getInstance().setTapis(this.tapis);
		TapisBasicConverter.getInstance().update();
		
		this.tapisDrawer.draw();
		this.fenetre.repaint();
	}
}
