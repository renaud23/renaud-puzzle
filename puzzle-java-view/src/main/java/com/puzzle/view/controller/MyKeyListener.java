package com.puzzle.view.controller;

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
		else if(e.getKeyCode() == KeyEvent.VK_S && e.isControlDown()) this.controler.controlPlusS();
		else if(e.getKeyCode() == KeyEvent.VK_L && e.isControlDown()) this.controler.controlPlusL();
		else if(e.getKeyCode() == KeyEvent.VK_CONTROL) this.controler.keyControlPressed();

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SHIFT) this.controler.keyShiftReleased();
		else if(e.getKeyCode() == KeyEvent.VK_CONTROL) this.controler.keyControlReleased();
	}
	
	

}
