package com.puzzle.view.zoomTapis;

import com.puzzle.model.Point;
import com.puzzle.view.Offscreen;
import com.puzzle.view.controller.TapisConverter;

public class TapisZoomConverteur implements TapisConverter{

	private Offscreen offscreen;
	private double scaleX;
	private double scaleY;
	
	private Point corner;
	private double largeur;
	private double hauteur;
	private double zoom;
	
	
	
	
	
	public TapisZoomConverteur(Offscreen offscreen) {
		this.offscreen = offscreen;
		this.corner = new Point(-1500,1500);
		this.largeur = 3000.0;
		this.hauteur = 3000.0;
		
		this.update();
	}

	@Override
	public void convertScreenToModel(Point p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void convertModelToScreen(Point p) {
		double x = p.getX();
		x -= this.corner.getX();
		x *= this.scaleX;
		
		double y = p.getY();
		y += this.corner.getY() - this.hauteur;
		y *= -1.0;
		y *= this.scaleY;
		
		p.setX(x);
		p.setY(y);
	}

	@Override
	public double getScaleX() {
		return this.scaleX;
	}

	@Override
	public double getScaleY() {
		return this.scaleY;
	}

	@Override
	public void update() {
		this.scaleX = this.offscreen.getLargeur();
		this.scaleX /= this.largeur;
		this.scaleY = this.offscreen.getHauteur();
		this.scaleY /= this.hauteur;
	}

	
	/* *** */
	public Point getCentre() {
		return corner;
	}

	public void setCentre(Point centre) {
		this.corner = centre;
	}

	public double getLargeur() {
		return largeur;
	}

	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}

	public double getHauteur() {
		return hauteur;
	}

	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
	}

	
	
	
}
