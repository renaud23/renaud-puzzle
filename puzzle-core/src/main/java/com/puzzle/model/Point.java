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
