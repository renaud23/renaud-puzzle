package com.jPuzzle.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import com.jPuzzle.view.basicTapis.TapisBasicControler;
import com.jPuzzle.view.basicTapis.TapisBasicConverter;
import com.jPuzzle.view.basicTapis.TapisBasicDrawer;
import com.jPuzzle.view.drawer.HeadUpDisplayDrawer;
import com.jPuzzle.view.drawer.IDrawable;
import com.jPuzzle.view.drawer.IDrawer;
import com.jPuzzle.view.drawer.IDrawerParametrable;
import com.jPuzzle.view.image.ImageBuffer;
import com.jPuzzle.view.image.ImageMemoryManager;
import com.jPuzzle.view.image.Offscreen;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.Tapis;

public class MainScreen implements IDrawable{
	private JFrame fenetre;
	private Offscreen offscreen;
	private ImageBuffer tapisOffscreen;
	private ImageBuffer hudOffscreen;
	private IDrawer tapisDrawer;
	private IDrawerParametrable<Point> hudDrawer;
	
	
	private Point point;
	
	
	public MainScreen(){
		this.fenetre = new JFrame("JPuzzle");
		this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.tapisOffscreen = new ImageBuffer(new Color(100,150,50,255), 800, 800);
		this.hudOffscreen = new ImageBuffer(new Color(0,0,0,0), 800, 800);
		
		this.offscreen = new Offscreen(800, 800);
		this.offscreen.setPreferredSize(new Dimension(800,800));
		
		this.fenetre.add(this.offscreen);
		
		this.fenetre.pack();
		this.fenetre.repaint();
		this.fenetre.setVisible(true);
	}

	

	
	
	public void drawTapis(){
		if(this.tapisDrawer != null) this.tapisDrawer.draw();
	}

	public void drawHud(){
		if(this.hudDrawer != null){
			this.hudDrawer.setParameter(this.point);
			this.hudDrawer.draw();
		}
	}

	
	
	
	public <U> void setParam(U param){
		if(param instanceof Point){
			this.point = (Point) param;
		}
	}
	
	
	public void repaint(){
		// reaffiche
		this.offscreen.drawImage(this.tapisOffscreen.getImage(), 0, 0);
		this.offscreen.drawImage(this.hudOffscreen.getImage(), 0, 0);
		
		
		this.fenetre.repaint();
	}

	

	
	
	
	public static void main(String[] args){
		MainScreen m = new MainScreen();
		ImageMemoryManager.getInstance().setPath("E:/git/renaud-puzzle/puzzle-java/src/main/resources/mini_mimie/images/");
		
		
		Tapis tapis = new Tapis(1500,1500);
		
		Piece p1 = new Piece(1,100, 100, 100, 86);
		Piece p2 = new Piece(2,-50, 50, 100, 86);
		Piece p3 = new Piece(3, 80, -80, 86, 100);
		Piece p4 = new Piece(4, 0, 0, 100, 100);
		
		tapis.poserPiece(p1);
		tapis.poserPiece(p2);
		tapis.poserPiece(p3);
		tapis.poserPiece(p4);
		
		
		TapisBasicControler tc = new TapisBasicControler(tapis);
		tc.setDrawable(m);
		m.getOffscreen().addMouseListener(tc);
		m.getOffscreen().addMouseMotionListener(tc);
		m.getOffscreen().addMouseWheelListener(tc);
		m.setTapisDrawer(new TapisBasicDrawer(tapis, m.getTapisOffscreen()));
		m.setHudDrawer(new HeadUpDisplayDrawer( m.getHudOffscreen() ));
		tapis.addObserver(tc);
		
		
		TapisBasicConverter.getInstance().setTapis(tapis);
		TapisBasicConverter.getInstance().setOffscreen(m.getOffscreen());
		TapisBasicConverter.getInstance().update();
		
		/* ** */
		m.drawTapis();
		m.drawHud();
		m.repaint();
		
		
//		System.out.println(tapis.chercherPiece(0, 0));
//		System.out.println(p3.getRect());
//		System.out.println(p4.getRect());
		
	}

	
	
	
	
	



	

	public Offscreen getOffscreen() {
		return offscreen;
	}

	public void setOffscreen(Offscreen offscreen) {
		this.offscreen = offscreen;
	}

	public ImageBuffer getHudOffscreen() {
		return hudOffscreen;
	}

	public void setHudOffscreen(ImageBuffer hudOffscreen) {
		this.hudOffscreen = hudOffscreen;
	}

	public ImageBuffer getTapisOffscreen() {
		return tapisOffscreen;
	}

	public void setTapisOffscreen(ImageBuffer tapisOffscreen) {
		this.tapisOffscreen = tapisOffscreen;
	}

	public IDrawer getTapisDrawer() {
		return tapisDrawer;
	}

	public void setTapisDrawer(IDrawer tapisDrawer) {
		this.tapisDrawer = tapisDrawer;
	}
	
	public IDrawer getHudDrawer() {
		return hudDrawer;
	}
	
	public void setHudDrawer(IDrawerParametrable<Point> hudDrawer) {
		this.hudDrawer = hudDrawer;
	}

	

	
}
