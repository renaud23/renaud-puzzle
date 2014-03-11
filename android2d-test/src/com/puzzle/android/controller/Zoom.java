package com.puzzle.android.controller;

import com.gl2d.core.renderer.MyRenderer;
import com.gl2d.core.renderer.RenderableTexture;
import com.puzzle.android.game.TapisVue;



public class Zoom extends RectangularController{
	
	private RenderableTexture texture;
	private RenderableTexture curseur;
	private TapisVue vue;
	
	
	private float relativeY;
	private int currentGrade = 6;
	private float intensity = 0.2f;
	private int nbGrade = 27;
	
	private float gradeHeight;
	
	public Zoom(MyRenderer renderer,TapisVue vue,float x, float y, float largeur,float hauteur){
		super(x,y,largeur,hauteur);
		this.vue = vue;
		this.gradeHeight = hauteur / this.nbGrade;
		
		this.texture = new RenderableTexture(1, x, y, largeur, hauteur);
		this.curseur = new RenderableTexture(1, x, y+ hauteur /2.0f - gradeHeight / 2.0f, largeur, gradeHeight);
		float[] tab = {
						0,0,
						0,0.62f,
						1.0f,0.62f,
						1.0f,0};
		this.texture.setTextCoord(tab);
		
		float[] tab2 = {
				0,0.64f,
				0,0.62f,
				1.0f,0.62f,
				1.0f,0.64f};
		this.curseur.setTextCoord(tab2);
		
		
		
		renderer.addRenderable(this.texture);
		renderer.addRenderable(this.curseur);
	
		texture.setZIndex(1000);
		curseur.setZIndex(1001);
		
		
		
		
		
		
	}

	@Override
	public void onTouchDown(float x, float y) {
		this.relativeY = y - this.y;
		this.check();
	}

	@Override
	public void onTouchUp(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTouchMove(float x, float y) {
		this.relativeY = y - this.y;
		this.check();
	}



	@Override
	public int getZIndex() {
		return 10000;
	}

	@Override
	public void removeRenderable(MyRenderer renderer) {
		// TODO Auto-generated method stub
		
	}
	
	private void check(){
		int candidat = this.currentGrade;
		float distMin = Float.MAX_VALUE;
		for(int i=0;i<this.nbGrade;i++){
			float e = Math.abs(i*this.gradeHeight - this.relativeY);
			if(e < distMin){
				candidat = i + 1;
				distMin = e;
			}
		}// for
		
		int var = candidat - (this.nbGrade / 2) ;
		float scale = this.intensity * Math.abs(var);
		
		if(var < 0) this.vue.scaleDown(scale);
		else if(var > 0) this.vue.scaleUp(scale);
		
		this.currentGrade = candidat;
	}

}
