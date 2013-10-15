package com.puzzle.view.zoomTapis;


import com.puzzle.model.Point;
import com.puzzle.view.Fenetre;
import com.puzzle.view.controller.IController;

public class LunetteController implements IController{

	
	private Lunette lunette;
	private TapisZoomConverteur converter;
	private DrawZoomSelection drawer;
	private Fenetre fenetre;
	
	private Point mousePosition;
	
	
	
	
	
	public LunetteController(Lunette lunette, TapisZoomConverteur converter,
			DrawZoomSelection drawer,Fenetre fenetre) {
		this.lunette = lunette;
		this.converter = converter;
		this.drawer = drawer;
		this.fenetre = fenetre;
		this.mousePosition = new Point();
	}
	
	private boolean isIn(int x,int y){
		boolean state = false;
		
		if(x >= lunette.getX() && x<=(lunette.getX()+lunette.getLargeur()) &&
				y >= lunette.getY() && y<=(lunette.getY()+lunette.getHauteur())){
			state = true;
		}
		return state;
	}

	@Override
	public void mouseEntered() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseLeftPressed(int x, int y) {
		if(this.isIn(x, y)){
//			 double ratio = this.lunette.getLargeur() / this.lunette.getTapis().getLargeur();
//			 
//			 double xi = x - this.lunette.getX();
//			 xi /= ratio;
//			 xi -= this.lunette.getTapis().getLargeur() / 2.0;
//			 double yi = y -  this.lunette.getY();
//			 yi /= ratio;
//			 yi *= -1.0;
//			 yi += this.lunette.getTapis().getHauteur() / 2.0;
//			 
//			 
//			 
//			 this.converter.getCorner().setX(xi);
//			 this.converter.getCorner().setY(yi);
//		
//
//			 this.drawer.clean();
//			 this.drawer.draw();
//			 this.fenetre.repaint();
		}
		
		this.mousePosition.setX(x);
		this.mousePosition.setY(y);
	}

	@Override
	public void mouseLeftReleased(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseRightPressed(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseRightReleased(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMove(int x, int y, boolean isShiftDown) {
		this.mousePosition.setX(x);
		this.mousePosition.setY(y);
	}

	@Override
	public void mouseDrag(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheel(boolean up) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyShiftPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyShiftReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyControlPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyControlReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controlPlusS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controlPlusL() {
		// TODO Auto-generated method stub
		
	}

}
