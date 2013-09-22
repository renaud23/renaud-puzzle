package com.jPuzzle.view;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import com.jPuzzle.view.image.MemoryManager;
import com.puzzle.model.Piece;
import com.puzzle.model.Tapis;
import com.view.jPuzzle.view.draw.IHPieceComponent;
import com.view.jPuzzle.view.draw.PieceDrawer;
import com.view.jPuzzle.view.draw.TapisBasicDrawer;
import com.view.jPuzzle.view.draw.IDrawer;

public class MainScreen implements Observer{
	private JFrame fenetre;
	private Offscreen offPuzzle;
	private IDrawer drawer;
	
	
	public MainScreen(){
		this.fenetre = new JFrame("JPuzzle");
		this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.offPuzzle = new Offscreen(800, 800);
		this.offPuzzle.setPreferredSize(new Dimension(800,600));
		
		this.fenetre.add(this.offPuzzle);
		
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
		
		
		Tapis tapis = new Tapis(1500,1500);
		
		
		m.setDrawer(new TapisBasicDrawer(tapis, m.getOffPuzzle()));
		
		
//		Piece p1 = new Piece(1,100, 100, 100, 86);
//		Piece p2 = new Piece(2,-50, 50, 100, 86);
//		Piece p3 = new Piece(3, 80, -80, 86, 100);
//		Piece p4 = new Piece(4, 0, 0, 100, 100);
		
		
		Piece p1 = new Piece(1,-500, 500, 100, 86);
		Piece p2 = new Piece(2,-450, 450, 100, 86);
		Piece p3 = new Piece(3, -400, 400, 86, 100);
		Piece p4 = new Piece(4, -350, 350, 100, 100);
		
		tapis.poserPiece(IHPieceComponent.<Piece>createComponent(p1,new PieceDrawer(p1,m.getOffPuzzle())));
		tapis.poserPiece(IHPieceComponent.<Piece>createComponent(p2,new PieceDrawer(p2,m.getOffPuzzle())));
		tapis.poserPiece(IHPieceComponent.<Piece>createComponent(p3,new PieceDrawer(p3,m.getOffPuzzle())));
		tapis.poserPiece(IHPieceComponent.<Piece>createComponent(p4,new PieceDrawer(p4,m.getOffPuzzle())));
		
		
		m.draw();
		
		
		System.out.println(p1.getZIndex());
		System.out.println(p2.getZIndex());
		System.out.println(p3.getZIndex());
		System.out.println(p4.getZIndex());
		
//		
//		Set<ComponentPiece> set = tapis.chercherPiece(new Rect(50,-40,1,1));
//		System.out.println(set.toString());
	}

	
	
	
	
	

	public Offscreen getOffPuzzle() {
		return offPuzzle;
	}

	public void setOffPuzzle(Offscreen offPuzzle) {
		this.offPuzzle = offPuzzle;
	}

	public IDrawer getDrawer() {
		return drawer;
	}

	public void setDrawer(IDrawer drawer) {
		this.drawer = drawer;
	}
}
