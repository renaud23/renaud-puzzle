package com.puzzle.view.hud;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.puzzle.model.MainGauche;
import com.puzzle.model.Piece;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.controller.IController;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.mainGauche.PieceInPocket;
import com.puzzle.view.mainGauche.Pocket;
import com.puzzle.zoomTapis.state.TapisZoomControllerEx;


public class HudControler implements IController,Observer{
	private IController controller;
	private Tapis tapis;
	private Fenetre fenetre;
	private List<HudArea> areas;
	private int mouseX;
	private int mouseY;
	private IDrawer drawer;
	
	private HudArea focused;
	
	private Pocket pocket;
	private LunetteArea lunette;
	

	public HudControler(IController controller, IDrawer drawer,Tapis tapis, Fenetre f) {
		this.controller = controller;
		this.tapis = tapis;
		this.drawer = drawer;
		this.fenetre = f;
		this.areas = new ArrayList<HudArea>();
	
		// cr�ation des �l�ments du hud.
		this.pocket = new Pocket(this,tapis, f);
		this.lunette = new LunetteArea((TapisZoomControllerEx) this.controller);
		this.addArea(this.lunette);
		((HudDrawer)this.drawer).addDrawer(this.pocket);
		((HudDrawer)this.drawer).addDrawer(this.lunette);
		
		this.tapis.addObserver(this);
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

	@Override
	public void update(Observable o, Object arg) {
		if(arg == State.droiteToGauche){
			Piece piece = MainGauche.getInstance().getLastIn();
			this.pocket.add(piece);
		}
		
	}
	
	
	public void addArea(HudArea a){
		this.areas.add(a);
	}
	
	public void removeArea(HudArea a){
		this.areas.remove(a);
	}
	
	public void removeArea(Piece p){
		PieceInPocket pi = this.pocket.get(p);
		
		this.areas.remove(pi);
		this.pocket.remove(p);
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

	@Override
	public void controlPlusS() {
		this.controller.controlPlusS();
	}

	@Override
	public void controlPlusL() {
		this.controller.controlPlusL();
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

	public Fenetre getFenetre() {
		return fenetre;
	}
	
	
	
	
	
}
