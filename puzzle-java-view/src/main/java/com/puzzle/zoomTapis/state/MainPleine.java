package com.puzzle.zoomTapis.state;

import java.util.Observable;
import java.util.Observer;
import com.puzzle.command.CommandeArgument;
import com.puzzle.command.IsClipsable;
import com.puzzle.command.PasserDansMainGauche;
import com.puzzle.command.PoserMainDroite;
import com.puzzle.command.tournerMainDroite;
import com.puzzle.command.param.ChangerDeMainParam;
import com.puzzle.command.param.IsClipsParam;
import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.view.zoomTapis.TapisZoomController;



public class MainPleine extends StateAdapter implements Observer{
	private TapisZoomController controller;
	private Point position;

	private boolean isZPressed;
	private boolean isShiftPressed;
	private boolean isRightClick;
	private double mouseX;
	private double mouseY;
	private IsClipsParam iscParam;
	

	public MainPleine(TapisZoomController controller,double mouseX,double mouseY) {
		this.controller = controller;
		this.position = new Point();
		this.iscParam = new IsClipsParam();
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}

	@Override
	public void mouseLeftPressed(int x, int y) {
		this.controller.getTapis().addObserver(this);
		if(!this.isShiftPressed){
			Point p = new Point(x, y);
			this.controller.getConverter().convertScreenToModel(p);
			
			CommandeArgument<Point> cmd = new PoserMainDroite(this.controller.getTapis());
			cmd.setArgument(p);
			cmd.execute();
		}
	}


	@Override
	public void mouseRightPressed(int x, int y) {
		this.isRightClick = true;		
	}

	@Override
	public void mouseRightReleased(int x, int y) {
		this.isRightClick = false;
	}

	@Override
	public void mouseMove(int x, int y, boolean isShiftDown) {
		
		if(!this.isShiftPressed){
			this.mouseX = x;
			this.mouseY = y;
			
			this.position.setX(x);
			this.position.setY(y);
			this.controller.getDrawSelectionParam().setPosition(this.position);
			
			this.controller.getDrawerSelection().draw();
		}
	}

	@Override
	public void mouseDrag(int x, int y) {
		if(this.isRightClick && !this.isShiftPressed){
			double vx = this.mouseX - x;
			double vy = this.mouseY - y;
			this.mouseX = x;
			this.mouseY = y;
			
			this.position.setX(x);
			this.position.setY(y);
			this.controller.getDrawSelectionParam().setPosition(this.position);
		
			this.controller.getConverter().moveBy(new Point(vx, vy));

			this.controller.getDrawerTapis().draw();
			this.controller.getDrawerSelection().draw();
		}
	}

	@Override
	public void mouseWheel(boolean up) {
		if(!this.isShiftPressed){
			CommandeArgument<Boolean> cmd = new tournerMainDroite();
			cmd.setArgument(!up);
			cmd.execute();
			
			this.controller.getDrawerSelection().draw();
		}
		
	}

	@Override
	public void keyShiftPressed() {
		this.isShiftPressed = true;
		
		Point p = new Point(this.mouseX,this.mouseY);
		this.controller.getConverter().convertScreenToModel(p);
		
		this.iscParam.setCentre(p);
		CommandeArgument<IsClipsParam> cmd = new IsClipsable(this.controller.getTapis());
		cmd.setArgument(this.iscParam);
		cmd.execute();
		
		IState state = new TryClips(this.controller, this.iscParam,this.mouseX,this.mouseY);
		this.controller.setState(state);
	}

	@Override
	public void keyShiftReleased() {
		this.isShiftPressed = false;
	}

	@Override
	public void keyControlPressed() {
		
		ChangerDeMainParam param = new ChangerDeMainParam();
		CommandeArgument<ChangerDeMainParam> cmd = new PasserDansMainGauche(this.controller.getTapis());
		cmd.setArgument(param);
		cmd.execute();
		
		if(param.isReussi()){
			this.controller.getDrawerSelection().setSelection(false);
		
			IState state = new MainVide(this.controller,this.mouseX,this.mouseY);
			this.controller.setState(state);
			
			this.controller.getDrawerSelection().draw();
		}
	}
	
	


	

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof State){
			State st = (State) arg;
			if(st == State.MainDroiteVide){
				this.controller.getTapis().deleteObserver(this);
				
				IState state = new MainVide(this.controller,this.mouseX,this.mouseY);
				this.controller.setState(state);
				
				this.controller.getDrawerSelection().setSelection(false);
				this.controller.getDrawerSelection().setZoom(1.0);
				
				this.controller.getDrawerTapis().draw();
				this.controller.getDrawerSelection().draw();
			}// if
		}// if 
	}

	@Override
	public void keyZPressed() {
		
		if(!this.isZPressed){
			this.isZPressed = true;
			this.controller.getDrawerSelection().setZoom(2.0);
			this.controller.getDrawerSelection().draw();
		}
		

	}

	@Override
	public void keyZReleased() {
		this.isZPressed = false;
		this.controller.getDrawerSelection().setZoom(1.0);
		this.controller.getDrawerSelection().draw();
	}
	
	
	
	

}
