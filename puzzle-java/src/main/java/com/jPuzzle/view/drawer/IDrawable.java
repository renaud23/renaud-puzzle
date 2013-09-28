package com.jPuzzle.view.drawer;

public interface IDrawable<U> {
	public void drawTapis();
	public void drawHud();
	public void drawSelection();
	public void repaint();
	public void setParam(U u);
}
