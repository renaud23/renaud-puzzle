package com.puzzle.view.tool;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferStrategy;

import com.puzzle.model.Tapis;
import com.puzzle.view.core.IDrawer;
import com.puzzle.view.core.Lunette;
import com.puzzle.view.core.Renderer;
import com.puzzle.view.core.TapisConverteur;

public class JavaRenderer implements Renderer{
	
	private IDrawer drawer;
	private Tapis tapis;
	private Image background;
	private TapisConverteur converter;
	private BufferStrategy strategy;
	
	private Lunette lunette;
	


	public JavaRenderer( Tapis tapis, TapisConverteur converter,
			IDrawer drawer, BufferStrategy strategy,Image background) {
		this.drawer = drawer;
		this.tapis = tapis;
		this.converter = converter;
		this.strategy = strategy;
		this.background = background;
		
		
		this.lunette = new Lunette();
		this.lunette.setTapis(tapis);
		this.lunette.setScale(0.2);
		this.lunette.setLargeur(this.drawer.getLargeur() * this.lunette.getScale());
		this.lunette.setHauteur(this.lunette.getLargeur() * tapis.getHauteur() / tapis.getLargeur());
		this.lunette.setX(this.drawer.getLargeur() - this.lunette.getLargeur() - 10.0);
		this.lunette.setY(10.0);
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
		
		// TODO
		
		this.drawLunette();
		
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

	
	private void drawLunette(){
		this.drawer.drawRect(Color.black, 
				(int)Math.round(this.lunette.getX()), (int)Math.round(this.lunette.getY()), 
				(int)Math.round(this.lunette.getLargeur()), (int)Math.round(this.lunette.getHauteur()),1.0f);
	
		double sx = this.lunette.getLargeur() / this.lunette.getTapis().getLargeur();
		
		double xi = this.converter.getCorner().getX() * sx;
		xi += this.lunette.getTapis().getLargeur() * sx / 2.0;
		xi += this.lunette.getX();
		double yi = -this.converter.getCorner().getY() * sx;
		yi += this.lunette.getTapis().getHauteur() * sx / 2.0;
		yi += this.lunette.getY();
		double l = this.converter.getLargeur() * sx;
		double h = this.converter.getHauteur() * sx;
		
		this.drawer.fillRect(Color.yellow, 
				(int)Math.round(xi), (int)Math.round(yi), 
				(int)Math.round(l), (int)Math.round(h),0.2f);
		
		this.drawer.drawRect(Color.black, 
				(int)Math.round(xi), (int)Math.round(yi), 
				(int)Math.round(l), (int)Math.round(h),1.0f);
	}
	
	
	
	public void setBackground(Image background) {
		this.background = background;
	}
	
	

}
