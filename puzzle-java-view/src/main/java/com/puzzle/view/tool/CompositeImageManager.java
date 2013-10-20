package com.puzzle.view.tool;

import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.RectCompositePiece;

public class CompositeImageManager implements Observer{
	private static CompositeImageManager instance;
	private double scale = 0.5;
	private static double limite = 2048.0;
	
	private Map<CompositePiece, CompositeBufferOperation> buffers;
	
	public static CompositeImageManager getInstance(){
		if(instance == null) instance = new CompositeImageManager();
		return instance;
	}
	
	
	private CompositeImageManager(){
		this.buffers = new HashMap<CompositePiece, CompositeBufferOperation>(); 
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
	
	public CompositeBufferOperation getBuffer(CompositePiece cmp){
		
		CompositeBufferOperation b = this.buffers.get(cmp);
		if(b == null){
			JImageBuffer bf = this.createbuffer(cmp);
			b = new  CompositeBufferOperation(scale, bf,cmp);
			this.buffers.put(cmp, b);
		}

		return b;
	}
	
	public CompositeBufferOperation getBufferDeferred(CompositePiece composite,Observer observer){
		
		CompositeBufferOperation b = this.buffers.get(composite);
		if(b == null){
			CompositeBufferTask task = new CompositeBufferTask(composite, limite);
			task.addObserver(observer);
			task.addObserver(this);
			task.start();
			
		}
		
		return b;
	}


	public double getScale() {
		return this.scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}


	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof CompositeBufferOperation){
			CompositeBufferOperation sb = (CompositeBufferOperation) arg;
			this.buffers.put(sb.getComposite(), sb);
			o.deleteObserver(this);
			
		}
		
	}
	
	

	
	
		
}
