package com.jPuzzle.view.basicTapis;

import com.jPuzzle.view.drawer.IDrawer;
import com.jPuzzle.view.drawer.PieceDrawer;
import com.jPuzzle.view.drawer.Transformation;
import com.jPuzzle.view.image.Offscreen;
import com.puzzle.model.ComponentPiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.Tapis;
import com.renaud.manager.IRect;

public class TapisBasicDrawer implements IDrawer {
	
	private Tapis tapis;
	private Offscreen offscreen;
	

	public TapisBasicDrawer(Tapis tapis, Offscreen offscreen) {
		this.tapis = tapis;
		this.offscreen = offscreen;
	}


	@Override
	public void draw() {
		
		
		for(Piece cmp : this.tapis){
			IDrawer pd = new PieceDrawer(cmp, this.offscreen);
			
			// calcul le centre d'affichage à l'écran de la piéce en se basant sur le centre de IRect
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
