package com.puzzle.view.state;

import java.util.Observable;
import java.util.Observer;

import com.puzzle.command.CommandeArgument;
import com.puzzle.command.PoserMainDroite;
import com.puzzle.command.tournerMainDroite;
import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.view.java.Game;

public class MainPleine implements IState,Observer{
	
	private Game game;
	

	
	
	
	public MainPleine(Game game) {
		this.game = game;
	}

	@Override
	public void wheel(boolean up) {
		CommandeArgument<Boolean> cmd = new tournerMainDroite();
		cmd.setArgument(!up);
		cmd.execute();
	}

	@Override
	public void dragRight(int vx, int vy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pressLeft(int x, int y, boolean shift) {
		this.game.getTapis().addObserver(this);
		if(!shift){
			
			Point p = new Point(x, y);
			
			this.game.getConverter().convertScreenToModel(p);
			CommandeArgument<Point> cmd = new PoserMainDroite(this.game.getTapis());
			cmd.setArgument(p);
			
			cmd.execute();
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof State){
			State st = (State) arg;
			if(st == State.MainDroiteVide){
				this.game.getTapis().deleteObserver(this);
				
				IState state = new MainVide(this.game);
				this.game.setState(state);
			}// if
		}// if 

	}

}
