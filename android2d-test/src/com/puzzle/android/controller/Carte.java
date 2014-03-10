package com.puzzle.android.controller;

import com.gl2d.core.renderer.MyRenderer;
import com.gl2d.core.renderer.RenderableLine;
import com.gl2d.core.renderer.RenderableTexture;
import com.puzzle.android.game.TapisVue;

public class Carte implements IController,Widget{
	
	
	private RenderableTexture texCarte;
	
	/**
	 * position à l'écran
	 */
	private float x;
	private float y;
	/**
	 * largeur à l'écran
	 */
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
		
		
		RenderableLine line = new RenderableLine(100.0f,500.0f,300.0f,500.0f);
		line.SetColor(0.8f, 0.0f, 0f, 0.1f);
		line.setCentreRotation(200.0f, 500.0f);
		line.turn((float) (Math.PI/4.0));
		
		renderer.addRenderable(line);
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



	@Override
	public void onTouchDown(float x, float y) {
		this.checkCarte(x, y);
		
	}



	@Override
	public void onTouchUp(float x, float y) {
		this.checkCarte(x, y);
		
	}



	@Override
	public void onTouchMove(float x, float y) {
		this.checkCarte(x, y);
		
	}
	
	
	private void checkCarte(float x, float y){
		float px = x - this.x;
		px /= this.largeur;
		float py = y - this.y;
		py /= this.hauteur;
		
		this.vue.moveVue(px, py);
	}

}
