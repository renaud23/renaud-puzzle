package com.puzzle.zoomTapis.state;

public abstract class StateAdapter implements IState{
	
	
	protected boolean rightClick;
	protected boolean leftClick;
	protected boolean shiftPressed;
	protected boolean controlPressed;
	protected double mouseX;
	protected double mouseY;
	

	@Override
	public void mouseMove(int x, int y, boolean isShiftDown) {
		this.mouseX = x;
		this.mouseY = y;
	}

	@Override
	public void mouseDrag(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheel(boolean up) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseLeftPressed(int x, int y) {
		this.leftClick = true;
		
	}

	@Override
	public void mouseRightPressed(int x, int y) {
		this.rightClick = true;
	}

	@Override
	public void mouseRightReleased(int x, int y) {
		this.rightClick = false;
	}

	@Override
	public void keyShiftPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyShiftReleased() {
		this.leftClick = false;
	}

	@Override
	public void keyControlPressed() {
		// TODO Auto-generated method stub
		
	}

}
