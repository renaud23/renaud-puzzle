package com.puzzle.view;

import com.puzzle.view.drawer.IDrawer;

public class DrawTask extends Thread{
	
	private IDrawer draw;


	public DrawTask(IDrawer draw) {
		this.draw = draw;
		this.start();
	}



	@Override
	public void interrupt() {
		
	}



	@Override
	public void run() {
		this.draw.draw();
	}

}
