package com.puzzle.view.tool;

import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.RectCompositePiece;

public class CompositeImageManager{
	private static CompositeImageManager instance;
	private double scale = 0.5;
	private static double limite = 2048.0;
	
	private Map<CompositePiece, ScaleBuffer> buffers;
	
	public static CompositeImageManager getInstance(){
		if(instance == null) instance = new CompositeImageManager();
		return instance;
	}
	
	
	private CompositeImageManager(){
		this.buffers = new HashMap<CompositePiece, ScaleBuffer>(); 
	}
	
	
	
	private void drawPiece(JImageBuffer buffer,Piece piece,double x,double y){
		Image img = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getImage(piece.getId());
		
		buffer.drawImage(img, 
				x, y, 
				0, 0, 0,
				scale, scale, 1.0f);
	}
	


	private JImageBuffer createbuffer(CompositePiece cmp){
		this.scale = 1.0;
		double l = cmp.getLargeur();
		l *= scale;
		double h = cmp.getHauteur();
		h *= scale;
		
		if(l > limite){
			scale = limite / cmp.getLargeur();
			l =  cmp.getLargeur() * scale;
			h = cmp.getHauteur() * scale;
		}
		if(h > limite){
			scale = limite / cmp.getHauteur();
			l =  cmp.getLargeur() * scale;
			h = cmp.getHauteur() * scale;
		}
		

		JImageBuffer buffer = new JImageBuffer(new Color(0,0,0,0),(int) Math.round(l), (int) Math.round(h));
		buffer.transparentClean();
		
		RectCompositePiece r =  (RectCompositePiece) cmp.getRect();
		
		for(Piece p : cmp){
			double x = p.getPuzzleX();
			x -= r.getPuzzX();
			x -= p.getLargeur() / 2.0;
			x *= scale;
			double y = p.getPuzzleY();
			y -= r.getPuzzY();
			y -= p.getHauteur() / 2.0;
			y *= scale;
			this.drawPiece(buffer,p, x, y);
		}
		
		return buffer;
	}
	
	public void removeBuffer(CompositePiece cmp){
		this.buffers.remove(cmp); 
	}
	
	public ScaleBuffer getBuffer(CompositePiece cmp){
		
		ScaleBuffer b = this.buffers.get(cmp);
		if(b == null){
			JImageBuffer bf = this.createbuffer(cmp);
			b = new  ScaleBuffer(scale, bf);
			this.buffers.put(cmp, b);
		}

		return b;
	}
	
	public void getBufferDeferred(CompositePiece cmp,Observable observer){
		
		ScaleBuffer b = this.buffers.get(cmp);
		if(b == null){
			JImageBuffer bf = this.createbuffer(cmp);
			b = new  ScaleBuffer(scale, bf);
			this.buffers.put(cmp, b);
		}

		
	}


	public double getScale() {
		return this.scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}
	
	
//	public class ScaleBuffer{
//		/**
//		 * réduction apportée à l'image.
//		 */
//		private double scale;
//		private JImageBuffer buffer;
//		
//		
//		public ScaleBuffer(double scale, JImageBuffer buffer) {
//			this.scale = scale;
//			this.buffer = buffer;
//		}
//		public double getScale() {
//			return scale;
//		}
//		public JImageBuffer getBuffer() {
//			return buffer;
//		}
//		
//		
//	}
	
	
		
}
