package com.puzzle.view2.controller;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public interface IController extends Comparable<IController>{
	
	
	
	// pour les classer géo
	public int getZIndex();
	public void setZIndex(int zIndex);
	public boolean contains(int x,int y);
	public Rectangle getRectangle();
	public boolean isOver(IController c);
	
	
	// event
	public void mouseEntered(MouseEvent e);
	public void mouseExited(MouseEvent e);
	public void mouseMoved(MouseEvent e);
	public void mouseDragged(MouseEvent e);
	public void mouseWheelMoved(MouseWheelEvent e);
}
