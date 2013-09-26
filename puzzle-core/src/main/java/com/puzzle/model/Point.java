package com.puzzle.model;



public class Point {
	private double x;
	private double y;
	
	public Point(){
		
	}

	
	
	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public void translate(double xi,double yi){
		this.x += xi;
		this.y += yi;
	}
	
	public void tourner(double angle){	
		double xi = this.x * Math.cos(angle) - this.y * Math.sin(angle);
		double yi = this.x * Math.sin(angle) + this.y * Math.cos(angle);
		
		this.x = xi;
		this.y = yi;
	}
	
	public void tourner(double angle,double x0,double y0){
		double ex= this.x - x0;
		double ey= this.y - y0;
		double xi = x0 + ex * Math.cos(angle) - ey * Math.sin(angle);
		double yi = y0 + ex * Math.sin(angle) + ey * Math.cos(angle);
		
		this.x = xi;
		this.y = yi;
	}
	
	
	private static double ComputeZCoordinate(Point p1, Point p2, Point p3) {
	    return p1.x * (p2.y - p3.y) + p2.x * (p3.y - p1.y) + p3.x * (p1.y - p2.y);
	}
	
	
	 public boolean IsInsideTriangle(Point a, Point b, Point c) {
	    double z1 = ComputeZCoordinate(a, b, this);
	    double z2 = ComputeZCoordinate(b, c, this);
	    double z3 = ComputeZCoordinate(c, a, this);
	    
	    return (z1 > 0 && z2 > 0 && z3 > 0) || (z1 < 0 && z2 < 0 && z3 < 0);
	  }
	
	public void tourner(double angle,Point c){
		this.tourner(angle, c.x, c.y);
	}
	

	public String toString(){
		return "[Point x="+this.x+" y="+this.y+"]";
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	
}
