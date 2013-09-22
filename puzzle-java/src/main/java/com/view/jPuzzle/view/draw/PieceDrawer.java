package com.view.jPuzzle.view.draw;

import java.awt.Image;

import com.jPuzzle.view.Offscreen;
import com.jPuzzle.view.image.MemoryManager;
import com.puzzle.model.Piece;
import com.renaud.manager.IRect;

public class PieceDrawer implements IDrawer{
	
	private Piece piece;
	private Offscreen offscreen;
	private Transformation transformation;
	
	

	public PieceDrawer(Piece piece, Offscreen offscreen) {
		this.piece = piece;
		this.offscreen = offscreen;
	}



	@Override
	public void draw() {
		Image img = MemoryManager.getInstance().getImage(this.piece.getId());
	
		double x = transformation.getTx();
		x -= this.piece.getLargeur() / 2.0 * transformation.getSx();
		
		double y = transformation.getTy();
		y -= this.piece.getHauteur() / 2.0 * transformation.getSy();
		
		this.offscreen.drawImage(img, (int)x, (int)y, 
				transformation.getTx() , transformation.getTy(), -piece.getAngle(), 
				transformation.getSx(),
				transformation.getSy(), 1.0f);
		
	
	}



	public void setTransformation(Transformation transformation) {
		this.transformation = transformation;
	}

	
	
	
	
	
}
