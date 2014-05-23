package com.puzzle.view2.layer;

import java.awt.Image;

import com.puzzle.view2.DrawOperationAware;
import com.puzzle.view2.draw.IDrawOperation;
import com.puzzle.view2.draw.IDrawable;

public class BackgroundLayer implements IDrawable,DrawOperationAware{
	
	private Image backgroundImage;
	private IDrawOperation drawOperation;
	
	private double largeurScreen;
	private double hauteurScreen;
	private double largeurTapis;
	private double hauteurTapis;
	private double largeurVue;
	private double hauteurVue;
	
	private double ratioLargeur;// rapport image / tapis
	private double ratioHauteur;
	
	private double xVue;// coordonnées sur le tapis de jeu
	private double yVue;
	
	private double scale;
	
	/**
	 * pas de variation du zoom
	 */
	private double zoomVar = 0.01;
		
	

	public BackgroundLayer(Image backgroundImage, double largeurScreen,
			double hauteurScreen, double largeurTapis, double hauteurTapis,
			double scale) {
		this.backgroundImage = backgroundImage;
		this.largeurScreen = largeurScreen;
		this.hauteurScreen = hauteurScreen;
		this.largeurTapis = largeurTapis;
		this.hauteurTapis = hauteurTapis;
		this.scale = scale;
		
		this.ratioLargeur = backgroundImage.getWidth(null) / largeurTapis;
		this.ratioHauteur = backgroundImage.getHeight(null) / hauteurTapis;
		
		this.validate();
	}
	
	
	public void moveTo(double x,double y){
		this.xVue = x;
		this.yVue = y;
		
		this.validate();
	}
	
	public void zoomIn(){
		double a = zoomVar /(scale*scale+scale*zoomVar);
		double vx = this.largeurScreen * a / 2.0;
		double vy = this.hauteurScreen * a / 2.0;
		this.xVue += vx;
		this.yVue -= vy;
		
		this.scale += zoomVar;
		this.validate();
		
	}
	
	public void zoomOut(){
		double a = zoomVar /(scale*scale+scale*zoomVar);
		double vx = this.largeurScreen * a / 2.0;
		double vy = this.hauteurScreen * a / 2.0;
		this.xVue -= vx;
		this.yVue += vy;
		
		this.scale -= zoomVar;
		this.validate();
		
	}

	@Override
	public boolean isChange() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setChange() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		double largeur = this.largeurVue * this.ratioLargeur;
		double hauteur = this.hauteurVue * this.ratioHauteur;
		double x = (this.xVue+this.largeurTapis / 2.0) * ratioLargeur;
		double y = (this.hauteurTapis / 2.0 - this.yVue) * this.ratioHauteur;

		this.drawOperation.drawPart(
				this.backgroundImage, 
				0, 0, (int)this.largeurScreen, (int)this.hauteurScreen, 
				(int)x, (int)y, (int)(x+largeur), (int)(y+hauteur));
	}
	
	
	public void validate(){
		double ll = this.largeurTapis / 2.0;
		double hl = this.hauteurTapis / 2.0;
		
		this.hauteurVue = this.hauteurScreen / scale;
		this.largeurVue = this.largeurScreen / scale;
		
		if(this.xVue  < -ll) this.xVue = -ll;
		else if((this.xVue + this.largeurVue) > ll) this.xVue = ll - this.largeurVue;
		if(this.hauteurVue > this.hauteurTapis){
			scale = hauteurScreen / this.hauteurTapis;
			this.validate();
		}
		
		if(this.yVue > hl) this.yVue = hl;
		else if((this.yVue - this.hauteurVue) < -hl) this.yVue = -hl + this.hauteurVue;
		if(this.largeurVue > this.largeurTapis){
			scale = largeurScreen / this.largeurTapis;
			this.validate();
		}
	}


	
	
	
	public double getLargeurScreen() {
		return largeurScreen;
	}

	public void setLargeurScreen(double largeurScreen) {
		this.largeurScreen = largeurScreen;
	}

	public double getHauteurScreen() {
		return hauteurScreen;
	}

	public void setHauteurScreen(double hauteurScreen) {
		this.hauteurScreen = hauteurScreen;
	}

	public double getLargeurTapis() {
		return largeurTapis;
	}

	public void setLargeurTapis(double largeurTapis) {
		this.largeurTapis = largeurTapis;
	}

	public double getHauteurTapis() {
		return hauteurTapis;
	}

	public void setHauteurTapis(double hauteurTapis) {
		this.hauteurTapis = hauteurTapis;
	}

	public double getLargeurVue() {
		return largeurVue;
	}

	public void setLargeurVue(double largeurVue) {
		this.largeurVue = largeurVue;
	}

	public double getHauteurVue() {
		return hauteurVue;
	}

	public void setHauteurVue(double hauteurVue) {
		this.hauteurVue = hauteurVue;
	}
	
	public double getxVue() {
		return xVue;
	}

	public void setxVue(double xVue) {
		this.xVue = xVue;
	}

	public double getyVue() {
		return yVue;
	}

	public void setyVue(double yVue) {
		this.yVue = yVue;
	}


	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}



	@Override
	public void setDrawOperation(IDrawOperation op) {
		this.drawOperation = op;	
	}
	
	
	
	
	
	
}
