package com.puzzle.view;

public class RepaintTask implements Runnable{
	
	private Fenetre fenetre;

	
	
	public RepaintTask(Fenetre fenetre) {
		this.fenetre = fenetre;
	}



	@Override
	public void run() {
		fenetre.repaint();
	}
	
}
