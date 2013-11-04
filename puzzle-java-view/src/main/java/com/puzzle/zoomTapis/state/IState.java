package com.puzzle.zoomTapis.state;




public interface IState {
	public void mouseMove(int x, int y, boolean isShiftDown);
	public void mouseDrag(int x, int y);
	public void mouseWheel(boolean up);
	public void mouseLeftPressed(int x, int y);
	public void mouseRightPressed(int x, int y);
	public void mouseRightReleased(int x, int y);
	public void keyShiftPressed();
	public void keyShiftReleased();
	public void keyControlPressed();
}
