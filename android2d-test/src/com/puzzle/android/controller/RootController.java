package com.puzzle.android.controller;

import java.util.ArrayList;
import java.util.List;

import com.gl2d.core.renderer.MyRenderer;

public class RootController implements IController{
	
	private List<IController> controllers = new ArrayList<IController>();


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

	@Override
	public void onTouchDown(float x, float y) {
		IController cdt = this.findCandidat(x,y);
		
		if(cdt!=null) cdt.onTouchDown(x, y);
	}

	@Override
	public void onTouchUp(float x, float y) {
		IController cdt = this.findCandidat(x,y);
		
		if(cdt!=null) cdt.onTouchUp(x, y);
		
	}

	@Override
	public void onTouchMove(float x, float y) {
		IController cdt = this.findCandidat(x,y);
		
		if(cdt!=null) cdt.onTouchMove(x, y);
	}

	
	private IController findCandidat(float x,float y){
		IController best = null;
		int z = -1;
		
		for(IController c : this.controllers){
			if(c.getZIndex() > z && c.isIn(x, y)){
				best = c;
				z = c.getZIndex();
			}
		}
		
		return best;
	}
}
