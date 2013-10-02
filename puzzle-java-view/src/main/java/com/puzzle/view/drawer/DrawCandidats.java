package com.puzzle.view.drawer;

import java.awt.Color;
import java.awt.Image;
import java.util.List;

import com.puzzle.controller.TapisConverter;
import com.puzzle.model.ComponentPiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.view.ImageBuffer;

public class DrawCandidats implements IDrawer{
	
	private ImageBuffer buffer;
	private List<Piece> candidats;
	private TapisConverter converter;
	
	

	public DrawCandidats(ImageBuffer buffer, List<Piece> candidats,TapisConverter converter) {
		this.buffer = buffer;
		this.candidats = candidats;
		this.converter = converter;
	}

	@Override
	public void draw() {
		for(Piece p : this.candidats){
			Image img = ImageMemoryManager.getInstance().getImage(p.getId());
			Point c = new Point(p.getCentre().getX(),p.getCentre().getY());
			this.converter.convertModelToScreen(c);
			double x = c.getX();
			
			double y = c.getY();
			
			
			this.buffer.drawImageMask(
					img, x, y, 
					c.getX(), c.getY(), p.getAngle(), 
					this.converter.getScaleX(), this.converter.getScaleY(), Color.yellow);
		}
		
	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}

}
