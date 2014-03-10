package com.puzzle.android.controller;

import com.gl2d.core.renderer.MyRenderer;
import com.gl2d.core.renderer.RenderableRect;
import com.gl2d.core.renderer.RenderableTexture;
import com.puzzle.android.game.TapisVue;

public class Zoom implements IController,Widget{
	
	private RenderableTexture texture;
	
	public Zoom(MyRenderer renderer,TapisVue vue,float x, float y, float largeur,float hauteur){
		
		
		this.texture = new RenderableTexture(0, x, y, largeur, hauteur);
		renderer.addRenderable(this.texture);
	}

	@Override
	public void onTouchDown(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTouchUp(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTouchMove(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isIn(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getZIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeRenderable(MyRenderer renderer) {
		// TODO Auto-generated method stub
		
	}

}
