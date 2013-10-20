package com.puzzle.view.tool.provider;

import java.awt.image.VolatileImage;
import com.puzzle.model.Piece;

public class PieceBufferOperation {
	private Piece piece;
	private VolatileImage image;
	
	
	
	public PieceBufferOperation(Piece piece, VolatileImage image) {
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
