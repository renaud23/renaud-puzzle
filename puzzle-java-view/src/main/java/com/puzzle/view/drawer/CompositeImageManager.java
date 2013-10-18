package com.puzzle.view.drawer;

import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.RectCompositePiece;
import com.puzzle.view.tool.ImageBuffer;
import com.puzzle.view.tool.ImageMemoryManager;

public class CompositeImageManager {
	private static CompositeImageManager instance;
	private double scale = 0.5;
	
	private Map<CompositePiece, ImageBuffer> buffers;
	
	public static CompositeImageManager getInstance(){
		if(instance == null) instance = new CompositeImageManager();
		return instance;
	}
	
	
	private CompositeImageManager(){
		this.buffers = new HashMap<CompositePiece, ImageBuffer>();
	}
	
	
	
	private void drawPiece(ImageBuffer buffer,Piece piece,double x,double y){
		Image img = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getImage(piece.getId());
		
		buffer.drawImage(img, 
				x, y, 
				0, 0, 0,
				scale, scale, 1.0f);
	}
	


	private ImageBuffer createbuffer(CompositePiece cmp){
		double l = cmp.getLargeur();
		l *= scale;
		double h = cmp.getHauteur();
		h *= scale;
		
		ImageBuffer buffer = new ImageBuffer(new Color(0,0,0,0),(int) Math.round(l), (int) Math.round(h));
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
	
	public ImageBuffer getBuffer(CompositePiece cmp){
		ImageBuffer b = this.buffers.get(cmp);
		if(b == null){
			b = this.createbuffer(cmp);
			this.buffers.put(cmp, b);
		}

		return b;
	}


	public double getScale() {
		return this.scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}
	
	
	
		
}
