package com.puzzle.view.hud;


import java.util.ArrayList;
import java.util.List;
import com.puzzle.model.Tapis;
import com.puzzle.view.controller.IController;
import com.puzzle.view.drawer.IDrawer;


public class HudControler implements IController{
	private IController controller;
	private Tapis tapis;
	private List<HudArea> areas;
	private int mouseX;
	private int mouseY;
	private IDrawer drawer;
	
	private HudArea focused;
	

	

	public HudControler(IController controller, IDrawer drawer,Tapis tapis) {
		this.controller = controller;
		this.tapis = tapis;
		this.drawer = drawer;
		this.areas = new ArrayList<HudArea>();
	
		this.drawer.draw();
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
		HudArea h = this.getCandidat(x,y);
		if(h != null){
			h.mouseLeftPressed(x,y);
		}else this.controller.mouseLeftPressed(x, y);
		
	}

	@Override
	public void mouseLeftReleased(int x, int y) {
		HudArea h = this.getCandidat(x,y);
		if(h != null){
			h.mouseLeftReleased(x,y);
		}else this.controller.mouseLeftReleased(x, y);
	}

	@Override
	public void mouseRightPressed(int x, int y) {
		HudArea h = this.getCandidat(x,y);
		if(h != null){
			h.mouseRightPressed(x,y);
		}else this.controller.mouseRightPressed(x, y);
	}

	@Override
	public void mouseRightReleased(int x, int y) {
		HudArea h = this.getCandidat(x,y);
		if(h != null){
			h.mouseRightReleased(x,y);
		}else this.controller.mouseRightReleased(x, y);
	}

	@Override
	public void mouseMove(int x, int y, boolean isShiftDown) {
	this.mouseX = x;
	this.mouseY = y;
	
	HudArea h = this.getCandidat(x, y);
	if(h != null){
		h.mouseMove(x,y,isShiftDown);
		if(focused == null || focused != h){
			if(focused != null) focused.mouseExited();
			focused = h;
			focused.mouseEntered();
		}
	}else{
		this.controller.mouseMove(x,y,isShiftDown);
		if(focused != null){
			focused.mouseExited();
			focused = null;
		}
	}
	}

	@Override
	public void mouseDrag(int x, int y) {
		HudArea h = this.getCandidat(x,y);
		if(h != null){
			h.mouseDrag(x,y);
		}else this.controller.mouseDrag(x, y);
	}

	@Override
	public void mouseWheel(boolean up) {
		HudArea h = this.getCandidat(this.mouseX,this.mouseY);
		if(h != null){
			h.mouseWheel(up);
		}else this.controller.mouseWheel(up);
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

	public IController getController() {
		return controller;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public IDrawer getDrawer() {
		return drawer;
	}

	public Tapis getTapis() {
		return tapis;
	}


	
	
	
	
}
