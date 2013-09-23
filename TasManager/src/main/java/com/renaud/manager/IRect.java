package com.renaud.manager;

public interface IRect {
	public boolean isIn(double x,double y,double largeur,double hauteur);
	public boolean isIn(IRect r);
	public boolean contains(double x,double y);
	
	public double getX();
	public double getY();
	public double getLargeur();
	public double getHauteur();
}
