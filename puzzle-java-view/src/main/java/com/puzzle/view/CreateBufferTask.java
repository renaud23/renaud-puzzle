package com.puzzle.view;

import javax.swing.SwingUtilities;

import com.puzzle.view.drawer.IDrawerSelection;

public class CreateBufferTask extends Thread{

	private IDrawerSelection selection;
	private Fenetre fenetre;
	
	public CreateBufferTask(IDrawerSelection selection,Fenetre fenetre) {
		this.selection = selection;
		this.fenetre = fenetre;
		this.start();
	}
	
	public void run(){
		this.selection.createbuffer();
		this.selection.draw();
		SwingUtilities.invokeLater(new RepaintTask(this.fenetre));
	}
}
