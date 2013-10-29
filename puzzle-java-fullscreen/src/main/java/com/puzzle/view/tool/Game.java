package com.puzzle.view.tool;



import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.puzzle.model.Tapis;
import com.puzzle.view.core.Activater;
import com.puzzle.view.core.TapisConverteur;
import com.puzzle.view.state.IState;
import com.puzzle.view.state.MainVide;





public class Game implements Activater, MouseListener, MouseMotionListener, MouseWheelListener{
	
	
	private Tapis tapis;
	private TapisConverteur converter;
	private IState state;

	

	public TapisConverteur getConverter() {
		return converter;
	}








	public Game(Tapis tapis, TapisConverteur converter) {
		
		this.tapis = tapis;
		this.converter = converter;
		this.state = new MainVide(this);
	}








	public void activate() {
		// TODO Auto-generated method stub

	}





	/*
	 * mouseListener
	 */
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		boolean up = e.getPreciseWheelRotation() < 0;
		this.state.wheel(up);
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	/*
	 * ascesseurs.
	 */
	
	public IState getState() {
		return state;
	}
	public void setState(IState state) {
		this.state = state;
	}
	public Tapis getTapis() {
		return tapis;
	}
}
