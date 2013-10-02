package com.puzzle.controller;

public interface IController {
	public void mousePressed(int x,int y);
	public void mouseMove(int x,int y,boolean isShiftDown);
	public void mouseWheel(boolean up);
}
