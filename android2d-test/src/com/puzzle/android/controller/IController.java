package com.puzzle.android.controller;

import android.graphics.PointF;

public interface IController {
	public void onTouchEvent(float x,float y);
	
	
	
	public boolean isIn(float x,float y);
	public int getZIndex();
}
