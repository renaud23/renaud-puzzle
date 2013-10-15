package com.puzzle.view.zoomTapis;

import com.puzzle.model.Point;
import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.controller.AbstractTapisController;
import com.puzzle.view.controller.MyMouseMotionListener;

public class TapisZoomController extends AbstractTapisController{

	public TapisZoomController(Fenetre fenetre, Tapis tapis) {
		super(fenetre, tapis);
		
		this.selectionDrawer = new DrawZoomSelection(this.fenetre.getBuffer(1), (TapisZoomConverteur) this.converter,this.tapis);
		this.selectionDrawer.setParam(this.selectionParam);
		this.fenetre.getOffscreen().addMouseMotionListener(
				new MyMouseMotionListener(
					new LunetteController(
							tapis,
							(TapisZoomConverteur)this.converter,
							(DrawZoomSelection)this.selectionDrawer)));
		
		this.tapisDrawer.draw();
		this.selectionDrawer.draw();
		this.fenetre.repaint();
	}

	
	private void zoom(boolean up){
		((TapisZoomConverteur)this.converter).zoom(up);
		
		((DrawZoomSelection)this.selectionDrawer).setZoomScale(this.converter.getScaleX());
		
		this.selectionDrawer.clean();
		this.selectionDrawer.draw();
		
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
	
	
	protected void move(double vx,double vy){
		Point p = new Point(vx,vy);
		
		((TapisZoomConverteur)this.converter).moveTo(p);
		
		if(!this.mainDroiteVide){
			this.selectionParam.setPosition(new Point(this.mousePosition.getX(),this.mousePosition.getY()));
			
		}
		
		this.selectionDrawer.clean();
		this.selectionDrawer.draw();
		this.tapisDrawer.draw();
		this.fenetre.repaint();
	}
}
