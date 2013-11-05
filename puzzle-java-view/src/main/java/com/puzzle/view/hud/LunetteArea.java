package com.puzzle.view.hud;

import java.awt.Color;

import com.puzzle.model.Tapis;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.tool.JImageBuffer;
import com.puzzle.view.zoomTapis.Lunette;
import com.puzzle.view.zoomTapis.TapisZoomConverteur;
import com.puzzle.zoomTapis.state.TapisZoomControllerEx;

public class LunetteArea extends HudArea implements IDrawer{
	
	private TapisZoomConverteur converter;
	private JImageBuffer buffer;
	private Lunette lunette;
	private Tapis tapis;
	private TapisZoomControllerEx controller;
	
	public LunetteArea(TapisZoomControllerEx controller){
		this.controller = controller;
		this.buffer = controller.getFenetre().getBuffer(1);
		this.tapis = controller.getTapis();
		this.converter = controller.getConverter();
		
		this.lunette = new Lunette();
		this.lunette.setTapis(this.controller.getTapis());
		this.lunette.setScale(0.2);
		this.lunette.setLargeur(this.buffer.getLargeur() * lunette.getScale());
		this.lunette.setHauteur(lunette.getLargeur() * this.tapis.getHauteur() / this.tapis.getLargeur());
		this.lunette.setX(this.buffer.getLargeur() - lunette.getLargeur() - 10.0);
		this.lunette.setY(10.0);
		
		this.shape = new Box(
				(int)Math.round(this.lunette.getX()), 
				(int)Math.round(this.lunette.getY()), 
				(int)Math.round(this.lunette.getLargeur()), 
				(int)Math.round(this.lunette.getHauteur()));
	}
	
	
	
	

	@Override
	public void mouseLeftPressed(int x, int y) {
		System.out.println("yy");
	}





	@Override
	public void draw() {
		this.buffer.drawRect(Color.black, 
				(int)Math.round(this.lunette.getX()), (int)Math.round(this.lunette.getY()), 
				(int)Math.round(this.lunette.getLargeur()), (int)Math.round(this.lunette.getHauteur()));
	
		double sx = this.lunette.getLargeur() / this.lunette.getTapis().getLargeur();
		
		double xi = this.converter.getCorner().getX() * sx;
		xi += this.lunette.getTapis().getLargeur() * sx / 2.0;
		xi += this.lunette.getX();
		double yi = -this.converter.getCorner().getY() * sx;
		yi += this.lunette.getTapis().getHauteur() * sx / 2.0;
		yi += this.lunette.getY();
		double l = this.converter.getLargeur() * sx;
		double h = this.converter.getHauteur() * sx;
		
		this.buffer.fillRect(Color.white, 
				(int)Math.round(xi), (int)Math.round(yi), 
				(int)Math.round(l), (int)Math.round(h),0.2f);
		this.buffer.drawRect(Color.black, 
				(int)Math.round(xi), (int)Math.round(yi), 
				(int)Math.round(l), (int)Math.round(h));
	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}

}
