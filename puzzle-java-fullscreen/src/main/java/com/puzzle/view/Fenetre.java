package com.puzzle.view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.puzzle.view.java.JavaBuffer;





public class Fenetre {
	private Window window;
	private BufferStrategy strategy; 
	private JavaBuffer buffer;
	private int largeur;
	private int hauteur;

	
	public Fenetre(int largeur,int hauteur){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
		GraphicsDevice device = ge.getDefaultScreenDevice();
		
		
		if(device.isFullScreenSupported()){
			this.window = new JFrame();
			((JFrame)this.window).setUndecorated(true);
			((JFrame)this.window).setResizable(false);
			this.buffer = new JavaBuffer(Color.black, largeur, hauteur);
			
			this.window.setPreferredSize(new Dimension(largeur,hauteur));
			this.window.setVisible(true);
		    this.window.pack();
		    this.largeur = largeur;
		    this.hauteur = hauteur;
			
			
			this.window.setIgnoreRepaint(true);  
			this.window.createBufferStrategy(2);
			this.strategy = this.window.getBufferStrategy(); 
		}

	}

	public Fenetre(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
		GraphicsDevice device = ge.getDefaultScreenDevice();
		
		if(device.isFullScreenSupported()){
			this.window = new JFrame();
			((JFrame)this.window).setUndecorated(true);
			((JFrame)this.window).setResizable(false);
		    
			device.setFullScreenWindow(this.window);
			device.setDisplayMode(device.getDisplayMode());
			
			this.largeur = device.getDisplayMode().getWidth();
			this.hauteur = device.getDisplayMode().getHeight();
			this.buffer = new JavaBuffer(Color.black, largeur, hauteur);

			this.window.setIgnoreRepaint(true);  
			this.window.createBufferStrategy(2);
			this.strategy = this.window.getBufferStrategy(); 
		}

	}

	public int getLargeur() {
		return largeur;
	}



	public int getHauteur() {
		return hauteur;
	}



	public BufferStrategy getStrategy() {
		return strategy;
	}

	
	public JavaBuffer getBuffer() {
		return buffer;
	}

	public Window getWindow(){
		return this.window;
	}
	
	
}
