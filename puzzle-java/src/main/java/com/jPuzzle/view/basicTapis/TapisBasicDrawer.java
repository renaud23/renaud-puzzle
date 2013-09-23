package com.jPuzzle.view.basicTapis;

import com.jPuzzle.view.drawer.IDrawer;
import com.jPuzzle.view.drawer.Transformation;
import com.jPuzzle.view.image.Offscreen;
import com.puzzle.model.ComponentPiece;
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
		for(ComponentPiece cmp : this.tapis){
			// calcul le centre d'affichage à l'écran de la piéce en se basant sur le centre de IRect
			Transformation t = new Transformation();
			
			IRect r = cmp.getRect();
			
			
			// centre de la piece
			double x = r.getX();
			x += r.getLargeur() / 2.0;
			double y = r.getY();
			y -= r.getHauteur() / 2.0;
			
			
			Point p = new Point(x,y);
			TapisBasicConverter.getInstance().convertModelToScreen(p);
			
			t.setTx(p.getX());
			t.setTy(p.getY());
			
			t.setSx(TapisBasicConverter.getInstance().getScaleX());
			t.setSy(TapisBasicConverter.getInstance().getScaleY());
			
			((IDrawer) cmp).setTransformation(t);
			((IDrawer) cmp).draw();
		}
		
	}


	@Override
	public void setTransformation(Transformation transformation) {
		// TODO Auto-generated method stub
		
	}

}
