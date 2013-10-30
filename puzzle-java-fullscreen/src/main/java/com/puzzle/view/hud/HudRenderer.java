package com.puzzle.view.hud;

import java.awt.Color;



import java.util.ArrayList;
import java.util.List;

import com.puzzle.model.Point;
import com.puzzle.model.Tapis;
import com.puzzle.view.core.IDrawer;
import com.puzzle.view.core.Lunette;
import com.puzzle.view.core.Renderer;
import com.puzzle.view.core.TapisConverteur;

public class HudRenderer implements Renderer{
	
	private IDrawer drawer;
	private TapisConverteur converter;
	
	private List<Renderer> renderers = new ArrayList<Renderer>();
	

	
	public HudRenderer(Tapis tapis,IDrawer drawer, TapisConverteur converter) {
		this.drawer = drawer;
		this.converter = converter;
		
		this.lunette = new Lunette();
		this.lunette.setTapis(tapis);
		this.lunette.setScale(0.2);
		this.lunette.setLargeur(this.drawer.getLargeur() * this.lunette.getScale());
		this.lunette.setHauteur(this.lunette.getLargeur() * tapis.getHauteur() / tapis.getLargeur());
		this.lunette.setX(10.0);
		this.lunette.setY(10.0);
	}




	private Point corner = new Point();
	private double scaleConverter;
	private double largeurConverter;
	private double hauteurConverter;
	
	private Lunette lunette;

	@Override
	public void Render() {
		this.corner.setX(this.converter.getCorner().getX());
		this.corner.setY(this.converter.getCorner().getY());
		this.scaleConverter = this.converter.getScaleX();
		this.largeurConverter = this.converter.getLargeur();
		this.hauteurConverter = this.converter.getHauteur();
		
		this.drawLunette();
		
		for(Renderer r : this.renderers) r.Render();
		
	}

	
	
	
	private void drawLunette(){
		this.drawer.drawRect(Color.black, 
				(int)Math.round(this.lunette.getX()), (int)Math.round(this.lunette.getY()), 
				(int)Math.round(this.lunette.getLargeur()), (int)Math.round(this.lunette.getHauteur()),1.0f);
	
		double sx = this.lunette.getLargeur() / this.lunette.getTapis().getLargeur();
		
		double xi = this.corner.getX() * sx;
		xi += this.lunette.getTapis().getLargeur() * sx / 2.0;
		xi += this.lunette.getX();
		double yi = -this.corner.getY() * sx;
		yi += this.lunette.getTapis().getHauteur() * sx / 2.0;
		yi += this.lunette.getY();
		double l = this.largeurConverter * sx;
		double h = this.hauteurConverter * sx;
		
		this.drawer.fillRect(Color.yellow, 
				(int)Math.round(xi), (int)Math.round(yi), 
				(int)Math.round(l), (int)Math.round(h),0.2f);
		
		this.drawer.drawRect(Color.black, 
				(int)Math.round(xi), (int)Math.round(yi), 
				(int)Math.round(l), (int)Math.round(h),1.0f);
	}
	
	
	
	
	public void addRenderer(Renderer r){
		this.renderers.add(r);
	}
	
	public void removeRenderer(Renderer r){
		this.removeRenderer(r);
	}
}
