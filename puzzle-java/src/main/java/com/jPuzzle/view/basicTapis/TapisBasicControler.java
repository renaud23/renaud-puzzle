package com.jPuzzle.view.basicTapis;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Observable;
import java.util.Observer;

import com.jPuzzle.view.controler.ITapisControler;
import com.jPuzzle.view.drawer.IDrawable;
import com.jPuzzle.view.drawer.Transformation;
import com.puzzle.command.AttrapperMainDroite;
import com.puzzle.command.CommandeArgument;
import com.puzzle.command.PoserMainDroite;
import com.puzzle.command.tournerMainDroite;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;





public class TapisBasicControler implements ITapisControler,MouseListener,MouseMotionListener,MouseWheelListener,Observer{
	
	private boolean mainDroiteVide;
	private Tapis tapis;
	
	
	private CommandeArgument<Point> attraper;
	private CommandeArgument<Point> poser;
	private CommandeArgument<Double> tourner;
	
	
	private IDrawable drawable;
	private Point mousePosition = new Point();


	public TapisBasicControler(Tapis tapis){
		this.tapis = tapis;
		this.mainDroiteVide = true;
		
		// TODO IOC
		this.attraper = new AttrapperMainDroite(tapis);
		this.poser = new PoserMainDroite(tapis);
		this.tourner = new tournerMainDroite();
	}
	
	
	@Override
	public void attraperMainDroite(Point position) {
		this.attraper.setArgument(position);
		this.attraper.execute();
		
		if(!this.mainDroiteVide){
			
			this.drawable.setParam(checkPointSaisi());
			
			this.drawable.drawTapis();
			this.drawable.drawHud();
			this.drawable.repaint();
		}
	}
	
	
	@Override
	public void poserMainDroite(Point position) {
		this.poser.setArgument(position);
		this.poser.execute();
		
		this.drawable.drawTapis();
		this.drawable.drawHud();
		this.drawable.repaint();
	}

	@Override
	public void tournerMainDroite(double sens) {
		double angle = Math.PI /8.0;
		angle *= sens;
		this.tourner.setArgument(angle);
		this.tourner.execute();
		
		this.drawable.drawHud();
		this.drawable.repaint();
	}
	
	
	private Transformation checkPointSaisi(){
		double x = MainDroite.getInstance().getX() * TapisBasicConverter.getInstance().getScaleX();
		double y = MainDroite.getInstance().getY() * TapisBasicConverter.getInstance().getScaleY();
		Point p =new Point(this.mousePosition.getX()-x,this.mousePosition.getY()+y);
		Transformation t = new Transformation();
		t.setTx(p.getX());
		t.setTy(p.getY());
		t.setRx(this.mousePosition.getX());
		t.setRy(this.mousePosition.getY());
		t.setSx(TapisBasicConverter.getInstance().getScaleX());
		t.setSy(TapisBasicConverter.getInstance().getScaleY());
		
		return t;
	}
	
	
	/* **** */
	
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		this.mousePosition.setX(e.getX());
		this.mousePosition.setY(e.getY());
		
		if(!this.mainDroiteVide){
			this.drawable.setParam(checkPointSaisi());
			this.drawable.drawHud();
			this.drawable.repaint();
		}
	}
	
	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.mousePosition.setX(e.getX());
		this.mousePosition.setY(e.getY());
		Point p = new Point(e.getX(), e.getY());
		TapisBasicConverter.getInstance().convertScreenToModel(p);
		
		if(this.mainDroiteVide){
			this.attraperMainDroite(p);	
		}else{
			this.poserMainDroite(p);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		this.mousePosition.setX(e.getX());
		this.mousePosition.setY(e.getY());
		double rotation = e.getPreciseWheelRotation();
		
		if(!this.mainDroiteVide){
			this.tournerMainDroite(rotation);
		}
		
		
	}

	@Override
	public void update(Observable o, Object a) {
		if(a instanceof State){
			State s = (State) a;
			if(s.equals(State.MainDroitePleine)) this.mainDroiteVide = false;
			else if(s.equals(State.MainDroiteVide)) this.mainDroiteVide = true;
		}
		
	}


	public IDrawable getDrawable() {
		return drawable;
	}


	public void setDrawable(IDrawable drawable) {
		this.drawable = drawable;
	}


	


	


	

	

	

}
