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
import com.puzzle.command.ClipsParam;
import com.puzzle.command.CommandeArgument;
import com.puzzle.command.IsClipsable;
import com.puzzle.command.PoserMainDroite;
import com.puzzle.command.tournerMainDroite;
import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;





public class TapisBasicControler implements ITapisControler,MouseListener,MouseMotionListener,MouseWheelListener,Observer{
	
	private boolean mainDroiteVide;
	private Tapis tapis;
	
	
	private CommandeArgument<Point> attraper;
	private CommandeArgument<Point> poser;
	private CommandeArgument<Double> tourner;
	private CommandeArgument<ClipsParam> iSclips;
	
	
	private IDrawable drawable;
	private Point mousePosition = new Point();


	public TapisBasicControler(Tapis tapis){
		this.tapis = tapis;
		this.mainDroiteVide = true;
		
		// TODO IOC
		this.attraper = new AttrapperMainDroite(tapis);
		this.poser = new PoserMainDroite(tapis);
		this.tourner = new tournerMainDroite();
		this.iSclips = new IsClipsable(tapis);
	}
	
	
	@Override
	public void attraperMainDroite(Point position) {
		this.attraper.setArgument(position);
		this.attraper.execute();
		
		if(!this.mainDroiteVide){
			this.drawable.setParam(this.checkselection());
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
			this.drawable.setParam(this.checkselection());
			this.drawable.drawHud();
			this.drawable.repaint();
		}
	}
	
	
	private Transformation checkselection(){
		Transformation t = new Transformation();
		t.setSx(TapisBasicConverter.getInstance().getScaleX());
		t.setSy(TapisBasicConverter.getInstance().getScaleY());
		t.setRx(this.mousePosition.getX());
		t.setRy(this.mousePosition.getY());
		t.setTx(this.mousePosition.getX());
		t.setTy(this.mousePosition.getY());
		
		return t;
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
