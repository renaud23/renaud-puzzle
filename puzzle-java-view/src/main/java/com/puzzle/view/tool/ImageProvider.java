package com.puzzle.view.tool;


import java.awt.image.VolatileImage;

public interface ImageProvider<U> {

	public PieceBufferOperation getImage(U code);
	
}
