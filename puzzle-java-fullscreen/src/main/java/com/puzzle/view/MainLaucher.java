package com.puzzle.view;

import com.puzzle.view.tool.Game;
import com.puzzle.view.tool.JavaDrawer;


public class MainLaucher {

	public static void main(String[] args) {
		
		
		Game game = new Game();
		Fenetre f = new Fenetre(game);
		JavaDrawer drw = new JavaDrawer(f.getBuffer());
		game.setDrawer(drw);

	}

}
