package com.puzzle.view.hud;

import java.awt.Color;import java.util.Observable;
import java.util.Observer;

import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.tool.JImageBuffer;
import com.puzzle.view.zoomTapis.Lunette;
import com.puzzle.view.zoomTapis.TapisZoomController;
import com.puzzle.view.zoomTapis.TapisZoomConverteur;

public class LunetteArea extends HudArea implements IDrawer,Observer{
	private Lunette lunette;
	private Tapis tapis;
	private JImageBuffer buffer;
	private TapisZoomConverteur converter;
	
	
	
	

	public LunetteArea(TapisZoomController controller, JImageBuffer buffer) {
		this.tapis = controller.getTapis();
		this.buffer = buffer;
		this.converter = controller.getConverter();
		
		lunette = new Lunette();
		lunette.setTapis(tapis);
		lunette.setScale(0.2);
		lunette.setLargeur(this.buffer.getLargeur() * lunette.getScale());
		lunette.setHauteur(lunette.getLargeur() * tapis.getHauteur() / tapis.getLargeur());
		lunette.setX(this.buffer.getLargeur() - lunette.getLargeur() - 0.01*buffer.getLargeur());
		lunette.setY(0.01*buffer.getHauteur());
		
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
		

		
		this.buffer.fillRect(Color.blue, 
				(int)Math.round(xi), (int)Math.round(yi), 
				(int)Math.round(l), (int)Math.round(h),0.2f);
		this.buffer.drawRect(Color.black, 
				(int)Math.round(xi), (int)Math.round(yi), 
				(int)Math.round(l), (int)Math.round(h));
	}

	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Fenetre){
			Fenetre f = (Fenetre)o;
			
			
			this.buffer = f.getBuffer(1);
	
			lunette.setLargeur(this.buffer.getLargeur() * lunette.getScale());
			lunette.setHauteur(lunette.getLargeur() * tapis.getHauteur() / tapis.getLargeur());
			lunette.setX(this.buffer.getLargeur() - lunette.getLargeur() - 0.01*buffer.getLargeur());
			lunette.setY(0.01*buffer.getHauteur());
			
			this.draw();
		}
	}
	
	
	@Override
	public void mouseLeftPressed(int x, int y) {

		
	}

	@Override
	public void clean() {
		this.buffer.clean();
	}

	@Override
	public void setBuffer(JImageBuffer buffer) {
		this.buffer = buffer;
	}

	


}
