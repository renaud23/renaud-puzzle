package com.puzzle.view.zoomTapis;



import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.Tapis;
import com.puzzle.view.controller.TapisConverter;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.tool.ImageMemoryManager;
import com.puzzle.view.tool.JImageBuffer;
import com.puzzle.view.tool.provider.CompositeBufferOperation;
import com.puzzle.view.tool.provider.CompositeImageProvider;
import com.puzzle.view.tool.provider.PieceBufferOperation;
import com.puzzle.view.tool.provider.PieceLoader;
import com.renaud.manager.IRect;
import com.renaud.manager.Rect;



public class TapisZoomDrawer implements IDrawer,Observer{
	
	private JImageBuffer tapisBuffer;
	private Tapis tapis;
	private TapisConverter converter;
	private Image background;
//	private Thread thread;
	
	

	public TapisZoomDrawer(Image background,Tapis tapis,JImageBuffer tapisBuffer,
			TapisConverter converter) {
		this.tapisBuffer = tapisBuffer;
		this.tapis = tapis;
		this.converter = converter;
		this.background = background;
		PieceLoader.getInstance().addObserver(this);
		
//		this.thread = new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				draw();
//			}
//		});
//		
//		this.thread.start();
	}
	
//	@Override
//	public void draw(){
//		if(this.thread.getState() == State.TERMINATED){
//			this.thread = new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					drawEx();
//				}
//			});
//			this.thread.start();
//		}
//	}
	
	
	public void draw(){
		this.clean();
		
		TapisZoomConverteur cvt = (TapisZoomConverteur)this.converter;
		Rect r = new Rect(cvt.getCorner().getX(), cvt.getCorner().getY(), 
				cvt.getLargeur(), cvt.getHauteur());
		
		//	 dessin tapis
		List<CompositePiece> alreadyDraw = new ArrayList<CompositePiece>();
		
		// filtrage des pi�ce dans la zone.
		IRect rect = new Rect(cvt.getCorner().getX(), cvt.getCorner().getY(), cvt.getLargeur(), cvt.getHauteur());
		List<Piece> pieces = this.tapis.chercherPiece(rect);
		Collections.sort(pieces);
	
		for(Piece piece : pieces){
			
			if(piece.getComposite() == null){
				if(piece.getRect().isIn(r)){
					PieceBufferOperation pbo = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getElementDeferred(piece,this);
					if(pbo != null) this.drawPiece(pbo);	
				}// if in
			}else{
				if(!alreadyDraw.contains(piece.getComposite())){
					alreadyDraw.add(piece.getComposite());
					CompositePiece cmp = piece.getComposite();
					
					if(cmp.getRect().isIn(r)){
						CompositeBufferOperation sb =  CompositeImageProvider.getInstance().getElementDeferred(cmp, this);
						if(sb != null) this.drawComposite(sb);
					}// if isIn	
				}// if already
			}// else
			
		}// for
	}
	
	
	private void drawComposite(CompositeBufferOperation sb){
		synchronized (this.tapisBuffer) {
			JImageBuffer b = sb.getBuffer();
			double scale = sb.getScale();
			CompositePiece cmp = sb.getComposite();
			Point p = new Point(cmp.getCentre().getX(),cmp.getCentre().getY());
			this.converter.convertModelToScreen(p);
			
			double x = p.getX();
			x -= b.getImage().getWidth(null) / 2.0 * this.converter.getScaleX() / scale;
			
			double y = p.getY();
			y -= b.getImage().getHeight(null) / 2.0 * this.converter.getScaleY() / scale;
			
			this.tapisBuffer.drawImage(b.getImage(),
					x,  y, 
					p.getX() , p.getY(), -cmp.getAngle(), 
					this.converter.getScaleX()/scale, this.converter.getScaleY()/scale, 
					1.0f);
		}
	}
	
	
	private void drawPiece(PieceBufferOperation pbo){
		synchronized (this.tapisBuffer) {
			
			Point p = new Point(pbo.getPiece().getCentre().getX(),pbo.getPiece().getCentre().getY());
			this.converter.convertModelToScreen(p);
			
			double x = p.getX();
			x -= pbo.getPiece().getLargeur() / 2.0 * this.converter.getScaleX();
			
			double y = p.getY();
			y -= pbo.getPiece().getHauteur() / 2.0 * this.converter.getScaleY();
		
			this.tapisBuffer.drawImage(pbo.getImage(),
					x,  y, 
					p.getX() , p.getY(), -pbo.getPiece().getAngle(), 
					this.converter.getScaleX(), this.converter.getScaleY(), 
					1.0f);
		
		}
	}
	
	

	@Override
	public void clean() {
		synchronized (this.tapisBuffer) {
			double scalex = tapis.getLargeur() / background.getWidth(null);
			double scaley = tapis.getHauteur() / background.getHeight(null);
			
			double x = -this.tapis.getLargeur() / 2.0;
			x -= ((TapisZoomConverteur)converter).getCorner().getX();
			x *= converter.getScaleX();
			double y = -this.tapis.getHauteur() / 2.0;
			y += ((TapisZoomConverteur)converter).getCorner().getY();
			y *= converter.getScaleY();
			
			this.tapisBuffer.drawImage(this.background, 
					x, y, 
					0, 0, 0, 
					scalex*converter.getScaleX(), scaley*converter.getScaleY(), 1.0f);
		}
	}


	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof CompositeBufferOperation){
			this.drawComposite((CompositeBufferOperation)arg);

		}else if(arg instanceof PieceBufferOperation){
			this.drawPiece((PieceBufferOperation) arg);

		}
	}


}
