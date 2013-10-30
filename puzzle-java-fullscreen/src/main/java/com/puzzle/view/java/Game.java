package com.puzzle.view.java;



import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Observable;
import java.util.Observer;

import com.puzzle.model.State;
import com.puzzle.model.Tapis;
import com.puzzle.view.core.Activater;
import com.puzzle.view.core.TapisConverteur;
import com.puzzle.view.state.IState;
import com.puzzle.view.state.MainVide;





public class Game implements Activater, MouseListener, MouseMotionListener, MouseWheelListener,KeyListener,Observer{
	
	
	private Tapis tapis;
	private TapisConverteur converter;
	private IState state;

	private int mouseX;
	private int mouseY;
	private boolean rightClick;
	private boolean leftClick;
	

	








	public Game(Tapis tapis, TapisConverteur converter) {
		this.tapis = tapis;
		this.converter = converter;
		this.state = new MainVide(this);
		tapis.addObserver(this);
	}








	public void activate() {
		// TODO Auto-generated method stub

	}

	
	/*
	 * 
	 * Observer
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof State){
			State st = (State) arg;
			if(st == State.MainDroitePleine){
				
			}else if(st == State.MainDroiteVide){
				
			}
		}
		
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
		if(e.getButton() == MouseEvent.BUTTON1){
			this.leftClick = true;
			this.state.pressLeft(e.getX(), e.getY(), e.isShiftDown());
		}else if(e.getButton() == MouseEvent.BUTTON3) this.rightClick = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) this.leftClick = false;
		else if(e.getButton() == MouseEvent.BUTTON3) this.rightClick = false;
		
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		boolean up = e.getPreciseWheelRotation() < 0;
		this.state.wheel(up,e.isShiftDown());
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(this.rightClick){
			int vx = this.mouseX - e.getX();
			int vy = this.mouseY - e.getY();
			this.mouseX = e.getX();
			this.mouseY = e.getY();
			
			this.state.dragRight(vx, vy,e.isShiftDown());
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
		
	}
	
	
	/*
	 * ascesseurs.
	 */
	public TapisConverteur getConverter() {
		return converter;
	}
	public IState getState() {
		return state;
	}
	public void setState(IState state) {
		this.state = state;
	}
	public Tapis getTapis() {
		return tapis;
	}







	/*
	 * KeyListener
	 * 
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)System.exit(0);	
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SHIFT)this.state.shiftReleased();	
		
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}








	
}
