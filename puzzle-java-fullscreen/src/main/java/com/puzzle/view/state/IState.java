package com.puzzle.view.state;

public interface IState {

	public void wheel(boolean up);
	public void dragRight(int vx,int vy);
	public void pressLeft(int x,int y,boolean shift);
}
