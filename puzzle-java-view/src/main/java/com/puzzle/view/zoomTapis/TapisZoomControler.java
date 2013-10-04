package com.puzzle.view.zoomTapis;

import java.util.Observable;
import java.util.Observer;

import com.puzzle.command.AttrapperMainDroite;
import com.puzzle.command.CommandeArgument;
import com.puzzle.command.PoserMainDroite;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.controller.IController;
import com.puzzle.view.controller.TapisConverter;
import com.puzzle.view.drawer.DrawSelection;
import com.puzzle.view.drawer.IDrawer;


public class TapisZoomControler implements IController,Observer{
	
	private Tapis tapis;
	private Fenetre fenetre;
	private IDrawer tapisDrawer;
	private TapisConverter converter;
	private Point mousePosition;
	
	
	private boolean mainVide;
	private boolean rightClick;
	

	public TapisZoomControler(Fenetre fenetre, Tapis tapis) {
		this.tapis = tapis;
		this.fenetre = fenetre;
		this.converter = new TapisZoomConverteur(fenetre.getOffscreen(),this.tapis);
		this.tapisDrawer = new TapisZoomDrawer(this.tapis,this.fenetre.getBuffer(0),this.converter);
		
		this.mainVide = true;
		this.rightClick = false;
		
		this.tapis.addObserver(this);
		
		this.mousePosition = new Point();
		
		this.tapisDrawer.draw();
		this.fenetre.repaint();
	}
	
	
	
	private void zoom(boolean up){
		((TapisZoomConverteur)this.converter).zoom(up);
		
		this.tapisDrawer.draw();
		this.fenetre.repaint();
	}
	
	
	
	
	private void move(double vx,double vy){
		Point p = new Point(vx,vy);
		
		((TapisZoomConverteur)this.converter).moveTo(p);
		
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

	
	
	private void attraper(int x,int y){
		Point p = new Point(x, y);
		this.converter.convertScreenToModel(p);
		
		CommandeArgument<Point> cmd = new AttrapperMainDroite(this.tapis);
		cmd.setArgument(p);
		
		cmd.execute();
		
		if(!this.mainVide){
//			this.selectionDrawer = new DrawSelection(
//					this.fenetre.getBuffer(1), 
//					MainDroite.getInstance().getPiece(), 
//					this.converter);
//			this.selectionParam.setPosition(new Point(x,y));
//			this.selectionDrawer.setParam(this.selectionParam);
			
			this.tapisDrawer.draw();
//			this.selectionDrawer.draw();
			this.fenetre.repaint();
		}
	}
	
	
	private void poser(int x,int y){
		Point p = new Point(x, y);
		
		this.converter.convertScreenToModel(p);
		CommandeArgument<Point> cmd = new PoserMainDroite(this.tapis);
		cmd.setArgument(p);
		
		cmd.execute();	
		
//		this.selectionDrawer.clean();
		this.tapisDrawer.draw();
		this.fenetre.repaint();
	}
	
	@Override
	public void mouseLeftPressed(int x, int y) {
		this.mousePosition.setX(x);
		this.mousePosition.setY(y);
	
		if(this.mainVide){
			this.attraper(x, y);
		}else{
			this.poser(x, y);
		}
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
		if(this.mainVide){
			this.zoom(up);
		}
		
	}

	@Override
	public void keyShiftPressed() {
		
		
	}

	@Override
	public void keyShiftReleased() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseDrag(int x, int y) {
		if(this.rightClick){
			double vx = this.mousePosition.getX() - x;
			double vy = this.mousePosition.getY() - y;
			this.mousePosition.setX(x);
			this.mousePosition.setY(y);
			
			this.move(vx, vy);
		}
	}



	@Override
	public void mouseRightPressed(int x, int y) {
		this.rightClick = true;
		
	}



	@Override
	public void mouseRightReleased(int x, int y) {
		this.rightClick = false;
		
	}

}
