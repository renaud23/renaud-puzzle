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
