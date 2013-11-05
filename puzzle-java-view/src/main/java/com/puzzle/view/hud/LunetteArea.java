package com.puzzle.view.hud;

import java.awt.Color;

import com.puzzle.model.Tapis;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.tool.JImageBuffer;
import com.puzzle.view.zoomTapis.Lunette;
import com.puzzle.view.zoomTapis.TapisZoomConverteur;
import com.puzzle.zoomTapis.state.TapisZoomControllerEx;

public class LunetteArea extends HudArea implements IDrawer{
	private Lunette lunette;
	private Tapis tapis;
	private JImageBuffer buffer;
	private TapisZoomConverteur converter;
	
	
	
	

	public LunetteArea(TapisZoomControllerEx controller) {
		this.tapis = controller.getTapis();
		this.buffer = controller.getFenetre().getBuffer(1);
		this.converter = controller.getConverter();
		
		lunette = new Lunette();
		lunette.setTapis(tapis);
		lunette.setScale(0.2);
		lunette.setLargeur(this.buffer.getLargeur() * lunette.getScale());
		lunette.setHauteur(lunette.getLargeur() * tapis.getHauteur() / tapis.getLargeur());
		lunette.setX(this.buffer.getLargeur() - lunette.getLargeur() - 10.0);
		lunette.setY(10.0);
		
		
		this.shape = new Box( 
				(int) Math.round(this.lunette.getX()),
				(int) Math.round(this.lunette.getY()),
				(int) Math.round(this.lunette.getLargeur()),
				(int) Math.round(this.lunette.getHauteur()));

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
	public void mouseLeftPressed(int x, int y) {

		
	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}


	
	
	
	

}
