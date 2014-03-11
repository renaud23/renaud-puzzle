package com.puzzle.android.controller;

import com.gl2d.core.renderer.MyRenderer;
import com.gl2d.core.renderer.RenderableRect;
import com.gl2d.core.renderer.RenderableTexture;
import com.puzzle.android.game.TapisVue;

public class Zoom extends RectangularController{
	
	private RenderableTexture texture;
	
	private float relativeY;
	private int currentGrade = 6;
	private float intensity = 0.05f;
	private int nbGrade;
	private float gradeHeight;
	
	public Zoom(MyRenderer renderer,TapisVue vue,float x, float y, float largeur,float hauteur){
		super(x,y,largeur,hauteur);
		
		this.texture = new RenderableTexture(0, x, y, largeur, hauteur);
		renderer.addRenderable(this.texture);
		
		this.nbGrade = 11;
		this.gradeHeight = hauteur / this.nbGrade;
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
		
		this.currentGrade = candidat;
	}

}
