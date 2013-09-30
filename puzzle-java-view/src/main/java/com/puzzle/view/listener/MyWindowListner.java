package com.puzzle.view.listener;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import com.puzzle.view.Fenetre;


public class MyWindowListner implements ComponentListener{
	
	private Fenetre fenetre;

	public MyWindowListner(Fenetre fenetre) {
		this.fenetre = fenetre;
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
	
		int w = this.fenetre.getOffscreen().getWidth();
		int h = this.fenetre.getOffscreen().getHeight();
	
		if(w>0 && h>0)
			this.fenetre.resize(w, h);
//		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
