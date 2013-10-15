package com.puzzle.view;

public class RepaintTask extends Thread{
	
	private Fenetre fenetre;

	
	
	public RepaintTask(Fenetre fenetre) {
		this.fenetre = fenetre;
	}



	@Override
	public void run() {
		fenetre.repaint();
	}
	
}
