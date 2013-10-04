package com.puzzle.view.controller;

public interface IController {
	public void mouseLeftPressed(int x,int y);
	public void mouseLeftReleased(int x,int y);
	public void mouseMove(int x,int y,boolean isShiftDown);
	public void mouseDrag(int x,int y);
	public void mouseWheel(boolean up);
	public void keyShiftPressed();
	public void keyShiftReleased();
}
