package com.view.jPuzzle.view.draw;

import com.jPuzzle.view.Offscreen;
import com.puzzle.model.ComponentPiece;
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
		double scaleLargeur = this.offscreen.getLargeur();
		scaleLargeur /= this.tapis.getLargeur();
		double scaleHauteur = this.offscreen.getHauteur();
		scaleHauteur /= this.tapis.getHauteur();
		
		for(ComponentPiece cmp : this.tapis){
			
			
			Transformation t = new Transformation();
			
			IRect r = cmp.getRect();
			double x = r.getX();
			x += tapis.getLargeur() / 2.0;
			x += r.getLargeur() / 2.0;
			x *= scaleLargeur;
			
			double y = r.getY();
			y -= r.getHauteur() / 2.0;
			y *= -1.0;
			y += tapis.getHauteur() / 2.0;
			
			y *= scaleHauteur;
			
			t.setTx(x);
			t.setTy(y);
			
			t.setSx(scaleLargeur);
			t.setSy(scaleHauteur);
			
			((IDrawer) cmp).setTransformation(t);
			((IDrawer) cmp).draw();
		}
		
	}


	@Override
	public void setTransformation(Transformation transformation) {
		// TODO Auto-generated method stub
		
	}

}
