package com.puzzle.view.state;

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
import com.puzzle.view.java.Game;

public class MainPleine implements IState,Observer{
	
	private Game game;
	private IsClipsParam iscParam;

	
	
	
	public MainPleine(Game game) {
		this.game = game;
		this.iscParam = new IsClipsParam();
	}

	@Override
	public void wheel(boolean up,boolean shift) {
		if(!shift){
			CommandeArgument<Boolean> cmd = new tournerMainDroite();
			cmd.setArgument(!up);
			cmd.execute();
		}
	}

	@Override
	public void dragRight(int vx, int vy,boolean shift) {
		if(!shift){
			Point p = new Point(vx,vy);
			this.game.getConverter().moveBy(p);
		}
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
		}else{
			
			
			
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

	@Override
	public void shiftReleased(int x,int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shiftPressed(int x,int y) {
		Point p = new Point(x,y);
		this.game.getConverter().convertScreenToModel(p);
		
		this.iscParam.setCentre(p);
		CommandeArgument<IsClipsParam> cmd = new IsClipsable(this.game.getTapis());
		cmd.setArgument(this.iscParam);
		cmd.execute();
		
		
		IState state = new TryClips(this.game, this.iscParam);
		this.game.setState(state);
		
	}

	@Override
	public void controlPressed() {
		ChangerDeMainParam param = new ChangerDeMainParam();
		CommandeArgument<ChangerDeMainParam> cmd = new PasserDansMainGauche(this.game.getTapis());
		cmd.setArgument(param);
		cmd.execute();
		
		if(param.isReussi()){
			IState state = new MainVide(this.game);
			this.game.setState(state);
		}
	}

}
