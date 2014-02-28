package com.puzzle.android.controller;

import com.gl2d.core.renderer.MyRenderer;


public interface IController {

	
	public void onTouchDown(float x,float y);
	public void onTouchUp(float x,float y);
	public void onTouchMove(float x,float y);
	
	
	
	public boolean isIn(float x,float y);
	public int getZIndex();
	public void removeRenderable(MyRenderer renderer);
}
