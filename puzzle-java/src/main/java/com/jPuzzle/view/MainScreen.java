package com.jPuzzle.view;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import com.jPuzzle.view.basicTapis.TapisBasicControler;
import com.jPuzzle.view.basicTapis.TapisBasicConverter;
import com.jPuzzle.view.basicTapis.TapisBasicDrawer;
import com.jPuzzle.view.drawer.IDrawer;
import com.jPuzzle.view.drawer.PieceDrawer;
import com.jPuzzle.view.image.IHPieceComponent;
import com.jPuzzle.view.image.MemoryManager;
import com.jPuzzle.view.image.Offscreen;
import com.puzzle.model.Piece;
import com.puzzle.model.Tapis;

public class MainScreen implements Observer{
	private JFrame fenetre;
	private Offscreen tapisOffscreen;
	private IDrawer drawer;
	
	
	public MainScreen(){
		this.fenetre = new JFrame("JPuzzle");
		this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.tapisOffscreen = new Offscreen(800, 800);
		this.tapisOffscreen.setPreferredSize(new Dimension(800,600));
		
		this.fenetre.add(this.tapisOffscreen);
		
		this.fenetre.pack();
		this.fenetre.repaint();
		this.fenetre.setVisible(true);
	}

	

	public void draw(){
		if(this.drawer != null) this.drawer.draw();
		this.fenetre.repaint();
	}




	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	public static void main(String[] args){
		MainScreen m = new MainScreen();
		MemoryManager.getInstance().setPath("E:/git/renaud-puzzle/puzzle-java/src/main/resources/mini_mimie/images/");
		
		
		Tapis tapis = new Tapis(800,800);
		
		Piece p1 = new Piece(1,100, 100, 100, 86);
		Piece p2 = new Piece(2,-50, 50, 100, 86);
		Piece p3 = new Piece(3, 80, -80, 86, 100);
		Piece p4 = new Piece(4, 0, 0, 100, 100);
		
		tapis.poserPiece(IHPieceComponent.<Piece>createComponent(p1,new PieceDrawer(p1,m.getTapisOffscreen())));
		tapis.poserPiece(IHPieceComponent.<Piece>createComponent(p2,new PieceDrawer(p2,m.getTapisOffscreen())));
		tapis.poserPiece(IHPieceComponent.<Piece>createComponent(p3,new PieceDrawer(p3,m.getTapisOffscreen())));
		tapis.poserPiece(IHPieceComponent.<Piece>createComponent(p4,new PieceDrawer(p4,m.getTapisOffscreen())));
		
		TapisBasicControler tc = new TapisBasicControler(tapis);
		m.getTapisOffscreen().addMouseListener(tc);
		m.getTapisOffscreen().addMouseMotionListener(tc);
		m.setDrawer(new TapisBasicDrawer(tapis, m.getTapisOffscreen()));
		
		
		TapisBasicConverter.getInstance().setTapis(tapis);
		TapisBasicConverter.getInstance().setOffscreen(m.getTapisOffscreen());
		TapisBasicConverter.getInstance().update();
		
		/* ** */
		m.draw();
		
		
//		System.out.println(tapis.chercherPiece(0, 0));
//		System.out.println(p3.getRect());
//		System.out.println(p4.getRect());
		
	}

	
	
	
	
	

	public Offscreen getTapisOffscreen() {
		return tapisOffscreen;
	}


	public IDrawer getDrawer() {
		return drawer;
	}

	public void setDrawer(IDrawer drawer) {
		this.drawer = drawer;
	}
}
