package com.puzzle.zoomTapis.state;

import java.util.Observable;
import java.util.Observer;
import com.puzzle.command.AttrapperMainDroite;
import com.puzzle.command.CommandeArgument;
import com.puzzle.command.param.AttrapperMainDroiteParam;
import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.view.zoomTapis.DrawZoomSelection;



public class MainVide implements IState,Observer{
	
	
	private TapisZoomControllerEx controller;
	private AttrapperMainDroiteParam attrParam;
	private boolean rightClick;
	private double mouseX;
	private double mouseY;
	
	
	public MainVide(TapisZoomControllerEx controller) {
		this.controller = controller;
		this.attrParam = new AttrapperMainDroiteParam();
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
		this.controller.getTapis().addObserver(this);
		
		
		Point p = new Point(x, y);
		this.controller.getConverter().convertScreenToModel(p);
		
		CommandeArgument<AttrapperMainDroiteParam> cmd = new AttrapperMainDroite(this.controller.getTapis());
		this.attrParam.setPosition(p);
		cmd.setArgument(this.attrParam);
		
		cmd.execute();
		
	}

	@Override
	public void mouseLeftReleased(int x, int y) {
		// TODO Auto-generated method stub
		
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
			
			this.controller.getDrawerSelection().clean();
			this.controller.getDrawerTapis().draw();
			this.controller.getDrawerSelection().draw();
		}
		
	}

	@Override
	public void mouseWheel(boolean up) {
		this.controller.getConverter().zoom(up);
		
		((DrawZoomSelection)this.controller.getDrawerSelection()).setZoomScale(this.controller.getConverter().getScaleX());
		
		this.controller.getDrawerSelection().clean();
		this.controller.getDrawerSelection().draw();
		this.controller.getDrawerTapis().draw();
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

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof State){
			State st = (State) arg;
			if(st == State.MainDroitePleine){
				this.controller.getTapis().deleteObserver(this);
				this.controller.getDrawSelectionParam().setComponent(this.attrParam.getContenu());
				this.controller.getDrawSelectionParam().setPosition(new Point(this.mouseX,this.mouseY));
				this.controller.getDrawSelectionParam().setAncre(this.attrParam.getAncre());
				this.controller.getDrawerSelection().setSelection(true);
				
				IState state = new MainPleine(this.controller,this.mouseX,this.mouseY);
				this.controller.setState(state);
				
				this.controller.getDrawerSelection().clean();
				this.controller.getDrawerSelection().draw();
				this.controller.getDrawerTapis().draw();
				
			}// if 
		}// if
		
	}

}
