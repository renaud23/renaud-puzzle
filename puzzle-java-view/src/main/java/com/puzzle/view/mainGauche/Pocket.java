package com.puzzle.view.mainGauche;


import java.util.HashMap;
import java.util.Map;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.RectPiece;
import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.hud.FreeBox;
import com.puzzle.view.hud.HudControler;



public class Pocket {
	private Map<Piece, PieceInPocket> pocket = new HashMap<Piece, PieceInPocket>();
	private HudControler controller;
	private Fenetre fenetre;
	private Tapis tapis;
	
	private int xRef = 50;
	private int yRef = 500;
	
	private int limite;
	
	public Pocket(HudControler controller, Tapis tapis, Fenetre f){
		this.controller = controller;
		this.fenetre = f;
		this.tapis = tapis;
	}
	
	public void add(Piece p){
		
		double scale = 0.1;// TODO
		
		RectPiece r = (RectPiece) p.getRect();
		Point[] in = r.getCoins();
		java.awt.Point[] out = new java.awt.Point[4];
		Point c = p.getCentre();
		
		for(int i=0;i<4;i++){
			out[i] = new java.awt.Point();
			out[i].x = (int) Math.round( (in[i].getX() - c.getX()) *scale);
			out[i].x += this.xRef;
			out[i].y = (int) Math.round( (in[i].getY() - c.getY()) *scale);
			out[i].y *= -1;
			out[i].y += this.yRef;
		}
		
		FreeBox b = new FreeBox(out);
		PieceInPocket pi = new PieceInPocket(p,this.tapis, this.fenetre, b);
		
		this.controller.addArea(pi);
		this.pocket.put(p,pi);
	}
	
	public void remove(Piece p){
		this.pocket.remove(p);
	}
	
}
