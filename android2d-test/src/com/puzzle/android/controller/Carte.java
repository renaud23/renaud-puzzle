package com.puzzle.android.controller;

import com.gl2d.core.renderer.MyRenderer;
import com.gl2d.core.renderer.RenderableTexture;
import com.puzzle.android.game.TapisVue;

public class Carte implements IController,Widget{
	
	
	private RenderableTexture texCarte;
	
	private float x;
	private float y;
	private float largeur;
	private float hauteur;
	private TapisVue vue;
	
	

	public Carte(MyRenderer renderer,TapisVue vue,float x, float y, float largeur) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = largeur;
		this.vue = vue;
		
		float ratioTapis = (float) vue.getTapis().getHauteur();
		ratioTapis /= vue.getTapis().getLargeur();
		
		this.hauteur *= ratioTapis;
		
		this.texCarte = new RenderableTexture(0, x, y, largeur, this.hauteur);
		
		renderer.addRenderable(this.texCarte);
	}

	@Override
	public void onTouchEvent(float x, float y) {
		System.out.println(x+" "+y);
	}

	@Override
	public boolean isIn(float x, float y) {
		boolean value = false;
		
		if(x > this.x && x < (this.x+largeur) && y > this.y && y < (this.y + this.hauteur))
			value = true;
		
		return value;
	}

	@Override
	public int getZIndex() {
		return 10000;
	}
	
	
	
	public void remove(){
		
	}

	@Override
	public void removeRenderable(MyRenderer renderer) {
		// TODO Auto-generated method stub
		
	}

}
