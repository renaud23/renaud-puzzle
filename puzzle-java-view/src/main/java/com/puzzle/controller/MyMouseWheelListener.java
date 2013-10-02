package com.puzzle.controller;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MyMouseWheelListener implements MouseWheelListener{

	private IController controller;
	
	
	public MyMouseWheelListener(IController controller) {
		this.controller = controller;
	}



	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		boolean up = e.getPreciseWheelRotation() < 0;
		this.controller.mouseWheel(up);
	}

}
