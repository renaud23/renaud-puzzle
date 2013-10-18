package com.puzzle.view.drawer;

import java.awt.Color;
import java.awt.Image;

import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.view.controller.TapisConverter;
import com.puzzle.view.tool.CompositeImageManager;
import com.puzzle.view.tool.JImageBuffer;
import com.puzzle.view.tool.ImageMemoryManager;
import com.puzzle.view.tool.CompositeImageManager.ScaleBuffer;



public class DrawSelection implements IDrawerSelection{
	private JImageBuffer buffer;
	private TapisConverter converter;
	private DrawSelectionParam param;
	private boolean selection;
	
	


	public DrawSelection(JImageBuffer buffer, TapisConverter converter) {
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
		if(this.selection){
			if(this.param.getComponent() instanceof Piece){
				Piece piece = (Piece) this.param.getComponent();
				Image img = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getImage(piece.getId());
				
				double cx = (double)img.getWidth(null) / 2.0 * this.converter.getScaleX();
				double cy = (double)img.getHeight(null) / 2.0 * this.converter.getScaleY();
				
				double x = this.param.getPosition().getX();
				double y = this.param.getPosition().getY();
				x += this.param.getAncre().getX() * this.converter.getScaleX();
				y -= this.param.getAncre().getY() * this.converter.getScaleY();
				x -= cx;
				y -= cy;
				
				this.buffer.drawImage(
					img, 
					x,y, 
					this.param.getPosition().getX(), this.param.getPosition().getY(), -this.param.getComponent().getAngle(), 
					this.converter.getScaleX(), this.converter.getScaleY(), 1.0f);
			}else if(this.param.getComponent() instanceof CompositePiece){
				ScaleBuffer sb = CompositeImageManager.getInstance().getBuffer((CompositePiece)this.param.getComponent());
				Image img = sb.getBuffer().getImage();
				double scale = sb.getScale();
				double cx = (double)img.getWidth(null) / 2.0 * this.converter.getScaleX()/scale;
				double cy = (double)img.getHeight(null) / 2.0 * this.converter.getScaleY()/scale;
				
				double x = this.param.getPosition().getX();
				double y = this.param.getPosition().getY();
				x += this.param.getAncre().getX() * this.converter.getScaleX();
				y -= this.param.getAncre().getY() * this.converter.getScaleY();
				x -= cx;
				y -= cy;
				
				this.buffer.drawImage(
					img, 
					x,y, 
					this.param.getPosition().getX(), this.param.getPosition().getY(), -this.param.getComponent().getAngle(), 
					this.converter.getScaleX()/scale, this.converter.getScaleY()/scale, 1.0f);
			}
		
		}
		//

	}

	
	
	


	public boolean isSelection() {
		return selection;
	}

	public void setSelection(boolean selection) {
		this.selection = selection;
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
