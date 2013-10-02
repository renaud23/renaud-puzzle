package com.puzzle.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyKeyListener extends KeyAdapter {
	private IController controler;

	public MyKeyListener(IController controler) {
		this.controler = controler;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SHIFT) this.controler.keyShiftPressed();

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SHIFT) this.controler.keyShiftReleased();

	}
	
	

}
