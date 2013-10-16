package com.puzzle.view;

import javax.swing.SwingUtilities;

import com.puzzle.view.drawer.IDrawer;

public class DrawTask extends Thread{
	
	private IDrawer draw;
	private Fenetre fenetre;


	public DrawTask(IDrawer draw,Fenetre fenetre) {
		this.draw = draw;
		this.fenetre = fenetre;
		this.start();
	}


	@Override
	public void run() {
		this.draw.draw();
		SwingUtilities.invokeLater(new RepaintTask(this.fenetre));
	}

}
