package com.puzzle.view;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.puzzle.controller.MyKeyListener;
import com.puzzle.controller.MyMouseListener;
import com.puzzle.controller.MyMouseMotionListener;
import com.puzzle.controller.MyMouseWheelListener;
import com.puzzle.loader.XmlLoader;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Puzzle;
import com.puzzle.model.Tapis;
import com.puzzle.view.basicTapis.TapisBasicController;
import com.puzzle.view.drawer.ImageMemoryManager;



public class Fenetre extends Thread {
	
	private JFrame frame;
	private ImageBuffer frontBuffer;
	private Offscreen offscreen;
	private List<ImageBuffer> backBuffers;
	private int largeur;
	private int hauteur;
	
	
	public Fenetre(){
		this.largeur = 600;
		this.hauteur = 600;
		this.backBuffers = new ArrayList<ImageBuffer>();
		this.backBuffers.add(0, new ImageBuffer(new Color(255,0,0,255), this.largeur,this.hauteur));
		this.backBuffers.get(0).transparentClean();
		this.backBuffers.add(1, new ImageBuffer(new Color(0,0,0,0), this.largeur,this.hauteur));
		this.backBuffers.get(1).transparentClean();
		this.frontBuffer = new ImageBuffer(Color.yellow, this.largeur,this.hauteur);
		this.frontBuffer.clean();
		this.offscreen = new Offscreen(this.frontBuffer,this.backBuffers);
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

	public ImageBuffer getFrontBuffer() {
		return frontBuffer;
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

	public void run(){
//		while(true)
//			System.out.println("u");
	}



	public static void main(String[] args){
		File file = new File("E:/git/renaud-puzzle/puzzle-java-view/src/main/resources/floflo/puzzle.xml");
		ImageMemoryManager.getInstance().setPath("E:/git/renaud-puzzle/puzzle-java-view/src/main/resources/floflo/images/");
		XmlLoader ld = new XmlLoader(file);
		
		
		int taille = 2000;
		int te = taille - 200;
		
		Tapis tapis = new Tapis(taille,taille);
		List<Piece> pieces = ld.getPieces();
		Puzzle puzzle = ld.getPuzzle();
		
		Random rnd = new Random();

		CompositePiece cmp = new CompositePiece(0, 0);
		for(Piece p : pieces){
			p.setX(rnd.nextInt(te)-te/2);
			p.setY(rnd.nextInt(te)-te/2);
			p.updateRect();
			
			p.setPuzzle(puzzle);
			puzzle.put(p.getId(), p);
			
			p.poser(tapis);
//			cmp.addComponent(p);
		}
//		cmp.poser(tapis);
		
		Fenetre f = new Fenetre();
		f.start();
		TapisBasicController c = new TapisBasicController(f, tapis);
		f.getOffscreen().addMouseListener(new MyMouseListener(c));
		f.getOffscreen().addMouseMotionListener(new MyMouseMotionListener(c));
		f.getOffscreen().addMouseWheelListener(new MyMouseWheelListener(c));
		f.getFrame().addKeyListener(new MyKeyListener(c));
		
	}
}
