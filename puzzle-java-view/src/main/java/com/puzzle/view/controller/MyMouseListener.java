package com.puzzle.view.controller;


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
		if(e.getButton() == MouseEvent.BUTTON1) this.controller.mouseLeftPressed(e.getX(), e.getY());
		else if(e.getButton() == MouseEvent.BUTTON3) this.controller.mouseRightPressed(e.getX(), e.getY());
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) this.controller.mouseLeftReleased(e.getX(), e.getY());
		else if(e.getButton() == MouseEvent.BUTTON3) this.controller.mouseRightPressed(e.getX(), e.getY());
		
	}
	
	
	
	
	
	
}
