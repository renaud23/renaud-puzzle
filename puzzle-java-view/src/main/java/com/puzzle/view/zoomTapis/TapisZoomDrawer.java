package com.puzzle.view.zoomTapis;


import java.awt.Color;
import java.awt.Image;

import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.Tapis;
import com.puzzle.view.controller.TapisConverter;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.tool.ImageBuffer;
import com.puzzle.view.tool.ImageMemoryManager;
import com.puzzle.view.tool.SimpleImageLoader;
import com.renaud.manager.Rect;



public class TapisZoomDrawer implements IDrawer{
	
	private ImageBuffer tapisBuffer;
	private Tapis tapis;
	private TapisConverter converter;
	private Image background;
	
	

	public TapisZoomDrawer(Tapis tapis,ImageBuffer tapisBuffer,
			TapisConverter converter) {
		this.tapisBuffer = tapisBuffer;
		this.tapis = tapis;
		this.converter = converter;
		
		this.background = new SimpleImageLoader().getImage("/home/renaud/git/renaud-puzzle/puzzle-java-view/src/main/resources/background/tapis.png");
		
	}
	

	@Override
	public void draw() {
		this.clean();
		
		TapisZoomConverteur cvt = (TapisZoomConverteur)this.converter;
		Rect r = new Rect(cvt.getCorner().getX(), cvt.getCorner().getY(), 
				cvt.getLargeur(), cvt.getHauteur());
		
		//	 dessin tapis
		for(Piece piece : this.tapis){
			if(piece.getRect().isIn(r)){

				Image img = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getImage(piece.getId());
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
//				Point c = new Point(piece.getCentre().getX(), piece.getCentre().getY());
//				this.converter.convertModelToScreen(c);
//				this.tapisBuffer.drawRect(Color.white, (int)Math.round(c.getX()), (int)Math.round(c.getY()), 2, 2);
				
//				if(piece.getComposite() != null) {
//					Point c = new Point(piece.getComposite().getCentre().getX(), piece.getComposite().getCentre().getY());
//					this.converter.convertModelToScreen(c);
//					this.tapisBuffer.drawRect(Color.red, (int)Math.round(c.getX()), (int)Math.round(c.getY()), 2, 2);
//				}
			}
		}
			
		
	}

	@Override
	public void clean() {
		this.tapisBuffer.clean();
		
		this.tapisBuffer.drawImage(this.background, 0, 0, 0, 0, 0, 1.0, 1.0f);
	}


}
