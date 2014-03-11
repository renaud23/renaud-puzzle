package com.puzzle.android.game;

import android.graphics.RectF;
import com.puzzle.android.Background;
import com.puzzle.model.Tapis;


/**
 * Zone du tapis visible à l'écran.
 * @author jean-françois
 *
 */
public class TapisVue {
	
	/**
	 * 
	 */
	private float largeurVue;
	
	/**
	 * 
	 */
	private float hauteurVue;
	/**
	 * position en abscice dans le tapis
	 */
	

	
	private float x;
	/**
	 * position en ordonnée dans le tapis
	 */
	private float y;
	
	private float hauteurInitiale;
	
	private float largeurInitiale;
	
	private Background background;
	
	private Tapis tapis;


	public TapisVue(Background background, Tapis tapis,float x, float y,float largeurVue, float hauteurVue) {
		this.largeurVue = largeurVue;
		this.hauteurVue = hauteurVue;
		this.largeurInitiale = largeurVue;
		this.hauteurInitiale = hauteurVue;
		this.x = x;
		this.y = y;
		this.tapis = tapis;
		
		this.background = background;
		this.checkVue();
	}


	/*
	 * attention dans les calcul, le tapis est centré en 0,0 pas le reste !
	 */
	public void moveVue(float px,float py){
		this.x = (float) (this.tapis.getLargeur() * px - tapis.getLargeur() / 2.0f);
		this.y = (float) (this.tapis.getHauteur() * py - tapis.getHauteur() / 2.0f);
	
		this.checkVue();
	}
	
	private void checkVue(){
		// calcul de la position de la texture
		float rl = (float) (this.largeurVue / this.tapis.getLargeur());
		float rh = (float) (this.hauteurVue / this.tapis.getHauteur());
		
		float rx = (float) ((this.x + tapis.getLargeur() / 2.0f) / this.tapis.getLargeur()) - rl / 2.0f;
		float ry = (float) ((this.y + tapis.getHauteur() / 2.0f) / this.tapis.getHauteur()) - rh / 2.0f;
		
		// control des bords
		if(rx < 0)	rx = 0;
		else if((rx+rl) > 1.0f) rx = 1.0f - rl;
		if(ry < 0)	ry = 0;
		else if((ry+rh) > 1.0f) ry = 1.0f - rh;
		
		RectF r = new RectF(rx, ry+rh, rx+rl, ry);
		
		this.background.setRect(r);
	}
	
	private void validate(){
		
	}
	
	
	public void scaleUp(float scale){//System.out.println("up "+scale);
		float var = 1.0f + scale;
		float nh = this.hauteurInitiale * var;
		float nl = this.largeurInitiale * var;
		
		
	
		
		if(nh > this.tapis.getHauteur()){
			nh = (float) this.tapis.getHauteur();
			this.y = 0;
			
		}


		
		
		this.hauteurVue = nh;
		this.largeurVue = nl;

		
		this.checkVue();
	}
	
	
	public void scaleDown(float scale){//System.out.println("down "+scale);
		float var = 1.0f - scale;
		float nh = this.hauteurInitiale * var;
		float nl = this.largeurInitiale * var;
		
		
		
		
		this.hauteurVue = nh;
		this.largeurVue = nl;
		
		this.checkVue();
	}
	
	
	
	
	
	
	
	
	
	
	public float getLargeurVue() {
		return largeurVue;
	}


	public float getHauteurVue() {
		return hauteurVue;
	}


	public float getX() {
		return x;
	}


	public float getY() {
		return y;
	}


	public Tapis getTapis() {
		return tapis;
	}
}
