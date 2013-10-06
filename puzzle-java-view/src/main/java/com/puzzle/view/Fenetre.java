package com.puzzle.view;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.puzzle.loader.XmlLoader;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Puzzle;
import com.puzzle.model.Tapis;
import com.puzzle.view.basicTapis.TapisBasicController;
import com.puzzle.view.controller.MyKeyListener;
import com.puzzle.view.controller.MyMouseListener;
import com.puzzle.view.controller.MyMouseMotionListener;
import com.puzzle.view.controller.MyMouseWheelListener;
import com.puzzle.view.drawer.ImageMemoryManager;



public class Fenetre extends Thread {
	
	private JFrame frame;
	private Offscreen offscreen;
	private List<ImageBuffer> backBuffers;
	private int largeur;
	private int hauteur;
	
	
	public Fenetre(int largeur,int hauteur){
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.backBuffers = new ArrayList<ImageBuffer>();
		this.backBuffers.add(0, new ImageBuffer(new Color(255,0,0,255), this.largeur,this.hauteur));
		this.backBuffers.get(0).transparentClean();
		this.backBuffers.add(1, new ImageBuffer(new Color(0,0,0,0), this.largeur,this.hauteur));
		this.backBuffers.get(1).transparentClean();
		this.offscreen = new Offscreen(this.backBuffers);
		this.offscreen.setPreferredSize(new Dimension(this.largeur,this.hauteur));
		
		this.frame = new JFrame("JPuzzle");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.frame.add(this.offscreen);
		
		
		
//		this.frame.addComponentListener(new MyWindowListner(this));
		
		
		this.frame.pack();
		this.frame.setVisible(true);
		
		this.frame.repaint();
		
		
	}
	
	
	public void resize(int largeur,int hauteur){
//		this.frontBuffer = new ImageBuffer(Color.yellow,largeur,hauteur);
//		this.frontBuffer.clean();
//		
//		this.offscreen.setPreferredSize(new Dimension(largeur,hauteur));
//		
//		this.backBuffers.clear();
//		this.backBuffers.add(0, new ImageBuffer(new Color(255,0,0,255), 800, 800));
//		this.backBuffers.get(0).transparentClean();
//		this.backBuffers.add(1, new ImageBuffer(new Color(0,0,0,0), 800, 800));
//		this.backBuffers.get(1).transparentClean();
//		this.offscreen = new Offscreen(this.frontBuffer,this.backBuffers);
//		
//		this.frame.add(this.offscreen);
//		this.frame.pack();
//		this.frame.repaint();
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
