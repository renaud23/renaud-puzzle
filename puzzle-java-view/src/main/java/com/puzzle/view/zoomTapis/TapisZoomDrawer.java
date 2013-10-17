package com.puzzle.view.zoomTapis;


import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.Tapis;
import com.puzzle.view.controller.TapisConverter;
import com.puzzle.view.drawer.CompositeImageManager;
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
	
	

	public TapisZoomDrawer(Image background,Tapis tapis,ImageBuffer tapisBuffer,
			TapisConverter converter) {
		this.tapisBuffer = tapisBuffer;
		this.tapis = tapis;
		this.converter = converter;
		
		this.background = background;
		
	}
	

	@Override
	public void draw() {
		this.clean();
		
		TapisZoomConverteur cvt = (TapisZoomConverteur)this.converter;
		Rect r = new Rect(cvt.getCorner().getX(), cvt.getCorner().getY(), 
				cvt.getLargeur(), cvt.getHauteur());
		
		//	 dessin tapis
		List<CompositePiece> alreadyDraw = new ArrayList<CompositePiece>();
		for(Piece piece : this.tapis){
			if(piece.getComposite() == null){
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
				}// if in
				
				// pour dev
//				Point c = new Point(piece.getCentre().getX(), piece.getCentre().getY());
//				this.converter.convertModelToScreen(c);
//				this.tapisBuffer.drawRect(Color.white, (int)Math.round(c.getX()), (int)Math.round(c.getY()), 2, 2);
				
				if(piece.getComposite() != null) {
					Point c = new Point(piece.getComposite().getCentre().getX(), piece.getComposite().getCentre().getY());
					this.converter.convertModelToScreen(c);
					this.tapisBuffer.drawRect(Color.red, (int)Math.round(c.getX()), (int)Math.round(c.getY()), 2, 2);
				}
				
				
				
			}else{
				if(!alreadyDraw.contains(piece.getComposite())){
					alreadyDraw.add(piece.getComposite());
					CompositePiece cmp = piece.getComposite();
					
					if(cmp.getRect().isIn(r)){
						ImageBuffer b = CompositeImageManager.getInstance().getBuffer(cmp);
						double scale = CompositeImageManager.getInstance().getScale();
						Point p = new Point(cmp.getCentre().getX(),cmp.getCentre().getY());
						this.converter.convertModelToScreen(p);
						
						double x = p.getX();
						x -= b.getImage().getWidth(null) / 2.0 * this.converter.getScaleX() / scale;
						
						double y = p.getY();
						y -= b.getImage().getHeight(null) / 2.0 * this.converter.getScaleY();
						
						this.tapisBuffer.drawImage(b.getImage(),
								x,  y, 
								p.getX() , p.getY(), -cmp.getAngle(), 
								this.converter.getScaleX()/scale, this.converter.getScaleY()/scale, 
								1.0f);
						
						
						
						Point c = new Point(piece.getComposite().getCentre().getX(), piece.getComposite().getCentre().getY());
						this.converter.convertModelToScreen(c);
						this.tapisBuffer.drawRect(Color.red, (int)Math.round(c.getX()), (int)Math.round(c.getY()), 2, 2);
					}
					
				}// if
			}// else
			
			
			
		}// for
			
		
	}

	@Override
	public void clean() {
	
		double scalex = tapis.getLargeur() / background.getWidth(null);
		double scaley = tapis.getHauteur() / background.getHeight(null);
		
		double x = -this.tapis.getLargeur() / 2.0;
		x -= ((TapisZoomConverteur)converter).getCorner().getX();
		x *= converter.getScaleX();
		double y = -this.tapis.getHauteur() / 2.0;
		y += ((TapisZoomConverteur)converter).getCorner().getY();
		y *= converter.getScaleY();
		
		this.tapisBuffer.drawImage(this.background, 
				x, y, 
				0, 0, 0, 
				scalex*converter.getScaleX(), scaley*converter.getScaleY(), 1.0f);
	}


}
