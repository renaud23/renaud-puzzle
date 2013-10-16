package com.puzzle.view.zoomTapis;

import java.awt.Image;

import javax.swing.SwingUtilities;

import com.puzzle.model.Point;
import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.RepaintTask;
import com.puzzle.view.controller.AbstractTapisController;
import com.puzzle.view.controller.MyMouseListener;
import com.puzzle.view.controller.MyMouseMotionListener;

public class TapisZoomController extends AbstractTapisController{

	public TapisZoomController(Image background,Fenetre fenetre, Tapis tapis) {
		super(fenetre, tapis);
		this.converter = new TapisZoomConverteur(fenetre.getOffscreen(),tapis);
		this.tapisDrawer = new TapisZoomDrawer(background,tapis,fenetre.getBuffer(0),this.converter);
		
		
		Lunette lunette = new Lunette();
		lunette.setTapis(tapis);
		lunette.setScale(0.2);
		lunette.setLargeur(this.fenetre.getBuffer(1).getLargeur() * lunette.getScale());
		lunette.setHauteur(lunette.getLargeur() * tapis.getHauteur() / tapis.getLargeur());
		lunette.setX(this.fenetre.getBuffer(1).getLargeur() - lunette.getLargeur() - 10.0);
		lunette.setY(10.0);
		
		this.selectionDrawer = new DrawZoomSelection(this.fenetre.getBuffer(1), (TapisZoomConverteur) this.converter,lunette);
		this.selectionDrawer.setParam(this.selectionParam);
		
		LunetteController lc = new LunetteController(lunette, (TapisZoomConverteur)converter, (DrawZoomSelection)this.selectionDrawer,this.fenetre);
		this.fenetre.getOffscreen().addMouseMotionListener(new MyMouseMotionListener(lc));
		this.fenetre.getOffscreen().addMouseListener(new MyMouseListener(lc));
		

		this.tapisDrawer.draw();
		this.selectionDrawer.draw();
		SwingUtilities.invokeLater(new RepaintTask(this.fenetre));
	}

	
	private void zoom(boolean up){
		((TapisZoomConverteur)this.converter).zoom(up);
		
		((DrawZoomSelection)this.selectionDrawer).setZoomScale(this.converter.getScaleX());
		
		this.selectionDrawer.clean();
		this.selectionDrawer.draw();
//		new DrawTask(this.tapisDrawer,this.fenetre);
		this.tapisDrawer.draw();
		
		SwingUtilities.invokeLater(new RepaintTask(this.fenetre));
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
		
		((TapisZoomConverteur)this.converter).moveBy(p);
		
		if(!this.mainDroiteVide){
			this.selectionParam.setPosition(new Point(this.mousePosition.getX(),this.mousePosition.getY()));
			
		}
		
		this.selectionDrawer.clean();
		this.selectionDrawer.draw();
		
//		new DrawTask(this.tapisDrawer, fenetre);

		this.tapisDrawer.draw();
		
		SwingUtilities.invokeLater(new RepaintTask(this.fenetre));
	}
}
