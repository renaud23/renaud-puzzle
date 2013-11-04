package com.puzzle.view.hud;

import com.puzzle.view.controller.IController;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.tool.JImageBuffer;





public abstract class HudArea implements IController,IDrawer{

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseLeftPressed(int x, int y) {
		this.task.execute();
	}

	@Override
	public void mouseLeftReleased(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseRightPressed(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseRightReleased(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMove(int x, int y, boolean isShiftDown) {
		// TODO Auto-generated method stub
		
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

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
