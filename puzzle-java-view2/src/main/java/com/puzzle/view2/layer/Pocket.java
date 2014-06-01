package com.puzzle.view2.layer;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.puzzle.model.Piece;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;
import com.puzzle.view2.widget.Widget;

public class Pocket implements Observer{
	private Map<Piece, Widget> widgets;
	
	private Tapis tapis;
	private HudLayer hud;
	
	private int x;
	private int y;
	private int largeur;
	private int hauteur;
	
	
	public Pocket(Tapis tapis,HudLayer hud, int x, int y, int largeur, int hauteur) {
		this.tapis = tapis;
		this.hud = hud;
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
		
		this.tapis.addObserver(this);
	}


	public void update(Observable o, Object arg) {
		if(arg instanceof State){
			State state = (State) arg;
			if(state == State.gaucheToDroite){
				
			}else if(state == State.droiteToGauche){
				
			}
		}
	}
	
	
	

}
