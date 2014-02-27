package com.puzzle.android;

import android.graphics.RectF;

import com.gl2d.core.renderer.MyRenderer;
import com.gl2d.core.renderer.RenderableElement;

public class Background {
	private RenderableElement renderable;
	
	private float screenLargeur;
	private float screenHauteur;
	private float texLargeur;
	private float texHauteur;
	
	private RectF rect;
	
	public Background(MyRenderer renderer,float screenLargeur,float screenHauteur){
		this.screenLargeur = screenLargeur;
		this.screenHauteur = screenHauteur;
		this.texLargeur = 1000.0f;
		this.texHauteur = 1000.0f;
		this.renderable = new RenderableElement(0, 0, 0, screenLargeur, screenHauteur);
		this.renderable.setZ(0);
		
		
		this.rect = new RectF();
		
		renderer.addRenderable(this.renderable);
	}
	
	
	public void setRect(RectF pl){
		float[] coord = {
			pl.left,pl.bottom,
			pl.left,pl.top,
			pl.right,pl.top,
			pl.right,pl.bottom
		};
		
		this.renderable.setTextCoord(coord);
	}
}
