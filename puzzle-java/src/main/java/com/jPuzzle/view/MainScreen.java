package com.jPuzzle.view;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.JFrame;

import com.puzzle.model.ComponentPiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Tapis;
import com.renaud.manager.Rect;
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
		
		this.offPuzzle = new Offscreen(800, 600);
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
		
		
		Tapis tapis = new Tapis(800,600);
		
//		tapis.poserPiece(new Piece(1,100, 100, 100, 86));
//		tapis.poserPiece(new Piece(2,-50, 50, 100, 86));
//		tapis.poserPiece(new Piece(3, 80, -80, 86, 100));
//		tapis.poserPiece(new Piece(4, 30, 0, 100, 100));
		
		
		m.setDrawer(new TapisBasicDrawer(tapis, m.getOffPuzzle()));
		
		
		
		Piece p = new Piece(1,100, 100, 100, 86);
		ComponentPiece c = IHPieceComponent.<Piece>createComponent(p,new PieceDrawer(p,m.getOffPuzzle()));
		tapis.poserPiece(c);
		((IDrawer)c).draw();
		
		m.draw();
		
		
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
