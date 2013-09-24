package com.jPuzzle.view.drawer;

import java.awt.Image;

import com.jPuzzle.view.image.ImageBuffer;
import com.jPuzzle.view.image.ImageMemoryManager;
import com.jPuzzle.view.image.Offscreen;
import com.puzzle.model.Piece;

public class PieceDrawer implements IDrawer{
	
	private Piece piece;
	private ImageBuffer offscreen;
	private Transformation transformation;
	
	

	public PieceDrawer(Piece piece, ImageBuffer offscreen) {
		this.piece = piece;
		this.offscreen = offscreen;
	}



	@Override
	public void draw() {
		Image img = ImageMemoryManager.getInstance().getImage(this.piece.getId());
	
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
