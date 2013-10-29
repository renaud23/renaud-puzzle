package com.puzzle.view.state;

import com.puzzle.view.tool.Game;




public class MainVide implements IState{
	private Game game;
	
	
	

	public MainVide(Game game) {
		this.game = game;
	}




	@Override
	public void wheel(boolean up) {
		this.game.getConverter().zoom(up);
	}
	
	
	
}
