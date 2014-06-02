package com.puzzle.view2.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

public class RootController implements MouseListener,MouseMotionListener,MouseWheelListener,KeyListener{
	
	private int mouseX;
	private int mouseY;
	private IController focused;
	private List<IController> controllers = new ArrayList<IController>();
	private boolean locked;
	

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	
	@Override
	public void mousePressed(MouseEvent e) {
		if(!this.locked){
			this.mouseX = e.getX();
			this.mouseY = e.getY();
			IController cdt = this.getCandidat(e.getX(), e.getY());
			if(cdt != null){
				cdt.mousePressed(e);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(!this.locked){
			this.mouseX = e.getX();
			this.mouseY = e.getY();
			IController cdt = this.getCandidat(e.getX(), e.getY());
			if(cdt != null){
				cdt.mouseReleased(e);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(!this.locked){
			
		}
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(!this.locked){
			
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(!this.locked){
			this.mouseX = e.getX();
			this.mouseY = e.getY();
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
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!this.locked){
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
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(!this.locked){
			IController cdt = this.getCandidat(e.getX(), e.getY());
			if(cdt != null){
				cdt.mouseWheelMoved(e);
			}
		}
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(!this.locked){
			if(e.getKeyCode() == KeyEvent.VK_CONTROL){
				IController c = this.getCandidat(this.mouseX, this.mouseY);
				if(c!=null) c.controlPressed();
			}else if(e.getKeyCode() == KeyEvent.VK_SHIFT){
				IController c = this.getCandidat(this.mouseX, this.mouseY);
				if(c!=null) c.shiftPressed();
			}
		}
	}



	@Override
	public void keyReleased(KeyEvent e) {
		if(!this.locked){
			if(e.getKeyCode() == KeyEvent.VK_CONTROL){
				IController c = this.getCandidat(this.mouseX, this.mouseY);
				if(c!=null) c.controlReleased();
			}else if(e.getKeyCode() == KeyEvent.VK_SHIFT){
				IController c = this.getCandidat(this.mouseX, this.mouseY);
				if(c!=null) c.shiftReleased();
			}
		}
	}
	
	
	
	
	
	public void lock(){
		this.locked = true;
	}
	
	public void unlock(){
		this.locked = false;
	}
	
	// tools
	
	
	private IController getCandidat(int x,int y){
		
		int zRef = Integer.MIN_VALUE;
		IController candidat = null;
		for(IController ctr : this.controllers){
			if(ctr instanceof IMousePositionAware){
				((IMousePositionAware)ctr).setMouseX(x);
				((IMousePositionAware)ctr).setMouseY(y);
			}
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
		c.setZIndex(zIndex+1);
		
		this.controllers.add(c);
		if(this.focused == c)this.focused = null; 
	}
	
	public void removeController(IController c){
		this.controllers.remove(c);
		if(this.focused == c)this.focused = null;
	}
	
	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}


	

}
