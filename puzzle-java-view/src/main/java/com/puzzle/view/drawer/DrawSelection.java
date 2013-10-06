package com.puzzle.view.drawer;

import java.awt.Color;
import java.awt.Image;import com.puzzle.model.ComponentPiece;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.RectCompositePiece;
import com.puzzle.view.ImageBuffer;
import com.puzzle.view.controller.TapisConverter;



public class DrawSelection implements IDrawerParametrable<DrawSelectionParam>{
	private ImageBuffer buffer;
	private ImageBuffer selection;
	private ComponentPiece component;
	private TapisConverter converter;
	private DrawSelectionParam param;
	


	public DrawSelection(ImageBuffer buffer, ComponentPiece component,
			TapisConverter converter) {
		this.buffer = buffer;
		this.component = component;
		this.converter = converter;
		
		this.createbuffer();
	}

	@Override
	public void draw() {
//		this.buffer.transparentClean();
		// les candidats
		for(Piece piece : this.param.getCandidats()){
			Image img = ImageMemoryManager.getInstance().getImage(piece.getId());
			Point p = new Point(piece.getCentre().getX(),piece.getCentre().getY());
			this.converter.convertModelToScreen(p);
			
			double xi = p.getX();
			xi -= img.getWidth(null) / 2.0 * this.converter.getScaleX();
			
			double yi = p.getY();
			yi -= img.getHeight(null) / 2.0 * this.converter.getScaleY();
		
			this.buffer.drawImageMask(img,
					xi,  yi, 
					p.getX() , p.getY(), -piece.getAngle(), 
					this.converter.getScaleX(), this.converter.getScaleY(), 
					Color.yellow);
		}
		// la selection
		double cx = (double)this.selection.getLargeur() / 2.0;
		double cy = (double)this.selection.getHauteur() / 2.0;
		double x = this.param.getPosition().getX() - cx;
		double y = this.param.getPosition().getY() - cy;
		
		
		this.buffer.drawImage(
				this.selection.getImage(), 
				x, y, 
				this.param.getPosition().getX(), this.param.getPosition().getY(), -this.component.getAngle(), 
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
		
		this.selection = new ImageBuffer(new Color(0, 0,0,0),(int) Math.round(l), (int) Math.round(h));
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
	public void setParam(DrawSelectionParam param) {
		this.param = param;
	}

	@Override
	public void clean() {
		this.buffer.transparentClean();
	}	
	
}
