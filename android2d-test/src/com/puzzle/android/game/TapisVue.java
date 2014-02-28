package com.puzzle.android.game;

import com.puzzle.model.Tapis;


/**
 * Zone du tapis visible à l'écran.
 * @author jean-françois
 *
 */
public class TapisVue {
	private float largeurVue;
	private float hauteurVue;
	private float x;
	private float y;
	
	
	private Tapis tapis;


	public TapisVue(Tapis tapis,float x, float y,float largeurVue, float hauteurVue) {
		this.largeurVue = largeurVue;
		this.hauteurVue = hauteurVue;
		this.x = x;
		this.y = y;
		this.tapis = tapis;
		
//		System.out.println(x+" "+y+" "+largeurVue+" "+hauteurVue);
		
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
