package com.puzzle.view.grid;

import java.awt.image.VolatileImage;

import com.puzzle.model.MyRect;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.RectPiece;
import com.puzzle.view.tool.ImageMemoryManager;
import com.puzzle.view.tool.provider.PieceBufferOperation;
import com.renaud.manager.IRect;

public class RectGridPiece implements MyRect{
	private RectPiece rp;
	
	private Piece p;
	
	private Grid g;
	
	public RectGridPiece(Piece p){
		this.p = p;
		this.rp = (RectPiece) p.getRect();
		
//		
	}

	@Override
	public boolean isIn(double x, double y, double largeur, double hauteur) {
		return this.rp.isIn(x, y, largeur, hauteur);
	}

	@Override
	public boolean isIn(IRect r) {
		return this.rp.isIn(r);
	}

	@Override
	public boolean contains(double x, double y) {
		boolean state = this.rp.contains(x, y);
		if(state){
			if(this.g == null) this.createGrid();
			else{
				double xi = x - this.p.getCentre().getX();
				double yi = y - this.p.getCentre().getY();
				Point p = new Point(xi, yi);
				p.tourner(this.p.getAngle()*-1.0);
				
				double xf = p.getX();
				xf += + this.p.getLargeur()/2.0;
				
				double yf = this.p.getHauteur() / 2.0 - p.getY();
				
				state = this.g.isIn(
						xf , 
						yf);
			}
		}
		return state;
	}

	@Override
	public double getX() {
		return this.rp.getX();
	}

	@Override
	public double getY() {
		return this.rp.getY();
	}

	@Override
	public double getLargeur() {
		return this.rp.getLargeur();
	}

	@Override
	public double getHauteur() {
		return this.rp.getHauteur();
	}

	@Override
	public void update() {
		this.rp.update();
		
	}

	@Override
	public Point[] getCoins() {
		return this.rp.getCoins();
	}
	
	private void createGrid(){
		PieceBufferOperation pbo = ImageMemoryManager.getInstance().get(this.p.getPuzzle().getId()).getElement(this.p);
		VolatileImage vi = (VolatileImage) pbo.getImage();
		this.g = new Grid(vi);
		
//		this.g.print(System.out);
//		System.out.println();
	}

}
