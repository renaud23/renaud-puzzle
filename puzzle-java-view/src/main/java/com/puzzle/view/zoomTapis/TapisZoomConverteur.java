package com.puzzle.view.zoomTapis;

import com.puzzle.model.Point;
import com.puzzle.model.Tapis;
import com.puzzle.view.controller.TapisConverter;

public class TapisZoomConverteur implements TapisConverter{

	
	private double scale;

	
	private Tapis tapis;
	private Point corner;
	private double largeur;
	private double hauteur;
	private double screenLargeur;
	private double screenHauteur;


	
	
	


	public TapisZoomConverteur(Tapis tapis,double screenLargeur,double screenHauteur) {
		this.screenLargeur = screenLargeur;
		this.screenHauteur = screenHauteur;

		this.tapis = tapis;
		

		// TODO 
		this.scale = 0.2;
		this.largeur = this.screenLargeur / this.scale;
		this.hauteur = this.screenLargeur / this.scale;
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

	
	
	public void zoom(boolean up){
		double next = this.scale;
		if(up) next *= 1.1;
		else next *= 0.9;// variation initiale pr�vues
		
		double nextl = this.screenLargeur / next;
		double nexth = this.screenHauteur / next;// taille obtenue avec cette var
		double l = Math.min(nextl, this.tapis.getLargeur());
		double h = Math.min(nexth, this.tapis.getHauteur());// limite impos� par le tapis
		
		double nexts = next;
		if(l == this.tapis.getLargeur()){
			nexts = this.screenLargeur / l;

		}else if(h == this.tapis.getHauteur()){
			nexts = this.screenHauteur / h;
		}
		this.scale = nexts;// scale effectif
		
		double vx = (this.largeur - this.screenLargeur / this.scale) / 2.0;
		double vy = (this.hauteur - this.screenHauteur / this.scale) / 2.0;// variations a r�partir
		this.largeur = this.screenLargeur / this.scale;
		this.hauteur = this.screenHauteur / this.scale;// largeur finale
//		
		double x = this.corner.getX();
		double y = this.corner.getY();
//		
		x = this.corner.getX() + vx ;
		y = this.corner.getY() - vy ;

		
		if(x < -(this.tapis.getLargeur() / 2.0)) x = -(this.tapis.getLargeur() / 2.0);
		else if((x+this.largeur) > (this.tapis.getLargeur() / 2.0)) x = this.tapis.getLargeur() / 2.0 - this.largeur;
		if(y > (this.tapis.getHauteur()/2.0)) y = this.tapis.getHauteur()/2.0;
		else if((y-this.hauteur) < -(this.tapis.getHauteur()/2.0)) y = this.hauteur-this.tapis.getHauteur()/2.0;
		
		this.corner.setX(x);
		this.corner.setY(y);
		
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
		this.largeur = this.screenLargeur / this.scale;
		this.hauteur = this.screenHauteur / this.scale;
	}

	
	public boolean moveBy(Point p){
		boolean state =true;
		double ml = this.tapis.getLargeur()/2.0;
		double mh = this.tapis.getHauteur()/2.0;
		
		double x = this.corner.getX() + p.getX() / scale;
		double y = this.corner.getY() - p.getY() / scale;
		
		if(x < -ml){
			x=-ml;
		} else if((x+this.largeur)>ml){
			x = ml - this.largeur;
		}
		if(y>mh){
			y=mh;
		}else if((y-this.hauteur)<-mh){
			y = -mh + this.hauteur;
		}
		
		this.corner.setX(x);
		this.corner.setY(y);
		
		return state;
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

	
	
	public Point getCorner() {
		return corner;
	}
	
	
}
