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
		return this.contains(x, y);
	}
	
	
	
	public boolean contains(int x, int y) {
		boolean state = false;
		Point p = new Point(x,y);
		
		if(this.IsInsideTriangle(p,this.points[0],this.points[1],this.points[3])){
			state = true;
		}else if(this.IsInsideTriangle(p,this.points[1],this.points[2],this.points[3])){
			state = true;
		}
		
		return state;
	}
	
	public boolean IsInsideTriangle(Point ref,Point a, Point b, Point c) {
	    double z1 = ComputeZCoordinate(a, b, ref);
	    double z2 = ComputeZCoordinate(b, c, ref);
	    double z3 = ComputeZCoordinate(c, a, ref);
	    
	    return (z1 > 0 && z2 > 0 && z3 > 0) || (z1 < 0 && z2 < 0 && z3 < 0);
	 }
	
	private  double ComputeZCoordinate(Point p1, Point p2, Point p3) {
	    return p1.x * (p2.y - p3.y) + p2.x * (p3.y - p1.y) + p3.x * (p1.y - p2.y);
	}

}
