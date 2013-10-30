package com.puzzle.view.state;

public interface IState {

	public void wheel(boolean up,boolean shift);
	public void dragRight(int vx,int vy,boolean shift);
	public void pressLeft(int x,int y,boolean shift);
	public void shiftReleased(int x,int y);
	public void shiftPressed(int x,int y);
}
