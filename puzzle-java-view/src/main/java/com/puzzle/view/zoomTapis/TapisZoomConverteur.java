package com.puzzle.view.zoomTapis;

import com.puzzle.model.Point;
import com.puzzle.model.Tapis;
import com.puzzle.view.Offscreen;
import com.puzzle.view.controller.TapisConverter;

public class TapisZoomConverteur implements TapisConverter{

	private Offscreen offscreen;
	private double scale;

	
	private Tapis tapis;
	private Point corner;
	private double largeur;
	private double hauteur;
	private double rapportAngle;
	private double zoom;
	
	
	
	
	
	public TapisZoomConverteur(Offscreen offscreen,Tapis tapis) {
		this.offscreen = offscreen;
		this.tapis = tapis;
		this.rapportAngle = this.tapis.getLargeur() / this.tapis.getHauteur();

		// TODO 
		this.scale = 0.5;
		this.largeur = this.offscreen.getLargeur() / this.scale;
		this.hauteur = this.offscreen.getHauteur() / this.scale;
		this.corner = new Point(-this.largeur / 2.0,this.hauteur / 2.0);
	
		
		
		this.update();
	}

	@Override
	public void convertScreenToModel(Point p) {
		double x = p.getX() / this.scale + this.corner.getX();
		double y = this.corner.getY()  - p.getY() / this.scale;
		
		p.setX(x);
		p.setY(y);
	
	}

	
	
	
	
	
	@Override
	public void convertModelToScreen(Point p) {
		double x = p.getX()  - corner.getX();
		x *= this.scale;
		double y = corner.getY() - p.getY();
		y *= this.scale;
		
		p.setX(x);
		p.setY(y);
	}

	@Override
	public double getScaleX() {
		return this.scale;
	}

	@Override
	public double getScaleY() {
		return this.scale;
	}

	@Override
	public void update() {
		

	}

	
	public void move(Point position){
		
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
