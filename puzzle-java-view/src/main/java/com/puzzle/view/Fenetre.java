package com.puzzle.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.puzzle.view.listener.MyWindowListner;
import com.puzzle.view.mainGauche.MainGaucheView;



public class Fenetre extends Thread {
	
	private JFrame frame;
	private Offscreen offscreen;
	private List<ImageBuffer> backBuffers;
	private MainGaucheView mainGauche;
	private int largeur;
	private int hauteur;
	
	
	public Fenetre(int largeur,int hauteur){
		this.frame = new JFrame("JPuzzle");
		this.largeur = largeur;
		this.hauteur = hauteur;
		
		// tapis
		this.backBuffers = new ArrayList<ImageBuffer>();
		this.backBuffers.add(0, new ImageBuffer(new Color(255,0,0,255),(int)(largeur*0.8),this.hauteur));
		this.backBuffers.get(0).transparentClean();
		this.backBuffers.add(1, new ImageBuffer(new Color(0,0,0,0), (int)(largeur*0.8),this.hauteur));
		this.backBuffers.get(1).transparentClean();
		this.offscreen = new Offscreen(this.backBuffers);
		
		
		// main gauche
		this.mainGauche = new MainGaucheView((int)(largeur*0.2), hauteur);
	
		
		
		
		this.mainGauche.getOffscreen().setPreferredSize(new Dimension((int)(largeur*0.2),this.hauteur));
		this.offscreen.setPreferredSize(new Dimension((int)(largeur*0.8),this.hauteur));
		this.offscreen.validate();
		
		this.frame.add(this.mainGauche.getOffscreen(),BorderLayout.WEST);
		this.frame.add(this.offscreen,BorderLayout.EAST);
		
		this.frame.addComponentListener(new MyWindowListner(this));
		
		
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
		this.frame.pack();
		this.frame.setVisible(true);
		
		this.frame.repaint();
		
		
	}
	
	
	public void resize(){
		// TODO
		
		
		
		
	}

	
	public MainGaucheView getMainGauche() {
		return mainGauche;
	}


	public JFrame getFrame() {
		return frame;
	}


	public Offscreen getOffscreen() {
		return this.offscreen;
	}
	
	public ImageBuffer getBuffer(int i){
		ImageBuffer b = null;
		if(i< this.backBuffers.size()) b = this.backBuffers.get(i);
		return b;
	}
	
	
	public void repaint(){
		this.frame.repaint();
	}

//	public void run(){
//		while(true)
//			System.out.println("u");
//	}



	
}
