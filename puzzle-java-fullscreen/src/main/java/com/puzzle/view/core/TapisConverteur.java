package com.puzzle.view.core;

import com.puzzle.model.Point;
import com.puzzle.model.Tapis;


public class TapisConverteur {


	private double scale;

	
	private Tapis tapis;
	private Point corner;
	private double largeurVue;
	private double hauteurVue;
	private double largeurScreen;
	private double hauteurScreen;

	
	
	


	public TapisConverteur(Tapis tapis,double largeurScreen,double hauteurScreen) {
		this.largeurScreen = largeurScreen;
		this.hauteurScreen = hauteurScreen;
		this.tapis = tapis;
		

		// TODO 
		this.scale = 0.2;
		this.largeurVue = largeurScreen / this.scale;
		this.hauteurVue = this.hauteurScreen / this.scale;
		this.corner = new Point(-this.largeurVue / 2.0,this.hauteurVue / 2.0);
	
		
		this.update();
	}

	
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
		
		double nextl = this.largeurScreen / next;
		double nexth = this.hauteurScreen / next;// taille obtenue avec cette var
		double l = Math.min(nextl, this.tapis.getLargeur());
		double h = Math.min(nexth, this.tapis.getHauteur());// limite imposée par le tapis
		
		double nexts = next;
		if(l == this.tapis.getLargeur()){
			nexts = this.largeurScreen / l;

		}else if(h == this.tapis.getHauteur()){
			nexts = this.hauteurScreen / h;
		}
		this.scale = nexts;// scale effectif
		
		double vx = (this.largeurVue - this.largeurScreen / this.scale) / 2.0;
		double vy = (this.hauteurVue - this.hauteurScreen / this.scale) / 2.0;// variations a r�partir
		this.largeurVue = this.largeurScreen / this.scale;
		this.hauteurVue = this.hauteurScreen / this.scale;// largeur finale
//		
		double x = this.corner.getX();
		double y = this.corner.getY();
//		
		x = this.corner.getX() + vx ;
		y = this.corner.getY() - vy ;

		
		if(x < -(this.tapis.getLargeur() / 2.0)) x = -(this.tapis.getLargeur() / 2.0);
		else if((x+this.largeurVue) > (this.tapis.getLargeur() / 2.0)) x = this.tapis.getLargeur() / 2.0 - this.largeurVue;
		if(y > (this.tapis.getHauteur()/2.0)) y = this.tapis.getHauteur()/2.0;
		else if((y-this.hauteurVue) < -(this.tapis.getHauteur()/2.0)) y = this.hauteurVue-this.tapis.getHauteur()/2.0;
		
		this.corner.setX(x);
		this.corner.setY(y);
		
	}
	
	
	
	public void convertModelToScreen(Point p) {
		double x = p.getX()  - corner.getX();
		x *= this.scale;
		double y = corner.getY() - p.getY();
		y *= this.scale;
		
		p.setX(x);
		p.setY(y);
	}

	
	public double getScaleX() {
		return this.scale;
	}


	public double getScaleY() {
		return this.scale;
	}


	public void update() {
		this.largeurVue = this.largeurScreen / this.scale;
		this.hauteurVue = this.hauteurScreen / this.scale;
	}

	
	public boolean moveBy(Point p){
		boolean state =true;
		double ml = this.tapis.getLargeur()/2.0;
		double mh = this.tapis.getHauteur()/2.0;
		
		double x = this.corner.getX() + p.getX() / scale;
		double y = this.corner.getY() - p.getY() / scale;
		
		if(x < -ml){
			x=-ml;
		} else if((x+this.largeurVue)>ml){
			x = ml - this.largeurVue;
		}
		if(y>mh){
			y=mh;
		}else if((y-this.hauteurVue)<-mh){
			y = -mh + this.hauteurVue;
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
		return largeurVue;
	}

	public void setLargeur(double largeur) {
		this.largeurVue = largeur;
	}

	public double getHauteur() {
		return hauteurVue;
	}

	public void setHauteur(double hauteur) {
		this.hauteurVue = hauteur;
	}

	
	
	public Point getCorner() {
		return corner;
	}
	
	
}
