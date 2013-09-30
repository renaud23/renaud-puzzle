package com.jPuzzle.view.basicTapis;


import java.awt.Image;

import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.Tapis;
import com.puzzle.view.ImageBuffer;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.drawer.ImageMemoryManager;


public class TapisBasicDrawer implements IDrawer {
	
	private Tapis tapis;
	private ImageBuffer tapisBuffer;

	


	public TapisBasicDrawer(Tapis tapis, ImageBuffer tapisBuffer) {
		this.tapis = tapis;
		this.tapisBuffer = tapisBuffer;
	}


	@Override
	public void draw() {
		this.tapisBuffer.clean();
		
		//	 dessin tapis
		for(Piece piece : this.tapis){
			Image img = ImageMemoryManager.getInstance().getImage(piece.getId());
			Point p = new Point(piece.getCentre().getX(),piece.getCentre().getY());
			TapisBasicConverter.getInstance().convertModelToScreen(p);
			
			double x = p.getX();
			x -= piece.getLargeur() / 2.0 * TapisBasicConverter.getInstance().getScaleX();
			
			double y = p.getY();
			y -= piece.getHauteur() / 2.0 * TapisBasicConverter.getInstance().getScaleY();
		
			this.tapisBuffer.drawImage(img,
					x,  y, 
					p.getX() , p.getY(), -piece.getAngle(), 
					TapisBasicConverter.getInstance().getScaleX(), TapisBasicConverter.getInstance().getScaleY(), 
					1.0f);
		}
		
	}
	
}
