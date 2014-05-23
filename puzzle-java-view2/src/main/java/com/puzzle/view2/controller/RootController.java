package com.puzzle.view2.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

public class RootController implements MouseListener,MouseMotionListener,MouseWheelListener{
	
	private int mouseX;
	private int mouseY;
	private IController focused;
	private List<IController> controllers = new ArrayList<>();
	

	@Override
	public void mouseClicked(MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		IController cdt = this.getCandidat(e.getX(), e.getY());
		if(cdt != null){
			if(cdt != this.focused){
				cdt.mouseEntered(e);
				if(this.focused != null) this.focused.mouseExited(e);
				this.focused = cdt;
			}
			cdt.mouseDragged(e);
		}else{
			if(this.focused != null){
				this.focused.mouseExited(e);
				this.focused = null;
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
		
		IController cdt = this.getCandidat(e.getX(), e.getY());
		if(cdt != null){
			if(cdt != this.focused){
				cdt.mouseEntered(e);
				if(this.focused != null) this.focused.mouseExited(e);
				this.focused = cdt;
			}
			cdt.mouseMoved(e);
		}else{
			if(this.focused != null){
				this.focused.mouseExited(e);
				this.focused = null;
			}
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	private IController getCandidat(int x,int y){
		
		int zRef = Integer.MIN_VALUE;
		IController candidat = null;
		for(IController ctr : this.controllers){
			if(ctr.contains(x, y)){
				if(ctr.getZIndex() > zRef){
					zRef = ctr.getZIndex();
					candidat = ctr;
				}
			}	
		}
		return candidat;
	}
	

	/*
	 * 
	 */
	
	public void addController(IController c){
		int zIndex = -1;
		for(IController o : this.controllers){
			if(c.isOver(o)){
				if(o.getZIndex() > zIndex) zIndex = o.getZIndex();
			}
		}
		c.setZIndex(zIndex);
		
		this.controllers.add(c);
		if(this.focused == c)this.focused = null; 
	}
	
	public void removeController(IController c){
		this.removeController(c);
		if(this.focused == c)this.focused = null;
	}
	
	
	
	
	


	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

}
