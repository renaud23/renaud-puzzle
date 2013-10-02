package com.puzzle.controller;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseListener extends  MouseAdapter{
	private IController controller;

	public MyMouseListener(IController controller) {
		this.controller = controller;
	}

	

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.controller.mousePressed(e.getX(), e.getY());
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
}
