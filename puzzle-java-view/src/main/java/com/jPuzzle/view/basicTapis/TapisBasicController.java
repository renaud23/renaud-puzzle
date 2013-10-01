package com.jPuzzle.view.basicTapis;

import java.util.Observable;
import java.util.Observer;

import com.puzzle.controller.IController;
import com.puzzle.controller.TapisConverter;
import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.drawer.IDrawer;

public class TapisBasicController implements IController,Observer{
	
	private Fenetre fenetre;
	private TapisConverter converter;
	private IDrawer tapisDrawer;
	private Tapis tapis;
	
	
	public TapisBasicController(Fenetre fenetre,Tapis tapis){
		this.fenetre = fenetre;
		this.tapis = tapis;
		
		// IOC
		this.converter = new TapisBasicConverter();
		((TapisBasicConverter)this.converter).setOffscreen(this.fenetre.getOffscreen());
		((TapisBasicConverter)this.converter).setTapis(tapis);
		((TapisBasicConverter)this.converter).update();
		this.tapisDrawer = new TapisBasicDrawer(tapis, fenetre.getBuffer(0),this.converter);
		
		
		
		
		this.tapis.addObserver(this);
		
		
		this.tapisDrawer.draw();
		this.fenetre.repaint();
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
