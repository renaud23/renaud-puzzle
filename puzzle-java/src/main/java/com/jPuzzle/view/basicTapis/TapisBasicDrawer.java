package com.jPuzzle.view.basicTapis;

import com.jPuzzle.view.drawer.IDrawer;
import com.jPuzzle.view.drawer.PieceDrawer;
import com.jPuzzle.view.drawer.Transformation;
import com.jPuzzle.view.image.ImageBuffer;
import com.jPuzzle.view.image.Offscreen;
import com.puzzle.model.ComponentPiece;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.Tapis;


public class TapisBasicDrawer implements IDrawer {
	
	private Tapis tapis;
	private ImageBuffer tapisBuffer;


	public TapisBasicDrawer(Tapis tapis, ImageBuffer tapisBuffer) {
		this.tapis = tapis;
		this.tapisBuffer = tapisBuffer;
	
	}


	@Override
	public void draw() {
		this.tapisBuffer.clean();
		
		//	 dessin tapis
		for(Piece cmp : this.tapis){
			IDrawer pd = new PieceDrawer(cmp, this.tapisBuffer);
			
			// calcul le centre d'affichage � l'�cran de la pi�ce en se basant sur le centre de IRect
			Transformation t = new Transformation();
			
			Point p = new Point(cmp.getCentre().getX(),cmp.getCentre().getY());
			TapisBasicConverter.getInstance().convertModelToScreen(p);
			
			t.setTx(p.getX());
			t.setTy(p.getY());
			
			t.setSx(TapisBasicConverter.getInstance().getScaleX());
			t.setSy(TapisBasicConverter.getInstance().getScaleY());
			
			pd.setTransformation(t);
			pd.draw();
		}
	}
	



	@Override
	public void setTransformation(Transformation transformation) {
		// TODO Auto-generated method stub
		
	}

}