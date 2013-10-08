package com.puzzle.view.zoomTapis;

import java.awt.Color;
import java.awt.Image;

import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.Tapis;
import com.puzzle.view.ImageBuffer;
import com.puzzle.view.controller.TapisConverter;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.drawer.ImageMemoryManager;
import com.renaud.manager.Rect;



public class TapisZoomDrawer implements IDrawer{
	
	private ImageBuffer tapisBuffer;
	private Tapis tapis;
	private TapisConverter converter;
	

	public TapisZoomDrawer(Tapis tapis,ImageBuffer tapisBuffer,
			TapisConverter converter) {
		this.tapisBuffer = tapisBuffer;
		this.tapis = tapis;
		this.converter = converter;
	}
	

	@Override
	public void draw() {
		this.tapisBuffer.clean();
		
//		System.out.println(((TapisZoomConverteur)this.converter).getLargeur());
		TapisZoomConverteur cvt = (TapisZoomConverteur)this.converter;
		Rect r = new Rect(cvt.getCorner().getX(), cvt.getCorner().getY(), 
				cvt.getLargeur(), cvt.getHauteur());
		
		//	 dessin tapis
		for(Piece piece : this.tapis){
			if(piece.getRect().isIn(r)){
				Image img = ImageMemoryManager.getInstance().getImage(piece.getId());
				Point p = new Point(piece.getCentre().getX(),piece.getCentre().getY());
				this.converter.convertModelToScreen(p);
				
				double x = p.getX();
				x -= img.getWidth(null) / 2.0 * this.converter.getScaleX();
				
				double y = p.getY();
				y -= img.getHeight(null) / 2.0 * this.converter.getScaleY();
			
				this.tapisBuffer.drawImage(img,
						x,  y, 
						p.getX() , p.getY(), -piece.getAngle(), 
						this.converter.getScaleX(), this.converter.getScaleY(), 
						1.0f);
				
				// pour dev
				Point c = new Point(piece.getCentre().getX(), piece.getCentre().getY());
				this.converter.convertModelToScreen(c);
				this.tapisBuffer.drawRect(Color.white, (int)Math.round(c.getX()), (int)Math.round(c.getY()), 2, 2);
			}
		}
			
		
	}

	@Override
	public void clean() {
		this.tapisBuffer.clean();
	}


}
