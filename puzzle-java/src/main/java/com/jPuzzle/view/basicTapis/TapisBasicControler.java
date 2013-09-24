package com.jPuzzle.view.basicTapis;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;

import com.jPuzzle.view.controler.ITapisControler;
import com.puzzle.command.AttrapperMainDroite;
import com.puzzle.command.CommandeLocalisee;
import com.puzzle.command.PoserMainDroite;
import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;





public class TapisBasicControler implements ITapisControler,MouseListener,MouseMotionListener,Observer{
	
	private boolean mainDroiteVide;
	private Tapis tapis;
	private CommandeLocalisee attraper;
	private CommandeLocalisee poser;
	
	
	
	


	public TapisBasicControler(Tapis tapis){
		this.tapis = tapis;
		this.mainDroiteVide = true;
		
		// TODO IOC
		this.attraper = new AttrapperMainDroite(tapis);
		this.poser = new PoserMainDroite(tapis);
	}
	
	
	@Override
	public void attraperMainDroite(Point position) {
		this.attraper.setPosition(position);
		this.attraper.execute();
	}
	
	
	@Override
	public void poserMainDroite(Point position) {
		this.poser.setPosition(position);
		this.poser.execute();
	}

	
	
	/* **** */
	
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point p = new Point(e.getX(), e.getY());
		TapisBasicConverter.getInstance().convertScreenToModel(p);
		
		if(this.mainDroiteVide){
			this.attraperMainDroite(p);
		} else {
			this.poserMainDroite(p);
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		
	}


	@Override
	public void update(Observable o, Object a) {
		if(a instanceof State){
			State s = (State) a;
			if(s.equals(State.MainDroitePleine)) this.mainDroiteVide = false;
			else if(s.equals(State.MainDroiteVide)) this.mainDroiteVide = true;
		}
		
	}


	

	

	

}
