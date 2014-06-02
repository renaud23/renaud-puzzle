package com.puzzle.view2.controller;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public interface IController extends Comparable<IController>{
	
	
	
	// pour les classer g�o
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
	public void mousePressed(MouseEvent e);
	public void mouseReleased(MouseEvent e);
	
	public void controlPressed();
	public void controlReleased();
	public void shiftPressed();
	public void shiftReleased();
}
