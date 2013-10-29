package com.puzzle.view.core;

import java.util.Timer;
import java.util.TimerTask;

public class GameLoop {
	private Timer timer;
	private TimerTask task;
	private Renderer renderer;
	private Activater activater;
	
	
	public GameLoop(final Renderer renderer, final Activater activater) {
		this.renderer = renderer;
		this.activater = activater;
		
		
	    this.task = new TimerTask() {
			
			@Override
			public void run() {

				activater.activate();
				renderer.Render();
				
			}
		};
	    
	    this.timer = new Timer(); 
	    this.timer.scheduleAtFixedRate(this.task, 0, 10);
		
		
		
		
		
	}
	
	
	
}
