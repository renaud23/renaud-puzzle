package com.puzzle.view.core.image;

import java.awt.Color;
import java.util.Observable;

import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.RectCompositePiece;
import com.puzzle.view.core.IDrawer;
import com.puzzle.view.java.JavaBuffer;



public class CompositeBufferTask extends Observable implements Runnable{
	
	private CompositePiece composite;
	private double limite;
	private Thread task;
	private CompositeBufferOperation operation;



	public CompositeBufferTask(CompositePiece composite, double limite) {
		this.composite = composite;
		this.limite = limite;
		
		this.task = new Thread(this);
	}
	
	public void start(){
		this.task.start();
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
		

		IDrawer buffer = new JavaBuffer(new Color(0,0,0,0),(int) Math.round(l), (int) Math.round(h));
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
		
		
		this.operation = new CompositeBufferOperation(scale, buffer,composite);
		
		this.setChanged();
		this.notifyObservers(operation);
		
	}
	

	public CompositeBufferOperation getOperation() {
		return operation;
	}
	
	
	private void drawPiece(IDrawer buffer,Piece piece,double x,double y,double scale){
		PieceBufferOperation pbo = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getElement(piece);
		
		buffer.drawImage(pbo.getImage(), 
				x, y, 
				0, 0, 0,
				scale, scale, 1.0f);
	}

}
