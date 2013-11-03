package com.jPuzzle.view.drawer;

import java.awt.Color;
import java.awt.Image;
import com.jPuzzle.view.basicTapis.TapisBasicConverter;
import com.jPuzzle.view.image.ImageBuffer;
import com.jPuzzle.view.image.ImageMemoryManager;
import com.puzzle.model.ComponentPiece;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Piece;
import com.puzzle.model.RectCompositePiece;

public class DrawSelection implements IDrawer{
	private ImageBuffer buffer;
	private ComponentPiece component;


	@Override
	public void draw() {
		this.component = MainDroite.getInstance().getContenu();
		
		if(component != null){
			
			this.createbuffer();
			
			if(component instanceof Piece){
				Piece piece = (Piece) component;
				this.drawPiece(piece, 0, 0);
				
			}else if(component instanceof ComponentPiece){
				CompositePiece composite = (CompositePiece) this.component;
				RectCompositePiece r = (RectCompositePiece) composite.getRect();
				
				for(Piece p : composite){
					double x = p.getPuzzleX();
					x -= r.getPuzzX();
					x -= p.getLargeur() / 2.0;
					x *= TapisBasicConverter.getInstance().getScaleX();
					double y = p.getPuzzleY();
					y -= r.getPuzzY();
					y -= p.getHauteur() / 2.0;
					y *= TapisBasicConverter.getInstance().getScaleY();
					this.drawPiece(p, x, y);
				}
				
			}
		}
		
	}

	private void drawPiece(Piece piece,double x,double y){
		Image img = ImageMemoryManager.getInstance().getImage(piece.getId());
		
		this.buffer.drawImage(img, 
				x, y, 
				0, 0, 0,
				TapisBasicConverter.getInstance().getScaleX(), TapisBasicConverter.getInstance().getScaleY(), 1.0f);
	}

	private void createbuffer(){
		double l = this.component.getLargeur();
		l *= TapisBasicConverter.getInstance().getScaleX();
		double h = this.component.getHauteur();
		h *= TapisBasicConverter.getInstance().getScaleY();
		this.buffer = new ImageBuffer(new Color(0, 0, 0,0),(int) Math.round(l), (int) Math.round(h));
		this.buffer.transparentClean();
	}
	
	
	
	
	
	public ImageBuffer getBuffer() {
		return buffer;
	}





	
	
}
