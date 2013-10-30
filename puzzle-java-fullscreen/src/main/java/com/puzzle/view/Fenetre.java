package com.puzzle.view;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.TimerTask;

import com.puzzle.view.core.IJouable;




public class Fenetre {
	private Window window;
	private BufferStrategy strategy; 
	private Graphics2D buffer;

	
	public Fenetre(int largeur,int hauteur){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
		GraphicsDevice device = ge.getDefaultScreenDevice();
		
		
		if(device.isFullScreenSupported()){
			this.window = new Window(null);
			
			this.window.setPreferredSize(new Dimension(largeur,hauteur));
			this.window.setVisible(true);
		    this.window.pack();
//			device.setFullScreenWindow(this.window);
//			device.setDisplayMode(device.getDisplayMode());
			
			// inhibe la méthode courante d'affichage du composant 
			this.window.setIgnoreRepaint(true);  
		    // on créé 2 buffers dans la VRAM donc c'est du double-buffering
			this.window.createBufferStrategy(2);
			// récupère les buffers graphiques dans la mémoire VRAM
			this.strategy = this.window.getBufferStrategy(); 
		    this.buffer = (Graphics2D) this.strategy.getDrawGraphics();
		   
		}

	}



	public BufferStrategy getStrategy() {
		return strategy;
	}

	public Graphics2D getBuffer() {
		return buffer;
	}
	
	public Window getWindow(){
		return this.window;
	}
	
	
}
