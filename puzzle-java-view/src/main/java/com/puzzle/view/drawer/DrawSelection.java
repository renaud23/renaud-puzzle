package com.puzzle.view.drawer;

import java.awt.Color;
import java.awt.Image;
import com.puzzle.controller.TapisConverter;
import com.puzzle.model.ComponentPiece;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.RectCompositePiece;
import com.puzzle.view.ImageBuffer;



public class DrawSelection implements IDrawerParametrable<Point>{
	private ImageBuffer buffer;
	private ImageBuffer selection;
	private ComponentPiece component;
	private TapisConverter converter;
	private Point mousePosition;


	public DrawSelection(ImageBuffer buffer, ComponentPiece component,
			TapisConverter converter) {
		this.buffer = buffer;
		this.component = component;
		this.converter = converter;
		
		this.createbuffer();
	}

	@Override
	public void draw() {
		this.buffer.transparentClean();
		double cx = (double)this.selection.getLargeur() / 2.0;
		double cy = (double)this.selection.getHauteur() / 2.0;
		double x = this.mousePosition.getX() - cx;
		double y = this.mousePosition.getY() - cy;
	
		this.buffer.drawImage(
				this.selection.getImage(), 
				x, y, 
				this.mousePosition.getX(), this.mousePosition.getY(), -this.component.getAngle(), 
				1.0, 1.0, 1.0f);
		
	}

	private void drawPiece(Piece piece,double x,double y){
		Image img = ImageMemoryManager.getInstance().getImage(piece.getId());
		
		this.selection.drawImage(img, 
				x, y, 
				0, 0, 0,
				this.converter.getScaleX(), this.converter.getScaleY(), 1.0f);
	}

	private void createbuffer(){
		double l = this.component.getLargeur();
		l *= this.converter.getScaleX();
		double h = this.component.getHauteur();
		h *= this.converter.getScaleY();
		this.selection = new ImageBuffer(new Color(0, 0, 0,0),(int) Math.round(l), (int) Math.round(h));
		this.selection.transparentClean();
		
		if(this.component instanceof Piece){
			Piece piece = (Piece)this.component;
			Image img = ImageMemoryManager.getInstance().getImage(piece.getId());
			
			this.selection.drawImage(img, 0, 0, 0, 0, 0, 
					this.converter.getScaleX(),this.converter.getScaleY(), 1.0f);
		}else if(this.component instanceof CompositePiece){
			CompositePiece composite = (CompositePiece) this.component;
			RectCompositePiece r =  (RectCompositePiece) composite.getRect();
			
			for(Piece p : composite){
				double x = p.getPuzzleX();
				x -= r.getPuzzX();
				x -= p.getLargeur() / 2.0;
				x *= this.converter.getScaleX();
				double y = p.getPuzzleY();
				y -= r.getPuzzY();
				y -= p.getHauteur() / 2.0;
				y *= this.converter.getScaleY();
				this.drawPiece(p, x, y);
			}
		}
	}
	
	


	@Override
	public void setParam(Point mousePosition) {
		this.mousePosition = mousePosition;
	}

	@Override
	public void clean() {
		this.buffer.transparentClean();
	}	
	
}
