package com.puzzle.android.game;

import android.graphics.RectF;
import com.puzzle.android.Background;
import com.puzzle.model.Tapis;


/**
 * Zone du tapis visible � l'�cran.
 * @author jean-fran�ois
 *
 */
public class TapisVue {
	
	private float largeurVue;
	private float hauteurVue;
	/**
	 * position dans le tapis
	 */
	private float x;
	/**
	 * position dans le tapis
	 */
	private float y;
	
	private Background background;
	
	private Tapis tapis;


	public TapisVue(Background background, Tapis tapis,float x, float y,float largeurVue, float hauteurVue) {
		this.largeurVue = largeurVue;
		this.hauteurVue = hauteurVue;
		this.x =  x;
		this.y = y;
		this.tapis = tapis;
		
		this.background = background;
		this.checkVue();
	}


	
	public void moveVue(float px,float py){
		this.x = (float) (this.tapis.getLargeur() * px - tapis.getLargeur() / 2.0f);
		this.y = (float) (this.tapis.getHauteur() * py - tapis.getHauteur() / 2.0f);
	
		this.checkVue();
	}
	
	public void checkVue(){
		float rl = (float) (this.largeurVue / this.tapis.getLargeur());
		float rh = (float) (this.hauteurVue / this.tapis.getHauteur());
		
		float rx = (float) ((this.x + tapis.getLargeur() / 2.0f) / this.tapis.getLargeur()) - rl / 2.0f;
		float ry = (float) ((this.y + tapis.getHauteur() / 2.0f) / this.tapis.getHauteur()) - rh / 2.0f;
		
		RectF r = new RectF(rx, ry+rh, rx+rl, ry);
		
		this.background.setRect(r);
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
