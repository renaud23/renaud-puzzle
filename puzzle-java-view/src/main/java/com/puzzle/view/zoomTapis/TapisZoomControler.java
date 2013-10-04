package com.puzzle.view.zoomTapis;

import java.util.Observable;
import java.util.Observer;

import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.controller.IController;
import com.puzzle.view.controller.TapisConverter;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.drawer.IDrawerParametrable;

public class TapisZoomControler implements IController,Observer{
	
	private Tapis tapis;
	private Fenetre fenetre;
	private IDrawerParametrable<Point> tapisDrawer;
	private TapisConverter converter;
	private Point mousePosition;
	
	
	private boolean mainVide;
	

	public TapisZoomControler(Fenetre fenetre, Tapis tapis) {
		this.tapis = tapis;
		this.fenetre = fenetre;
		this.tapisDrawer = new TapisZoomDrawer();
		this.converter = new TapisZoomConverteur(fenetre.getOffscreen());
		this.mousePosition = new Point();
		
		this.tapisDrawer.draw();
		this.fenetre.repaint();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof State){
			State st = (State) arg;
			if(st == State.MainDroitePleine)
				this.mainVide = false;
			else if(st == State.MainDroiteVide)
				this.mainVide = true;
		}
	}

	@Override
	public void mouseLeftPressed(int x, int y) {
		this.mousePosition.setX(x);
		this.mousePosition.setY(y);
		
		
		
		
	}

	@Override
	public void mouseLeftReleased(int x, int y) {
		this.mousePosition.setX(x);
		this.mousePosition.setY(y);
		
	}

	@Override
	public void mouseMove(int x, int y, boolean isShiftDown) {
		this.mousePosition.setX(x);
		this.mousePosition.setY(y);
		
	}

	@Override
	public void mouseWheel(boolean up) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyShiftPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyShiftReleased() {
		// TODO Auto-generated method stub
		
	}

}
