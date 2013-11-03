package com.puzzle.zoomTapis.state;

import com.puzzle.model.Point;
import com.puzzle.view.zoomTapis.DrawZoomSelection;
import com.puzzle.view.zoomTapis.TapisZoomConverteur;

public class MainVide implements IState{
	
	
	private TapisZoomControllerEx controller;
	private boolean rightClick;
	private double mouseX;
	private double mouseY;
	
	
	
	
	
	

	public MainVide(TapisZoomControllerEx controller) {
		this.controller = controller;
	}

	@Override
	public void mouseEntered() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseLeftPressed(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseLeftReleased(int x, int y) {
		// TODO Auto-generated method stub
		
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
	public void mouseMove(int x, int y, boolean isShiftDown) {
		this.mouseX = x;
		this.mouseY = y;
		
	}

	@Override
	public void mouseDrag(int x, int y) {
		if(this.rightClick){
			double vx = this.mouseX - x;
			double vy = this.mouseY - y;
			this.mouseX = x;
			this.mouseY = y;
		
			this.controller.getConverter().moveBy(new Point(vx, vy));
			this.controller.getDrawerSelection().clean();
			this.controller.getDrawerTapis().draw();
			this.controller.getDrawerSelection().draw();
//			this.controller.repaint();
		}
		
	}

	@Override
	public void mouseWheel(boolean up) {
		this.controller.getConverter().zoom(up);
		
		((DrawZoomSelection)this.controller.getDrawerSelection()).setZoomScale(this.controller.getConverter().getScaleX());
		
		this.controller.getDrawerSelection().clean();
		this.controller.getDrawerTapis().draw();
		this.controller.getDrawerSelection().draw();
		
	}

	@Override
	public void keyShiftPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyShiftReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyControlPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyControlReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controlPlusS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controlPlusL() {
		// TODO Auto-generated method stub
		
	}

}
