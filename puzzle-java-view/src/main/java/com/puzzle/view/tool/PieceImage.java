package com.puzzle.view.tool;

import java.awt.image.VolatileImage;
import com.puzzle.model.Piece;

public class PieceImage {
	private Piece piece;
	private VolatileImage image;
	
	
	
	public PieceImage(Piece piece, VolatileImage image) {
		this.piece = piece;
		this.image = image;
	}
	
	
	public Piece getPiece() {
		return piece;
	}
	
	public VolatileImage getImage() {
		return image;
	}
	
	
	
	
}
