package com.puzzle.view.zoomTapis;

import java.awt.Image;

import com.puzzle.model.Piece;

public class ImageBufferOperation {
	private Piece piece;
	private Image image;
	
	
	
	public ImageBufferOperation(Piece piece, Image image) {
		this.piece = piece;
		this.image = image;
	}
	
	
	public Piece getPiece() {
		return piece;
	}
	public Image getImage() {
		return image;
	}
	
	
}
