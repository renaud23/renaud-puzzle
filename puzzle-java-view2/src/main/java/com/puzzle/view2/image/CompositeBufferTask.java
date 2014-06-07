package com.puzzle.view2.image;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.RectCompositePiece;
import com.puzzle.view2.image.tool.JImageBuffer;




public class CompositeBufferTask extends Observable implements Runnable{
	
	private CompositePiece composite;
	private double limite;
	private List<Piece> todo = new ArrayList<Piece>();




	public CompositeBufferTask(CompositePiece composite, double limite) {
		this.composite = composite;
		this.limite = limite;
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
		
		this.todo.addAll(this.composite.getPieces());
		Piece[] pieces = new Piece[this.todo.size()];
		this.todo.<Piece>toArray(pieces);
		
		while(!this.todo.isEmpty()){
			pieces = new Piece[this.todo.size()];
			this.todo.<Piece>toArray(pieces);

			for(Piece p : pieces){
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
		}
		
		ImageProvider.getInstance().PutImage(this.composite, buffer.getImage());
		
//		for(Piece p : composite){
//			double x = p.getPuzzleX();
//			x -= r.getPuzzX();
//			x -= p.getLargeur() / 2.0;
//			x *= scale;
//			double y = p.getPuzzleY();
//			y -= r.getPuzzY();
//			y -= p.getHauteur() / 2.0;
//			y *= scale;
//			this.drawPiece(buffer,p, x, y,scale);
//		}
		
		
		
		
	}
	

	
	
	
	private void drawPiece(JImageBuffer buffer,Piece piece,double x,double y,double scale){
		Image image = ImageProvider.getInstance().getImage(piece);
		
		if(image != null){
			buffer.drawImage(image, 
					x, y, 
					0, 0, 0,
					scale, scale, 1.0f);
			todo.remove(piece);
		}
	}

}
