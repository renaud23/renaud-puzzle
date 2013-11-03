package com.puzzle.view.core;

import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import com.puzzle.view.Fenetre;

public class GameLoop {
	private Timer timer;
	private TimerTask task;
	
	
	public GameLoop(final Renderer renderer, final Activater activater, final Fenetre fenetre) {

	    this.task = new TimerTask() {
			
			@Override
			public void run() {

				activater.activate();
				renderer.Render();
				
				Graphics g = fenetre.getStrategy().getDrawGraphics();
				g.drawImage(fenetre.getBuffer().getImage(), 0, 0, null);
				g.dispose();
				
				fenetre.getStrategy().show();
			}
		};
	    
	    this.timer = new Timer(); 
	    this.timer.scheduleAtFixedRate(this.task, 0, 10);
		
	}
	
	
	
}
