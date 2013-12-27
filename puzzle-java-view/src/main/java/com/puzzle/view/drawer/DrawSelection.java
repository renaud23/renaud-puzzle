package com.puzzle.view.drawer;

import java.awt.Color;
import java.awt.Image;
import java.util.Observable;
import java.util.Observer;

import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.view.Fenetre;
import com.puzzle.view.controller.TapisConverter;
import com.puzzle.view.tool.ImageMemoryManager;
import com.puzzle.view.tool.JImageBuffer;
import com.puzzle.view.tool.provider.CompositeBufferOperation;
import com.puzzle.view.tool.provider.CompositeImageProvider;
import com.puzzle.view.tool.provider.PieceBufferOperation;



public class DrawSelection implements IDrawerSelection,Observer{
	private JImageBuffer buffer;
	private TapisConverter converter;
	private DrawSelectionParam param;
	private boolean selection;
	private double zoom;
	
	


	public DrawSelection(JImageBuffer buffer, TapisConverter converter) {
		this.buffer = buffer;
		this.converter = converter;
		this.selection = false;
		this.zoom = 1.0;
	}

	

	@Override
	public void draw() {
		this.clean();
		// les candidats
		for(Piece piece : this.param.getCandidats()){
			PieceBufferOperation pbo = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getElement(piece);
			Point p = new Point(piece.getCentre().getX(),piece.getCentre().getY());
			this.converter.convertModelToScreen(p);
			
			double xi = p.getX();
			xi -= pbo.getImage().getWidth(null) / 2.0 * this.converter.getScaleX();
			
			double yi = p.getY();
			yi -= pbo.getImage().getHeight(null) / 2.0 * this.converter.getScaleY();
		
			this.buffer.drawImageMask(pbo.getImage(),
					xi,  yi, 
					p.getX() , p.getY(), -piece.getAngle(), 
					this.converter.getScaleX(), this.converter.getScaleY(), 
					Color.yellow);
		}
		
		// la selection
		if(this.selection){
			if(this.param.getComponent() instanceof Piece){
				Piece piece = (Piece) this.param.getComponent();
				PieceBufferOperation pbo = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getElement(piece);
				
				double cx = (double)pbo.getImage().getWidth(null) / 2.0 * this.converter.getScaleX() * this.zoom;
				double cy = (double)pbo.getImage().getHeight(null) / 2.0 * this.converter.getScaleY() * this.zoom;
				
				double x = this.param.getPosition().getX();
				double y = this.param.getPosition().getY();
				x += this.param.getAncre().getX() * this.converter.getScaleX() * this.zoom;
				y -= this.param.getAncre().getY() * this.converter.getScaleY() * this.zoom;
				x -= cx;
				y -= cy;
		
				this.buffer.drawImage(
						pbo.getImage(), 
					x,y, 
					this.param.getPosition().getX(), this.param.getPosition().getY(), -this.param.getComponent().getAngle(), 
					this.converter.getScaleX()*this.zoom, this.converter.getScaleY()*this.zoom, 1.0f);
			}else if(this.param.getComponent() instanceof CompositePiece){
				CompositeBufferOperation sb = CompositeImageProvider.getInstance().getElement((CompositePiece)this.param.getComponent());
				Image img = sb.getBuffer().getImage();
				double scale = sb.getScale();
				double cx = (double)img.getWidth(null) / 2.0 * this.converter.getScaleX()/scale * this.zoom;
				double cy = (double)img.getHeight(null) / 2.0 * this.converter.getScaleY()/scale * this.zoom;
				
				double x = this.param.getPosition().getX();
				double y = this.param.getPosition().getY();
				x += this.param.getAncre().getX() * this.converter.getScaleX() * this.zoom;
				y -= this.param.getAncre().getY() * this.converter.getScaleY() * this.zoom;
				x -= cx;
				y -= cy;
				
				this.buffer.drawImage(
					img, 
					x,y, 
					this.param.getPosition().getX(), this.param.getPosition().getY(), -this.param.getComponent().getAngle(), 
					this.converter.getScaleX()/scale*this.zoom, this.converter.getScaleY()/scale*this.zoom, 1.0f);
			}
		
		}
		//

	}

	
	
	


	public void setBuffer(JImageBuffer buffer) {
		this.buffer = buffer;
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



	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Fenetre){
			Fenetre f = (Fenetre)o;
			this.buffer = f.getBuffer(1);
			
			this.draw();
		}
		
	}



	@Override
	public void setZoom(double zoom) {
		this.zoom = zoom;
	}



	
}
