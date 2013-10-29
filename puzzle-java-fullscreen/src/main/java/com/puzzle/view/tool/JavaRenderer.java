package com.puzzle.view.tool;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferStrategy;

import com.puzzle.model.Tapis;
import com.puzzle.view.core.IDrawer;
import com.puzzle.view.core.Renderer;
import com.puzzle.view.core.TapisConverteur;

public class JavaRenderer implements Renderer{
	
	private IDrawer drawer;
	private Tapis tapis;
	private Image background;
	private TapisConverteur converter;
	private BufferStrategy strategy;
	
	


	public JavaRenderer( Tapis tapis, TapisConverteur converter,
			IDrawer drawer, BufferStrategy strategy,Image background) {
		this.drawer = drawer;
		this.tapis = tapis;
		this.converter = converter;
		this.strategy = strategy;
		this.background = background;
	}

	public IDrawer getDrawer() {
		return drawer;
	}

	public void setDrawer(IDrawer drawer) {
		this.drawer = drawer;
	}

	@Override
	public void Render() {
		this.clean();
		
		
		this.strategy.show();
		
	}
	
	
	
	private void clean(){
		double scalex = tapis.getLargeur() / background.getWidth(null);
		double scaley = tapis.getHauteur() / background.getHeight(null);
		
		double x = -this.tapis.getLargeur() / 2.0;
		x -= converter.getCorner().getX();
		x *= converter.getScaleX();
		double y = -this.tapis.getHauteur() / 2.0;
		y += converter.getCorner().getY();
		y *= converter.getScaleY();
		
		this.drawer.drawImage(this.background, 
				x, y, 
				0, 0, 0, 
				scalex*converter.getScaleX(), scaley*converter.getScaleY(), 1.0f);
	}

	
	
	public void setBackground(Image background) {
		this.background = background;
	}
	
	

}
