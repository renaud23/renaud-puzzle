package com.puzzle.android.controller;

import java.util.ArrayList;
import java.util.List;

import com.gl2d.core.renderer.MyRenderer;

public class RootController implements IController{
	
	private List<IController> controllers = new ArrayList<IController>();

	@Override
	public void onTouchEvent(float x, float y) {
		IController best = null;
		int z = -1;
		for(IController c : this.controllers){
			if(c.getZIndex() > z && c.isIn(x, y)){
				best = c;
				z = c.getZIndex();
			}
		}
		
		if(best != null) best.onTouchEvent(x, y);
	}

	@Override
	public boolean isIn(float x, float y) {
		return false;
	}

	@Override
	public int getZIndex() {
		return -1;
	}
	
	
	
	public void addController(IController c){
		this.controllers.add(c);
	}
	
	public void removeController(IController c){
		this.controllers.remove(c);
	}

	@Override
	public void removeRenderable(MyRenderer renderer) {
		// TODO Auto-generated method stub
		
	}

}
