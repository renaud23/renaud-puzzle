package com.puzzle.view2.layer;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import com.puzzle.view2.controller.ControllerAdaptater;
import com.puzzle.view2.controller.IController;

public class TapisLayer extends ControllerAdaptater{
	private int largeur;
	private int hauteur;
	
	private BackgroundLayer bckLayer;
	private int mouseX;
	private int mouseY;
	
	private boolean rightButtonDown;
	
	
	
	public TapisLayer(BackgroundLayer bckLayeur, int largeur, int hauteur) {
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.bckLayer = bckLayeur;
		
		this.rectangle = new Rectangle(largeur, hauteur);
	}
	
	
	
	
	
	
	
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
		
		if(e.getButton() == MouseEvent.BUTTON3) this.rightButtonDown = true;
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		this.rightButtonDown = false;
		
	}
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(this.rightButtonDown){
			int vx = this.mouseX - e.getX();
			int vy = this.mouseY - e.getY();
			double vex = vx / bckLayer.getScale();
			double vey = vy / bckLayer.getScale();
			
			double ex = bckLayer.getxVue() + vex;
			double ey = bckLayer.getyVue() - vey;
			
			
			bckLayer.moveTo(ex, ey);
		
	
			this.mouseY = e.getY();
		}
		this.mouseX = e.getX();
	}
	
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation()>0){
			bckLayer.zoomOut();
		}else{
			bckLayer.zoomIn();
		}
	}
}
