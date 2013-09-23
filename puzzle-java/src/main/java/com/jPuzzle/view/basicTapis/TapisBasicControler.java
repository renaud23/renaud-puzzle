package com.jPuzzle.view.basicTapis;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.jPuzzle.view.controler.ITapisControler;
import com.jPuzzle.view.controler.TapisConverter;
import com.puzzle.command.AttrapperUnePiece;
import com.puzzle.command.ICommand;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Point;
import com.puzzle.model.Tapis;





public class TapisBasicControler implements ITapisControler,MouseListener,MouseMotionListener,KeyListener{
	
	private boolean mainDroiteVide;
	private Tapis tapis;
	private AttrapperUnePiece attraper;
	
	
	
	


	public TapisBasicControler(Tapis tapis){
		this.tapis = tapis;
		this.mainDroiteVide = true;
		
		this.attraper = new AttrapperUnePiece(tapis);
	}
	
	
	@Override
	public void attraperUnePiece(double x, double y) {
		attraper.setX(x);
		attraper.setY(y);
		attraper.execute();
	
		
		System.out.println(MainDroite.getInstance().getPiece());
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
		if(this.mainDroiteVide){
			Point p = new Point(e.getX(), e.getY());
			TapisBasicConverter.getInstance().convertScreenToModel(p);
			this.attraperUnePiece(p.getX(), p.getY());
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
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	

	

}
