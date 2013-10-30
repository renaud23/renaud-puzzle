package com.puzzle.view.state;

import com.puzzle.model.Point;
import com.puzzle.view.java.Game;




public class MainVide implements IState{
	private Game game;
	
	
	

	public MainVide(Game game) {
		this.game = game;
	}




	@Override
	public void wheel(boolean up) {
		this.game.getConverter().zoom(up);
	}



	@Override
	public void dragRight(int vx, int vy) {
		Point p = new Point(vx,vy);
		this.game.getConverter().moveBy(p);
	}
	
	
	
}
