package com.puzzle.android;

import android.graphics.RectF;

import com.gl2d.core.renderer.MyRenderer;
import com.gl2d.core.renderer.RenderableTexture;

public class Background {
	private RenderableTexture renderable;
	
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
		this.renderable = new RenderableTexture(0, 0, 0, screenLargeur, screenHauteur);
		this.renderable.setZIndex(0);
		
		
		this.rect = new RectF();
		
		renderer.addRenderable(this.renderable);
	}
	
	
	public void setRect(RectF pl){
		float[] coord = {// attention, l'image est à l'envers dans la texture
			pl.left,1.0f-pl.top,
			pl.left,1.0f-pl.bottom,
			pl.right,1.0f-pl.bottom,
			pl.right,1.0f-pl.top
		};
		
		this.renderable.setTextCoord(coord);
	}
}
