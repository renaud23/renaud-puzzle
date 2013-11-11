package com.puzzle.view.hud;

import com.puzzle.view.controller.IController;
import com.puzzle.view.tool.JImageBuffer;

public abstract class HudArea implements IController{

	protected HudShape shape;
	protected HudTask task;
	protected JImageBuffer buffer;
	protected int index;
	
	public HudArea() {
		
	}
	
	

	public HudArea(HudShape shape, HudTask task) {
		this.shape = shape;
		this.task = task;
	}

	public HudArea(HudShape shape, HudTask task, JImageBuffer buffer) {
		this.shape = shape;
		this.task = task;
		this.buffer = buffer;
	}

	public boolean isIn(int x,int y){
		return this.shape.isIn(x, y);
	}
	
	
	public void setShape(HudShape shape) {
		this.shape = shape;
	}

	public void setTask(HudTask task) {
		this.task = task;
	}

	@Override
	public void mouseEntered() {
		
	}

	@Override
	public void mouseExited() {
		
	}

	@Override
	public void mouseLeftPressed(int x, int y) {
		this.task.execute();
	}

	@Override
	public void mouseLeftReleased(int x, int y) {
		
	}

	@Override
	public void mouseRightPressed(int x, int y) {
		
	}

	@Override
	public void mouseRightReleased(int x, int y) {
		
	}

	@Override
	public void mouseMove(int x, int y, boolean isShiftDown) {
		
	}

	@Override
	public void mouseDrag(int x, int y) {
		
	}

	@Override
	public void mouseWheel(boolean up) {
		
	}

	@Override
	public void keyShiftPressed() {
		
	}

	@Override
	public void keyShiftReleased() {
		
	}

	@Override
	public void keyControlPressed() {
		
	}

	@Override
	public void keyControlReleased() {
		
	}

	@Override
	public void controlPlusS() {
		
	}

	@Override
	public void controlPlusL() {
		
	}

	

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public HudShape getShape() {
		return shape;
	}

}
