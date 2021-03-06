package com.puzzle.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import com.puzzle.view.PuzzleCursor.CursorType;
import com.puzzle.view.listener.MyWindowListner;
import com.puzzle.view.tool.JImageBuffer;



public class Fenetre extends Observable{
	
	

	private JFrame frame;
	
	private Offscreen offscreen;
	
	
	private List<JImageBuffer> backBuffers;
	
	private int largeur;
	private int hauteur;
	
	


	private final Timer timer;	
	
	public Fenetre(int largeur,int hauteur){
		this.frame = new JFrame("JPuzzle");
		this.frame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.largeur = largeur;
		this.hauteur = hauteur;
		
		// tapis
		this.backBuffers = new ArrayList<JImageBuffer>();
		this.backBuffers.add(0, new JImageBuffer(new Color(200,50,50,255),(int)(largeur*1.0),this.hauteur));
		this.backBuffers.get(0).transparentClean();
		this.backBuffers.add(1, new JImageBuffer(new Color(0,0,0,0), (int)(largeur*1.0),this.hauteur));
		this.backBuffers.get(1).transparentClean();
		
		
		this.offscreen = new Offscreen(this.backBuffers);
		this.offscreen.setCursor(PuzzleCursor.getInstance().get(CursorType.mainVide));
		
//		this.offscreen.setBounds(new Rectangle(0, 0, largeur, hauteur));
		this.offscreen.setPreferredSize(new Dimension(this.largeur,this.hauteur));
		

		this.frame.add(this.offscreen);
//		this.offscreen.setLocation(0, 0);
//		this.offscreen.setSize(largeur, hauteur);
//		this.frame.add(this.offscreen,BorderLayout.EAST);
		
		this.frame.addComponentListener(new MyWindowListner(this));
		
		
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.offscreen.validate();
		this.frame.validate();
		this.frame.pack();
		this.frame.setVisible(true);
		this.frame.setResizable(false);
		
		
		this.timer = new Timer();
		this.start();
		
		this.frame.repaint();
		
		
	}
	
	public void setIconImage(Image image){
		this.frame.setIconImage(image);
	}
	
	
	public void cleanListener(){
		this.offscreen.cleanListener();
	}
	
	public void resize(int largeur,int hauteur){
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.backBuffers = new ArrayList<JImageBuffer>();
		this.backBuffers.add(0, new JImageBuffer(new Color(200,50,50,255),largeur,hauteur));
		this.backBuffers.get(0).transparentClean();
		this.backBuffers.add(1, new JImageBuffer(new Color(0,0,0,0), largeur,hauteur));
		this.backBuffers.get(1).transparentClean();
		
		this.offscreen.setBackBuffer(this.backBuffers);
		
		
		 
		this.setChanged();
		this.notifyObservers(FenetreMessage.resize);
		
		this.frame.setResizable(true);
		this.offscreen.setPreferredSize(new Dimension(this.largeur,this.hauteur));
		this.offscreen.validate();
		this.frame.validate();
		this.repaint();
		this.frame.pack();
		
		this.frame.setResizable(false);
		
		
	}

	public enum FenetreMessage{
		resize;
	}


	public JFrame getFrame() {
		return frame;
	}


	public Offscreen getOffscreen() {
		return this.offscreen;
	}
	
	public JImageBuffer getBuffer(int i){
		JImageBuffer b = null;
		if(i< this.backBuffers.size()) b = this.backBuffers.get(i);
		return b;
	}
	
	
	public void repaint(){
		this.frame.repaint();
	}



	public void toggleToFullScreen(){


		
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice myDevice = ge.getDefaultScreenDevice();
		
		if(myDevice.isFullScreenSupported()){
			myDevice.setFullScreenWindow(this.frame);
			this.frame.pack();
//			 myDevice.setDisplayMode(newDisplayMode);
		}
		
		
		Thread tr = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					Graphics g = frame.getGraphics();
					
					g.drawImage(getBuffer(0).getImage(), 100, 100, null);
					g.dispose();
					
				}
				
			}
		});
		
		tr.start();
				   
	}
	
	
	private void start(){
		
		final Fenetre f = this;
	
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				f.repaint();
			}
		};
		
		this.timer.schedule(task, 0, 10);
	}

	public int getLargeur() {
		return largeur;
	}


	public int getHauteur() {
		return hauteur;
	}
}
