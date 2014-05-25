package com.puzzle.view2.later.state;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import com.puzzle.command.AttrapperMainDroite;
import com.puzzle.command.CommandeArgument;
import com.puzzle.command.param.AttrapperMainDroiteParam;
import com.puzzle.model.Point;
import com.puzzle.model.Tapis;
import com.puzzle.view2.controller.ControllerAdaptater;
import com.puzzle.view2.layer.BackgroundLayer;

public class MainVideState extends ControllerAdaptater implements IState{
	
	private boolean rightButtonDown;
	private Tapis tapis;
	
	
	private BackgroundLayer bckLayer;
	private Point clickPoint;
	private int mouseX;
	private int mouseY;
	private AttrapperMainDroiteParam attrParam;
	
	
	
	
	
	
	public MainVideState(Tapis tapis, BackgroundLayer bckLayer) {
		this.tapis = tapis;
		this.bckLayer = bckLayer;
		
		this.attrParam = new AttrapperMainDroiteParam();
		this.clickPoint = new Point();
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		if(this.rightButtonDown){
			int vx = this.mouseX - e.getX();
			int vy = this.mouseY - e.getY();
			double vex = vx / bckLayer.getScale();
			double vey = vy / bckLayer.getScale();
			double ex = bckLayer.getVue().getX() + Math.round(vex);
			double ey = bckLayer.getVue().getY() - Math.round(vey);
			
			bckLayer.moveTo(Math.round(ex), Math.round(ey));
		
			this.mouseY = e.getY();
			this.mouseX = e.getX();
		}
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
		
		if(e.getButton() == MouseEvent.BUTTON3) this.rightButtonDown = true;
		else if(e.getButton() == MouseEvent.BUTTON1){
			CommandeArgument<AttrapperMainDroiteParam> cmd = new AttrapperMainDroite(this.tapis);
			
			
			
			
			
			
			
			this.attrParam.setPosition(this.clickPoint);
			cmd.setArgument(this.attrParam);
			
			cmd.execute();
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		this.rightButtonDown = false;
		
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
