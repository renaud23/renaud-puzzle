package com.puzzle.view.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MyMouseMotionListener extends MouseMotionAdapter{
	private IController controler;

	public MyMouseMotionListener(IController controler) {
		this.controler = controler;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	
		this.controler.mouseMove(e.getPoint().x, e.getPoint().y,e.isShiftDown());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		this.controler.mouseDrag(e.getX(), e.getY());
	}
	
	
	
	
}
