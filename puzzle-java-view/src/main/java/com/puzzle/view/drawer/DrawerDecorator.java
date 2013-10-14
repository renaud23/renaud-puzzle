package com.puzzle.view.drawer;

public abstract class DrawerDecorator implements IDrawerSelection{
	protected IDrawerSelection decore;
	
	

	public DrawerDecorator() {
	
	}

	@Override
	public void draw() {
		this.decore.draw();
	}

	@Override
	public void clean() {
		this.decore.clean();
	}
	@Override
	public void createbuffer() {
		this.decore.createbuffer();
		
	}

	@Override
	public void setSelection(boolean selection) {
		this.decore.setSelection(selection);

	}
}
