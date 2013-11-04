package com.puzzle.view.hud;

import java.awt.Point;

public class FreeBox implements HudShape{
	
	
	private Point[] points;
	
	
	

	public FreeBox(Point[] points) {
		this.points = points;
	}

	public Point[] getPoints() {
		return points;
	}

	@Override
	public boolean isIn(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

}
