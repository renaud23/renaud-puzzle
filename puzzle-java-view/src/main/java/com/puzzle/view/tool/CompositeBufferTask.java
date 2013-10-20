package com.puzzle.view.tool;

import java.awt.Color;
import java.awt.Image;
import java.util.Observable;

import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.RectCompositePiece;



public class CompositeBufferTask extends Observable implements Runnable{
	
	private CompositePiece composite;
	private double limite;




	public CompositeBufferTask(CompositePiece composite, double limite) {
		this.composite = composite;
		this.limite = limite;
		
		Thread t = new Thread(this);
		t.start();
	}


	@Override
	public void run() {
	
		double scale = 1.0;
		double l = composite.getLargeur();
		l *= scale;
		double h = composite.getHauteur();
		h *= scale;
		
		if(l > limite){
			scale = limite / composite.getLargeur();
			l =  composite.getLargeur() * scale;
			h = composite.getHauteur() * scale;
		}
		if(h > limite){
			scale = limite / composite.getHauteur();
			l =  composite.getLargeur() * scale;
			h = composite.getHauteur() * scale;
		}
		

		JImageBuffer buffer = new JImageBuffer(new Color(0,0,0,0),(int) Math.round(l), (int) Math.round(h));
		buffer.transparentClean();
		
		RectCompositePiece r =  (RectCompositePiece) composite.getRect();
		
		for(Piece p : composite){
			double x = p.getPuzzleX();
			x -= r.getPuzzX();
			x -= p.getLargeur() / 2.0;
			x *= scale;
			double y = p.getPuzzleY();
			y -= r.getPuzzY();
			y -= p.getHauteur() / 2.0;
			y *= scale;
			this.drawPiece(buffer,p, x, y,scale);
		}
		
		
		ScaleBuffer sc = new ScaleBuffer(scale, buffer);
		
		this.setChanged();
		this.notifyObservers(sc);
		
	}
	
	
	private void drawPiece(JImageBuffer buffer,Piece piece,double x,double y,double scale){
		Image img = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getImage(piece.getId());
		
		buffer.drawImage(img, 
				x, y, 
				0, 0, 0,
				scale, scale, 1.0f);
	}

}
