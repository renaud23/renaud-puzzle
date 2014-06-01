package com.puzzle.view2.layer;

import java.awt.Image;

import com.puzzle.view2.DrawOperationAware;
import com.puzzle.view2.image.IDrawOperation;
import com.puzzle.view2.image.IDrawable;

public class BackgroundLayer implements IDrawable,DrawOperationAware{
	
	private Image backgroundImage;
	private IDrawOperation drawOperation;
	
	private double largeurScreen;
	private double hauteurScreen;
	private double largeurTapis;
	private double hauteurTapis;

	private double ratioLargeur;// rapport image / tapis
	private double ratioHauteur;
	
	private Vue vue;
	
	private double scale;
	
	/**
	 * pas de variation du zoom
	 */
	private double zoomVar = 0.1;
	
		
	

	public BackgroundLayer(Vue vue,Image backgroundImage, double largeurScreen,
			double hauteurScreen, double largeurTapis, double hauteurTapis,
			double scale) {
		this.backgroundImage = backgroundImage;
		this.largeurScreen = largeurScreen;
		this.hauteurScreen = hauteurScreen;
		this.largeurTapis = largeurTapis;
		this.hauteurTapis = hauteurTapis;
		this.scale = scale;
		this.vue = vue;
		
		this.ratioLargeur = backgroundImage.getWidth(null) / largeurTapis;
		this.ratioHauteur = backgroundImage.getHeight(null) / hauteurTapis;
		
		this.validate();
	}
	
	
	
	
	public void moveTo(double x,double y){
		this.vue.setX(x);
		this.vue.setY(y);
		
		this.validate();
	}
	
	public void zoomIn(){
			double v = 1.0+zoomVar;
			
			double f = (v-1)/v/scale;
			double vx = this.largeurScreen * f / 2.0;
			double vy = this.hauteurScreen * f / 2.0;
			
			this.vue.addX(vx);
			this.vue.addY(-vy);
			
			this.scale *= v;
			this.validate();
		
		
	}
	
	public void zoomOut(){
			double v = 1.0-zoomVar;
			
			double f = (v-1)/v/scale;
			double vx = this.largeurScreen * f / 2.0;
			double vy = this.hauteurScreen * f / 2.0;
		
			this.scale *= v;
			
			double nh = this.hauteurScreen / scale;
			double nl = this.largeurScreen / scale;
			
			if( nh < hauteurTapis && nl < largeurTapis){
				this.vue.addX(vx);
				this.vue.addY(-vy);
			}
			
			this.validate();
		
	}

	
	
	
	private void validate(){
		double ll = this.largeurTapis / 2.0;
		double hl = this.hauteurTapis / 2.0;
		
		this.vue.setHauteur(this.hauteurScreen / scale);
		this.vue.setLargeur(this.largeurScreen / scale);
		
		if(this.vue.getX()  < -ll) this.vue.setX(-ll);
		else if((this.vue.getX() + this.vue.getLargeur()) > ll) this.vue.setX(ll - this.vue.getLargeur());
		if(this.vue.getHauteur() > this.hauteurTapis){
			scale = hauteurScreen / this.hauteurTapis;
			this.validate();
		}
		
		if(this.vue.getY() > hl) this.vue.setY(hl); 
		else if((this.vue.getY() - this.vue.getHauteur()) < -hl) this.vue.setY(-hl + this.vue.getHauteur());
		if(this.vue.getLargeur() > this.largeurTapis){
			scale = largeurScreen / this.largeurTapis;
			this.validate();
		}
	}


	
	
	
	public double getLargeurScreen() {
		return largeurScreen;
	}



	public double getHauteurScreen() {
		return hauteurScreen;
	}



	public double getLargeurTapis() {
		return largeurTapis;
	}



	public double getHauteurTapis() {
		return hauteurTapis;
	}

	public void setHauteurTapis(double hauteurTapis) {
		this.hauteurTapis = hauteurTapis;
	}


	public Vue getVue() {
		return vue;
	}




	public void setVue(Vue vue) {
		this.vue = vue;
	}




	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	
	/*
	 * draw
	 */


	@Override
	public void setDrawOperation(IDrawOperation op) {
		this.drawOperation = op;	
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
	public void draw(Vue vue) {
		double scale =  this.largeurScreen / vue.getLargeur();
	
		
		double x = this.largeurTapis / 2.0 + vue.getX();
		x *= scale;
		double y = this.hauteurTapis / 2.0 - vue.getY();
		y *= scale;
		
		
		
		

		this.drawOperation.drawImage(this.backgroundImage,
				-x, -y, 
				0, 0, 0, 
				(largeurTapis/backgroundImage.getWidth(null))*scale, 1.0f);
	}
	
	
	
	
	
}
