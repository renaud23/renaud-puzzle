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

public class MainScreen implements Observer{
	private JFrame fenetre;
	private Offscreen offPuzzle;
	
	
	
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



	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	public static void main(String[] args){
		MainScreen m = new MainScreen();
		
		Tapis tapis = new Tapis(800,600);
		tapis.poserPiece(new Piece(1,100, 100, 100, 86));
		tapis.poserPiece(new Piece(2,-50, 50, 100, 86));
		tapis.poserPiece(new Piece(3, 80, -80, 86, 100));
		tapis.poserPiece(new Piece(4, 30, 0, 100, 100));
		
		
		Set<ComponentPiece> set = tapis.chercherPiece(new Rect(50,-40,1,1));
		
	
		System.out.println(set.toString());
	}

}
