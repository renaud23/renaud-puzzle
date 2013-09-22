package com.view.jPuzzle.view.draw;

import com.jPuzzle.view.Offscreen;
import com.puzzle.model.ComponentPiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Tapis;

public class TapisBasicDrawer implements IDrawer {
	
	private Tapis tapis;
	private Offscreen offscreen;
	

	public TapisBasicDrawer(Tapis tapis, Offscreen offscreen) {
		this.tapis = tapis;
		this.offscreen = offscreen;
	}


	@Override
	public void draw() {
		
		for(ComponentPiece cmp : this.tapis){
			((IDrawer) cmp).draw();
		}
		
	}

}
