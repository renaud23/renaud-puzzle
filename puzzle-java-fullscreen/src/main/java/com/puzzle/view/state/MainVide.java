package com.puzzle.view.state;

import java.util.Observable;
import java.util.Observer;

import com.puzzle.command.AttrapperMainDroite;
import com.puzzle.command.CommandeArgument;
import com.puzzle.command.param.AttrapperMainDroiteParam;
import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.view.java.Game;




public class MainVide implements IState,Observer{
	private Game game;
	
	private AttrapperMainDroiteParam attrParam;
	

	public MainVide(Game game) {
		this.game = game;
		this.attrParam = new AttrapperMainDroiteParam();
	}




	@Override
	public void wheel(boolean up) {
		this.game.getConverter().zoom(up);
	}



	@Override
	public void dragRight(int vx, int vy) {
		Point p = new Point(vx,vy);
		this.game.getConverter().moveBy(p);
	}




	@Override
	public void pressLeft(int x, int y, boolean shift) {
		this.game.getTapis().addObserver(this);
		
		
		Point p = new Point(x, y);
		game.getConverter().convertScreenToModel(p);
		
		CommandeArgument<AttrapperMainDroiteParam> cmd = new AttrapperMainDroite(this.game.getTapis());
		this.attrParam.setPosition(p);
		cmd.setArgument(this.attrParam);
		
		cmd.execute();
	}




	@Override
	public void update(Observable obs, Object arg) {
		if(arg instanceof State){
			State st = (State) arg;
			if(st == State.MainDroitePleine){
				this.game.getTapis().deleteObserver(this);
				
				IState state = new MainPleine(this.game);
				this.game.setState(state);
			}// if 
		}// if
	}
	
	
	
}
