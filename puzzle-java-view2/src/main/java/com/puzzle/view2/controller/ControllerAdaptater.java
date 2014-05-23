package com.puzzle.view2.controller;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public abstract class ControllerAdaptater implements IController{
	
	protected int zIndex;
	protected Rectangle rectangle;
	

	@Override
	public int compareTo(IController o) {
		int v = 0;
		if(this.zIndex > o.getZIndex()) v=1;
		else if(this.zIndex < o.getZIndex()) v=-1;
		return v;
	}

	@Override
	public int getZIndex() {
		return this.zIndex;
	}

	@Override
	public void setZIndex(int zIndex) {
		this.zIndex = zIndex;
	}

	@Override
	public boolean contains(int x, int y) {
		return x>=rectangle.x && x<=rectangle.getMaxX() && y>=rectangle.y && y<=rectangle.getMaxY();
	}

	@Override
	public Rectangle getRectangle(){
		return this.rectangle;
	}

	@Override
	public boolean isOver(IController c){
		return rectangle.intersects(c.getRectangle());
	}

	
	
	
	
	
	
	
	
	
	
	/*
	 * event
	 */
	
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
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
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}
