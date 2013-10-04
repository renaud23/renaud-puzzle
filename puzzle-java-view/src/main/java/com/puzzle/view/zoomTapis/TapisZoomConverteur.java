package com.puzzle.view.zoomTapis;

import com.puzzle.model.Point;
import com.puzzle.view.Offscreen;
import com.puzzle.view.controller.TapisConverter;

public class TapisZoomConverteur implements TapisConverter{

	private Offscreen offscreen;
	
	
	
	
	
	
	
	
	public TapisZoomConverteur(Offscreen offscreen) {
		this.offscreen = offscreen;
	}

	@Override
	public void convertScreenToModel(Point p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void convertModelToScreen(Point p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getScaleX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getScaleY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
