package com.jPuzzle.view.drawer;

public interface IDrawable {
	public void drawTapis();
	public void drawHud();
	public void repaint();
	public <U> void setParam(U u);
}
