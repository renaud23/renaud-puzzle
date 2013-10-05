package com.puzzle.view.TapisStateContoller;

import java.util.Observable;
import java.util.Observer;

import com.puzzle.model.Point;
import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.controller.IController;
import com.puzzle.view.controller.TapisConverter;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.zoomTapis.TapisZoomConverteur;
import com.puzzle.view.zoomTapis.TapisZoomDrawer;

public class TapisZoomControllerEx implements IController,Observer,ControllerState{
	
	private Tapis tapis;
	private Fenetre fenetre;
	private IDrawer tapisDrawer;
	private TapisConverter converter;
	private Point mousePosition;
	
	private State etatCourant;
	
	private boolean rightClick;
	private boolean shiftPressed;
	
	

	public TapisZoomControllerEx(Fenetre fenetre,Tapis tapis) {
		this.tapis = tapis;
		this.fenetre = fenetre;
		
		
		this.converter = new TapisZoomConverteur(fenetre.getOffscreen(), tapis);
		this.tapisDrawer = new TapisZoomDrawer(this.tapis,this.fenetre.getBuffer(0),this.converter);
		
		
		this.tapis.addObserver(this);
		this.mousePosition = new Point();
		this.etatCourant = new EtatMainVide(this);
		
		this.tapisDrawer.draw();
		this.fenetre.repaint();
		
	}
	
	
	
	
	private void move(double vx,double vy){
		Point p = new Point(vx,vy);
		
		((TapisZoomConverteur)this.converter).moveTo(p);
		
//		if(!this.mainVide){
//			this.selectionParam.setPosition(new Point(this.mousePosition.getX(),this.mousePosition.getY()));
//			this.selectionDrawer.clean();
//			this.selectionDrawer.draw();
//		}
		
		
		this.tapisDrawer.draw();
		this.fenetre.repaint();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
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
	public void mouseRightPressed(int x, int y) {
		this.mousePosition.setX(x);
		this.mousePosition.setY(y);
		this.rightClick = true;
	}

	@Override
	public void mouseRightReleased(int x, int y) {
		this.mousePosition.setX(x);
		this.mousePosition.setY(y);
		this.rightClick = false;
	}

	@Override
	public void mouseMove(int x, int y, boolean isShiftDown) {
		this.mousePosition.setX(x);
		this.mousePosition.setY(y);
		
	}

	@Override
	public void mouseDrag(int x, int y) {
		if(this.rightClick){
			double vx = this.mousePosition.getX() - x;
			double vy = this.mousePosition.getY() - y;
			
			this.move(vx, vy);
		}
		this.mousePosition.setX(x);
		this.mousePosition.setY(y);
	}

	@Override
	public void mouseWheel(boolean up) {
		if(up)	this.etatCourant.handle(StateEvent.wheelUp);
		else this.etatCourant.handle(StateEvent.wheelDown);
	}

	@Override
	public void keyShiftPressed() {
		this.shiftPressed = true;
		
	}

	@Override
	public void keyShiftReleased() {
		this.shiftPressed = false;
		
	}


	@Override
	public Point getMousePosition() {
		return this.mousePosition;
	}

}
