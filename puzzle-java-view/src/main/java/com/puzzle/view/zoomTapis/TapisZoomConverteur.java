package com.puzzle.view.zoomTapis;

import java.util.Observable;
import java.util.Observer;

import com.puzzle.model.Point;
import com.puzzle.view.Fenetre;
import com.puzzle.view.controller.TapisConverter;

public class TapisZoomConverteur implements TapisConverter,Observer{

	
	private double scale;


	private Point corner;
	private double largeur;
	private double hauteur;
	private double screenLargeur;
	private double screenHauteur;
	private double tapisLargeur;
	private double tapisHauteur;


	
	public TapisZoomConverteur(double tapisLargeur,double tapisHauteur,double screenLargeur,double screenHauteur) {
		this.screenLargeur = screenLargeur;
		this.screenHauteur = screenHauteur;
		this.tapisLargeur = tapisLargeur;
		this.tapisHauteur = tapisHauteur;
	
		

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
		double l = Math.min(nextl, this.tapisLargeur);
		double h = Math.min(nexth, this.tapisHauteur);// limite impos� par le tapis
		
		double nexts = next;
		if(l == this.tapisLargeur){
			nexts = this.screenLargeur / l;

		}else if(h == this.tapisHauteur){
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

		
		if(x < -(this.tapisLargeur / 2.0)) x = -(this.tapisLargeur / 2.0);
		else if((x+this.largeur) > (this.tapisLargeur / 2.0)) x = this.tapisLargeur / 2.0 - this.largeur;
		if(y > (this.tapisHauteur/2.0)) y = this.tapisHauteur/2.0;
		else if((y-this.hauteur) < -(this.tapisHauteur/2.0)) y = this.hauteur-this.tapisHauteur/2.0;
		
		this.corner.setX(x);
		this.corner.setY(y);
		
	}
	
	
	
	
	public void setScale(double scale) {
		this.scale = scale;
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
		double ml = this.tapisLargeur/2.0;
		double mh = this.tapisHauteur/2.0;
		
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

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Fenetre){
			Fenetre f = (Fenetre)o;
			this.screenHauteur = f.getHauteur();
			this.screenLargeur = f.getLargeur();
		
			this.scale = 0.2;
			this.largeur = this.screenLargeur / this.scale;
			this.hauteur = this.screenLargeur / this.scale;
			this.corner = new Point(-this.largeur / 2.0,this.hauteur / 2.0);
		}
	}
	
	
}
