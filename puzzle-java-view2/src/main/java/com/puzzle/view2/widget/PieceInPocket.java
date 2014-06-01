package com.puzzle.view2.widget;


import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.view2.controller.ControllerAdaptater;
import com.puzzle.view2.layer.Pocket;


public class PieceInPocket extends ControllerAdaptater implements Widget{
	private Piece piece;
	private Pocket pocket;
	private int x;
	private int y;
	
	private double scale;
	
	private Point coins[];
	private Point tmp = new Point();

	
	
	
	
	
	public PieceInPocket(Pocket pocket,Piece piece, int x, int y, double scale) {
		this.piece = piece;
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.pocket = pocket;
		
		this.init();
	}



	/*
	 * 
	 */
	
	public void init(){
		this.coins = new Point[4];
		double cx = this.x + this.piece.getLargeur() * this.scale / 2.0;
		double cy = this.y + this.piece.getHauteur()  * this.scale / 2.0;
		double pl = this.piece.getLargeur() * this.scale;
		double ph = this.piece.getHauteur() * this.scale;
		this.coins[0] = new Point(this.x, this.y);
		this.coins[1] = new Point(this.x + pl, this.y); 
		this.coins[2] = new Point(this.x + pl, this.y + ph); 
		this.coins[3] = new Point(this.x, this.y + ph); 
		this.coins[0].tourner(-this.piece.getAngle(), cx, cy);
		this.coins[1].tourner(-this.piece.getAngle(), cx, cy);
		this.coins[2].tourner(-this.piece.getAngle(), cx, cy);
		this.coins[3].tourner(-this.piece.getAngle(), cx, cy);
		
		int minx=Integer.MAX_VALUE,maxx=Integer.MIN_VALUE, miny=Integer.MAX_VALUE, maxy=Integer.MIN_VALUE;
		
		for(int i=0;i<4;i++){
			minx = (int) Math.min(minx, coins[i].getX());
			maxx = (int) Math.max(maxx, coins[i].getX());
			miny = (int) Math.min(miny, coins[i].getY());
			maxy = (int) Math.max(maxy, coins[i].getY());
		}
		this.rectangle = new Rectangle(minx,miny,maxx - minx,maxy - miny);
	}
	
	
	@Override
	public boolean contains(int x, int y) {
		tmp.setX(x);
		tmp.setY(y);
		boolean in = 
				tmp.IsInsideTriangle(this.coins[0], this.coins[1], this.coins[2]) ||
				tmp.IsInsideTriangle(this.coins[2], this.coins[3], this.coins[0]);

		return in;
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		if(MainDroite.getInstance().isEmpty()) 
			this.pocket.setFocused(this);
	}


	@Override
	public void mouseExited(MouseEvent e) {
		if(this.pocket.getFocused() == this)
			this.pocket.setFocused(null);
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		this.pocket.pick(this);
	}





	public Piece getPiece() {
		return piece;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	
}
