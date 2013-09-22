package com.view.jPuzzle.view.draw;

import com.jPuzzle.view.Offscreen;
import com.puzzle.model.Piece;

public class PieceDrawer implements IDrawer{
	
	private Piece piece;
	private Offscreen offscreen;
	
	

	public PieceDrawer(Piece piece, Offscreen offscreen) {
		this.piece = piece;
		this.offscreen = offscreen;
	}



	@Override
	public void draw() {
		System.out.println("***");
		
	}

}
