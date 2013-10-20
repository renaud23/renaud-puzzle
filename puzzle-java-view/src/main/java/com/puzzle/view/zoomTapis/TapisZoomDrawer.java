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
import com.puzzle.view.tool.CompositeImageManager;
import com.puzzle.view.tool.JImageBuffer;
import com.puzzle.view.tool.ImageMemoryManager;
import com.puzzle.view.tool.ScaleBuffer;
import com.renaud.manager.IRect;
import com.renaud.manager.Rect;



public class TapisZoomDrawer implements IDrawer,Observer{
	
	private JImageBuffer tapisBuffer;
	private Tapis tapis;
	private TapisConverter converter;
	private Image background;
	
	

	public TapisZoomDrawer(Image background,Tapis tapis,JImageBuffer tapisBuffer,
			TapisConverter converter) {
		this.tapisBuffer = tapisBuffer;
		this.tapis = tapis;
		this.converter = converter;
		
		this.background = background;
		
	}
	

	@Override
	public void draw() {
		this.clean();
		
		TapisZoomConverteur cvt = (TapisZoomConverteur)this.converter;
		Rect r = new Rect(cvt.getCorner().getX(), cvt.getCorner().getY(), 
				cvt.getLargeur(), cvt.getHauteur());
		
		//	 dessin tapis
		List<CompositePiece> alreadyDraw = new ArrayList<CompositePiece>();
		
		// filtrage des pièce dans la zone.
		IRect rect = new Rect(cvt.getCorner().getX(), cvt.getCorner().getY(), cvt.getLargeur(), cvt.getHauteur());
		List<Piece> pieces = this.tapis.chercherPiece(rect);
		Collections.sort(pieces);
	
		for(Piece piece : pieces){
			if(piece.getComposite() == null){
				if(piece.getRect().isIn(r)){
					Image img = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getImage(piece.getId());
					Point p = new Point(piece.getCentre().getX(),piece.getCentre().getY());
					this.converter.convertModelToScreen(p);
					
					double x = p.getX();
					x -= img.getWidth(null) / 2.0 * this.converter.getScaleX();
					
					double y = p.getY();
					y -= img.getHeight(null) / 2.0 * this.converter.getScaleY();
				
					this.tapisBuffer.drawImage(img,
							x,  y, 
							p.getX() , p.getY(), -piece.getAngle(), 
							this.converter.getScaleX(), this.converter.getScaleY(), 
							1.0f);
					
					
				}// if in
			}else{
				if(!alreadyDraw.contains(piece.getComposite())){
					alreadyDraw.add(piece.getComposite());
					CompositePiece cmp = piece.getComposite();
					
					if(cmp.getRect().isIn(r)){
						ScaleBuffer sb =  CompositeImageManager.getInstance().getBuffer(cmp);
						
						if(sb != null){
							this.drawComposite(sb);
						}// if != null
					}// if isIn	
				}// if already
			}// else				
		}// for
	}
	
	
	private void drawComposite(ScaleBuffer sb){
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
	
	

	@Override
	public void clean() {
	
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


	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof ScaleBuffer){
			
		}
		
	}


}
