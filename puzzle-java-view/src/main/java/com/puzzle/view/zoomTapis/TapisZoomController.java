package com.puzzle.view.zoomTapis;

import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.controller.AbstractTapisController;

public class TapisZoomController extends AbstractTapisController{

	public TapisZoomController(Fenetre fenetre, Tapis tapis) {
		super(fenetre, tapis);
	}

	
	private void zoom(boolean up){
		((TapisZoomConverteur)this.converter).zoom(up);
		
		this.tapisDrawer.draw();
		this.fenetre.repaint();
	}
	
	@Override
	public void mouseWheel(boolean up) {
		if(this.mainDroiteVide){
			this.zoom(up);
		}else super.mouseWheel(up);
	}
	
	@Override
	public void mouseDrag(int x, int y) {
		if(this.rightClick){
			double vx = this.mousePosition.getX() - x;
			double vy = this.mousePosition.getY() - y;
			this.mousePosition.setX(x);
			this.mousePosition.setY(y);
			
			this.move(vx, vy);
		}
	}
}
