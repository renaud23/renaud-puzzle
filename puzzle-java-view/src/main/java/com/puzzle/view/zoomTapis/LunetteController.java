package com.puzzle.view.zoomTapis;


import com.puzzle.view.controller.IController;

public class LunetteController implements IController{

	
	private Lunette lunette;
	private TapisZoomConverteur converter;
	private DrawZoomSelection drawer;
	
	
	
	
	
	
	
	public LunetteController(Lunette lunette, TapisZoomConverteur converter,
			DrawZoomSelection drawer) {
		this.lunette = lunette;
		this.converter = converter;
		this.drawer = drawer;
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
		// TODO Auto-generated method stub
		
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
