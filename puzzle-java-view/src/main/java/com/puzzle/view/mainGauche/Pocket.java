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
	
	private int xRef = 150;
	private int yRef = 500;
	private int xVar = 50;
	
	private int limite;
	
	public Pocket(HudControler controller, Tapis tapis, Fenetre f){
		this.controller = controller;
		this.fenetre = f;
		this.tapis = tapis;
	}
	
	public void add(Piece p){
		
		double scale = 0.3;// TODO
		
		int x = this.xRef + this.xVar * this.pocket.size();
		int y = this.yRef;
		
		RectPiece r = (RectPiece) p.getRect();
		Point[] in = r.getCoins();
		java.awt.Point[] out = new java.awt.Point[4];
		Point c = p.getCentre();
		
		for(int i=0;i<4;i++){
			out[i] = new java.awt.Point();
			out[i].x = (int) Math.round( (in[i].getX() - c.getX()) * scale);
			out[i].x += x;
			out[i].y = (int) Math.round( (c.getY() - in[i].getY()) * scale);
			out[i].y += y;
		}
		java.awt.Point centre = new java.awt.Point(x,y);
		
		FreeBox b = new FreeBox(out,centre);
		PieceInPocket pi = new PieceInPocket(p,this.controller, b, scale);
		
		this.controller.addArea(pi);
		this.pocket.put(p,pi);
	}
	
	public void remove(Piece p){
//		this.controller.removeArea(this.pocket.get(p));
		this.pocket.remove(p);
		
	}
	
	
	public PieceInPocket get(Piece p){
		return this.pocket.get(p);
	}
	
}
