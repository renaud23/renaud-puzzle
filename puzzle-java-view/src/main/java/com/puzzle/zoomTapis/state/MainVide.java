package com.puzzle.zoomTapis.state;

import java.util.Observable;
import java.util.Observer;

import com.puzzle.command.AttrapperMainDroite;
import com.puzzle.command.CommandeArgument;
import com.puzzle.command.param.AttrapperMainDroiteParam;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Point;
import com.puzzle.model.State;



public class MainVide extends StateAdapter implements Observer{
	
	
	private TapisZoomController controller;
	private AttrapperMainDroiteParam attrParam;
	private boolean rightClick;
	private double mouseX;
	private double mouseY;
	
	
	public MainVide(TapisZoomController controller,double mouseX,double mouseY) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.controller = controller;
		this.attrParam = new AttrapperMainDroiteParam();
		this.controller.getTapis().addObserver(this);
	}

	

	@Override
	public void mouseLeftPressed(int x, int y) {
		Point p = new Point(x, y);
		this.controller.getConverter().convertScreenToModel(p);
		
		CommandeArgument<AttrapperMainDroiteParam> cmd = new AttrapperMainDroite(this.controller.getTapis());
		this.attrParam.setPosition(p);
		cmd.setArgument(this.attrParam);
		
		cmd.execute();
	}


	@Override
	public void mouseRightPressed(int x, int y) {
		this.rightClick = true;
	}

	@Override
	public void mouseRightReleased(int x, int y) {
		this.rightClick = false;
	}

	@Override
	public void mouseMove(int x, int y, boolean isShiftDown) {
		this.mouseX = x;
		this.mouseY = y;
		
	}

	@Override
	public void mouseDrag(int x, int y) {
		if(this.rightClick){
			double vx = this.mouseX - x;
			double vy = this.mouseY - y;
			this.mouseX = x;
			this.mouseY = y;
		
			this.controller.getConverter().moveBy(new Point(vx, vy));
			
			this.controller.getDrawerSelection().draw();
			this.controller.getDrawerTapis().draw();
		}
		
	}

	@Override
	public void mouseWheel(boolean up) {
		this.controller.getConverter().zoom(up);
		
//		((DrawZoomSelection)this.controller.getDrawerSelection()).setZoomScale(this.controller.getConverter().getScaleX());
		
		this.controller.getDrawerSelection().draw();
		this.controller.getDrawerTapis().draw();
	}
	

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof State){
			State st = (State) arg;
			
			if(st == State.MainDroitePleine){
				
				this.controller.getDrawSelectionParam().setComponent(this.attrParam.getContenu());
				this.controller.getDrawSelectionParam().setPosition(new Point(this.mouseX,this.mouseY));
				this.controller.getDrawSelectionParam().setAncre(this.attrParam.getAncre());
				this.controller.getDrawerSelection().setSelection(true);
				
				IState state = new MainPleine(this.controller,this.mouseX,this.mouseY);
				this.controller.setState(state);
				this.controller.getTapis().deleteObserver(this);
				
				this.controller.getDrawerSelection().draw();
				this.controller.getDrawerTapis().draw();
				
			}else if(st == State.gaucheToDroite){
				this.controller.getDrawSelectionParam().setComponent(MainDroite.getInstance().getContenu());
				this.controller.getDrawSelectionParam().setPosition(new Point(Double.MAX_VALUE,Double.MAX_VALUE));
				this.controller.getDrawSelectionParam().setAncre(new Point());
				
				this.controller.getDrawerSelection().setSelection(true);
				
				IState state = new MainPleine(this.controller,Double.MAX_VALUE,Double.MAX_VALUE);
				this.controller.setState(state);
				this.controller.getTapis().deleteObserver(this);
				
				this.controller.getDrawerSelection().draw();
				this.controller.repaint();
			}
			
		}// if
		
	}

}
