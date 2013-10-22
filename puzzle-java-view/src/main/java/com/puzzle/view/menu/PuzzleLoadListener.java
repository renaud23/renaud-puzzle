package com.puzzle.view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PuzzleLoadListener implements ActionListener{
	
	private MenuController controller;
	private String name;
	
	
	

	public PuzzleLoadListener(MenuController controller,String name) {
		this.controller = controller;
		this.name = name;
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		this.controller.loadPuzzle(this.name);
	}

}
