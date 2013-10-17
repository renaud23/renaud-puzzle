package com.puzzle.view.drawer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.text.DecimalFormat;

import com.puzzle.model.ComponentPiece;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.RectCompositePiece;
import com.puzzle.view.controller.TapisConverter;
import com.puzzle.view.tool.ImageBuffer;
import com.puzzle.view.tool.ImageMemoryManager;



public class DrawSelection implements IDrawerSelection{
	private ImageBuffer buffer;
	private ImageBuffer selectionBuffer;
	private TapisConverter converter;
	private DrawSelectionParam param;
	private boolean selection;
	
	


	public DrawSelection(ImageBuffer buffer, TapisConverter converter) {
		this.buffer = buffer;
		this.converter = converter;
		this.selection = false;
	}

	@Override
	public void draw() {
		// les candidats
		for(Piece piece : this.param.getCandidats()){
			Image img = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getImage(piece.getId());
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
		if(this.selection && selectionBuffer != null){
			double cx = (double)this.selectionBuffer.getLargeur() / 2.0;
			double cy = (double)this.selectionBuffer.getHauteur() / 2.0;
			
			double x = this.param.getPosition().getX();
			double y = this.param.getPosition().getY();
			x += this.param.getAncre().getX() * this.converter.getScaleX();
			y -= this.param.getAncre().getY() * this.converter.getScaleY();
			x -= cx;
			y -= cy;
			
			this.buffer.drawImage(
				this.selectionBuffer.getImage(), 
				x,y, 
				this.param.getPosition().getX(), this.param.getPosition().getY(), -this.param.getComponent().getAngle(), 
				1.0, 1.0, 1.0f);
		
		}

		//

		
	}

	private void drawPiece(Piece piece,double x,double y){
		Image img = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getImage(piece.getId());
		if(this.selectionBuffer != null)
			this.selectionBuffer.drawImage(img, 
					x, y, 
					0, 0, 0,
					this.converter.getScaleX(), this.converter.getScaleY(), 1.0f);
	}

	public void createbuffer(){
		double l = this.param.getComponent().getLargeur();
		l *= this.converter.getScaleX();
		double h = this.param.getComponent().getHauteur();
		h *= this.converter.getScaleY();
		
		this.selectionBuffer = new ImageBuffer(new Color(0, 0,0,0),(int) Math.round(l), (int) Math.round(h));
		this.selectionBuffer.transparentClean();
		
		if(this.param.getComponent() instanceof Piece){
			Piece piece = (Piece)this.param.getComponent();
			Image img = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getImage(piece.getId());
			
			this.selectionBuffer.drawImage(img, 0, 0, 0, 0, 0, 
					this.converter.getScaleX(),this.converter.getScaleY(), 1.0f);
		}else if(this.param.getComponent() instanceof CompositePiece){
			CompositePiece composite = (CompositePiece) this.param.getComponent();
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
			
//			this.selectionBuffer = CompositeImageManager.getInstance().getBuffer(composite);
		}
	}
	
	


	public boolean isSelection() {
		return selection;
	}

	public void setSelection(boolean selection) {
		this.selection = selection;
		this.selectionBuffer = null;
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
