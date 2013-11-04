package com.puzzle.view.hud;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import com.puzzle.model.Tapis;
import com.puzzle.view.controller.IController;


public class HudControler implements IController,Observer{
	private IController controller;
	private Tapis tapis;
	private List<HudArea> areas;
	
	private HudArea focused;
	

	public HudControler(IController controller, Tapis tapis) {
		this.controller = controller;
		this.tapis = tapis;
		this.areas = new ArrayList<HudArea>();
		
		this.tapis.addObserver(this);
	}

	private HudArea getCandidat(int x,int y){
		HudArea cdt = null;
		int i=0;
		while(i<this.areas.size() && cdt == null){
			HudArea h = this.areas.get(i);
			if(h.isIn(x, y)) cdt = h;
			
			i++;
		}
		return cdt;
	}
	
	
//	@Override
//	public void mouseClicked(MouseEvent e) {
//		HudArea h = this.getCandidat(e.getX(), e.getY());
//		if(h != null){
//			h.mouseClicked(e);
//		}else this.game.mouseClicked(e);
//		
//	}
//
//	@Override
//	public void mouseEntered(MouseEvent e) {
//		this.game.mouseEntered(e);
//	}
//
//	@Override
//	public void mouseExited(MouseEvent e) {
//		this.game.mouseExited(e);
//	}
//
//	@Override
//	public void mousePressed(MouseEvent e) {
//		HudArea h = this.getCandidat(e.getX(), e.getY());
//		if(h != null){
//			h.mousePressed(e);
//		}else this.game.mousePressed(e);
//		
//		
//	}
//
//	@Override
//	public void mouseReleased(MouseEvent e) {
//		HudArea h = this.getCandidat(e.getX(), e.getY());
//		if(h != null){
//			h.mouseReleased(e);
//		}else this.game.mouseReleased(e);
//	}
//
//	@Override
//	public void mouseDragged(MouseEvent e) {
//		HudArea h = this.getCandidat(e.getX(), e.getY());
//		if(h != null){
//			h.mouseDragged(e);
//		}else this.game.mouseDragged(e);
//		
//	}
//
//	@Override
//	public void mouseMoved(MouseEvent e) {
//		HudArea h = this.getCandidat(e.getX(), e.getY());
//		if(h != null){
//			h.mouseMoved(e);
//			if(focused == null){
//				focused = h;
//				focused.mouseEntered(e);
//			}
//		}else{
//			this.game.mouseMoved(e);
//			if(focused != null){
//				focused.mouseExited(e);
//				focused = null;
//			}
//		}
//	}

	@Override
	public void update(Observable o, Object arg) {
		
		
	}
	
	
	public void addArea(HudArea a){
		this.areas.add(a);
	}
	
	public void removeArea(HudArea a){
		this.areas.remove(a);
	}

	@Override
	public void mouseEntered() {
		this.controller.mouseEntered();
	}

	@Override
	public void mouseExited() {
		this.controller.mouseExited();
	}

	@Override
	public void mouseLeftPressed(int x, int y) {
		this.controller.mouseLeftPressed(x, y);
	}

	@Override
	public void mouseLeftReleased(int x, int y) {
		this.controller.mouseLeftReleased(x, y);
	}

	@Override
	public void mouseRightPressed(int x, int y) {
		this.controller.mouseRightPressed(x, y);
	}

	@Override
	public void mouseRightReleased(int x, int y) {
		this.controller.mouseRightReleased(x, y);
	}

	@Override
	public void mouseMove(int x, int y, boolean isShiftDown) {
		this.controller.mouseMove(x, y, isShiftDown);
	}

	@Override
	public void mouseDrag(int x, int y) {
		this.controller.mouseDrag(x, y);
	}

	@Override
	public void mouseWheel(boolean up) {
		this.controller.mouseWheel(up);
	}

	@Override
	public void keyShiftPressed() {
		this.controller.keyShiftPressed();
	}

	@Override
	public void keyShiftReleased() {
		this.controller.keyShiftReleased();
	}

	@Override
	public void keyControlPressed() {
		this.controller.keyControlPressed();
	}

	@Override
	public void keyControlReleased() {
		this.controller.keyControlReleased();
	}

	@Override
	public void controlPlusS() {
		this.controller.controlPlusS();
	}

	@Override
	public void controlPlusL() {
		this.controller.controlPlusL();
	}
	
}
