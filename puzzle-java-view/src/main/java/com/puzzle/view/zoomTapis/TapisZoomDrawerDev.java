package com.puzzle.view.zoomTapis;

import java.awt.Color;
import java.awt.Image;
import java.util.Collections;
import java.util.List;import com.puzzle.model.CompositePiece;
import com.puzzle.model.MyRect;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.RectCompositePiece;
import com.puzzle.model.Tapis;
import com.puzzle.view.controller.TapisConverter;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.tool.JImageBuffer;
import com.renaud.manager.IRect;
import com.renaud.manager.Rect;

public class TapisZoomDrawerDev implements IDrawer {
	
	private Tapis tapis;
	private IDrawer drawer;
	private JImageBuffer tapisBuffer;
	private TapisConverter converter;

	
	
	/**
	 * pour le dev.
	 * @param background
	 * @param tapis
	 * @param tapisBuffer
	 * @param converter
	 */
	public TapisZoomDrawerDev(Image background,Tapis tapis,JImageBuffer tapisBuffer,
			TapisConverter converter){
		this.tapis = tapis;
		this.tapisBuffer = tapisBuffer;
		this.converter = converter;
		this.drawer = new TapisZoomDrawer(background, tapis, tapisBuffer, converter);
	}

	@Override
	public void draw() {
		this.drawer.draw();
		
		TapisZoomConverteur cvt = (TapisZoomConverteur)this.converter;
		
		
		// filtrage des pi�ce dans la zone.
		IRect rect = new Rect(cvt.getCorner().getX(), cvt.getCorner().getY(), cvt.getLargeur(), cvt.getHauteur());
		List<Piece> pieces = this.tapis.chercherPiece(rect);
		Collections.sort(pieces);
		
		for(Piece piece : this.tapis){
			// dev
			Point cp = new Point(piece.getCentre().getX(), piece.getCentre().getY());
			this.converter.convertModelToScreen(cp);
			this.tapisBuffer.drawRect(Color.green, (int)Math.round(cp.getX()), (int)Math.round(cp.getY()), 2, 2);
			
			Point[] pt = ((MyRect)piece.getRect()).getCoins();
			Point[] sc = new Point[4];
			for(int i=0;i<pt.length;i++){
				sc[i] = new Point(pt[i].getX(),pt[i].getY());
				this.converter.convertModelToScreen(sc[i]);
				this.tapisBuffer.drawRect(Color.red, (int)Math.round(sc[i].getX()), (int)Math.round(sc[i].getY()), 2, 2);
			}
			
			this.tapisBuffer.drawLine(Color.blue, sc[0].getX(), sc[0].getY(), sc[1].getX(), sc[1].getY());
			this.tapisBuffer.drawLine(Color.blue, sc[1].getX(), sc[1].getY(), sc[2].getX(), sc[2].getY());
			this.tapisBuffer.drawLine(Color.blue, sc[2].getX(), sc[2].getY(), sc[3].getX(), sc[3].getY());
			this.tapisBuffer.drawLine(Color.blue, sc[3].getX(), sc[3].getY(), sc[0].getX(), sc[0].getY());
			
			
			
			
			if(piece.getComposite() != null){
				CompositePiece cmp = piece.getComposite();
				// dev
				Point c = new Point(piece.getComposite().getCentre().getX(), piece.getComposite().getCentre().getY());
				this.converter.convertModelToScreen(c);
				this.tapisBuffer.drawRect(Color.yellow, (int)Math.round(c.getX()), (int)Math.round(c.getY()), 2, 2);
				
				Point[] pt1 = ((RectCompositePiece)cmp.getRect()).getCoins();
				Point[] sc1 = new Point[4];
				for(int i=0;i<pt.length;i++){
					sc1[i] = new Point(pt1[i].getX(),pt1[i].getY());
					this.converter.convertModelToScreen(sc1[i]);
					this.tapisBuffer.drawRect(Color.yellow, (int)Math.round(sc1[i].getX()), (int)Math.round(sc1[i].getY()), 2, 2);
				}
				
				this.tapisBuffer.drawLine(Color.yellow, sc1[0].getX(), sc1[0].getY(), sc1[1].getX(), sc1[1].getY());
				this.tapisBuffer.drawLine(Color.yellow, sc1[1].getX(), sc1[1].getY(), sc1[2].getX(), sc1[2].getY());
				this.tapisBuffer.drawLine(Color.yellow, sc1[2].getX(), sc1[2].getY(), sc1[3].getX(), sc1[3].getY());
				this.tapisBuffer.drawLine(Color.yellow, sc1[3].getX(), sc1[3].getY(), sc1[0].getX(), sc1[0].getY());
				
			}// if
			
			
			
		}// for
	}

	@Override
	public void clean() {
		this.drawer.clean();
	}
}
